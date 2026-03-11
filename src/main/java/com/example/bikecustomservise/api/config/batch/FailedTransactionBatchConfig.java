package com.example.bikecustomservise.api.config.batch;

import com.example.bikecustomservise.api.dto.analyze.FailedTransactionEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.sql.DataSource;
import javax.persistence.OptimisticLockException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FailedTransactionBatchConfig {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private  DataSource dataSource;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${analyze-transaction.events.topic.name}")
    private String analyzeTransactionTopic;

    @Value("${batch.partition.grid-size}")
    private int gridSize;

    @Value("${batch.chunk-size}")
    private int chunkSize;

    @Bean
    public ProducerFactory<String, FailedTransactionEvent> batchProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.RETRIES_CONFIG, 20);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, FailedTransactionEvent> batchKafkaTemplate() {
        return new KafkaTemplate<>(batchProducerFactory());
    }

    @Bean
    public ColumnRangePartitioner columnRangePartitioner() {
        return new ColumnRangePartitioner(new JdbcTemplate(dataSource));
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Map<String, Object>> failedTransactionReader(
            @Value("#{stepExecutionContext['minId']}") Long minId,
            @Value("#{stepExecutionContext['maxId']}") Long maxId) {

        JdbcPagingItemReader<Map<String, Object>> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(chunkSize);
        reader.setRowMapper((rs, rowNum) -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", rs.getLong("id"));
            row.put("customer_id", rs.getLong("customer_id"));
            row.put("owner_transaction", rs.getString("owner_transaction"));
            row.put("description_detail", rs.getString("description_detail"));
            row.put("period_transaction", rs.getTimestamp("period_transaction"));
            return row;
        });

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, customer_id, owner_transaction, description_detail, period_transaction");
        queryProvider.setFromClause("cus_transaction");
        queryProvider.setWhereClause("transaction_status = 'fail' AND description_detail IS NOT NULL AND id >= :minId AND id <= :maxId");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        Map<String, Object> params = new HashMap<>();
        params.put("minId", minId);
        params.put("maxId", maxId);
        reader.setParameterValues(params);
        reader.setQueryProvider(queryProvider);

        return reader;
    }

    @Bean
    public FailedTransactionProcessor failedTransactionProcessor() {
        return new FailedTransactionProcessor();
    }

    @Bean
    public FailedTransactionKafkaWriter failedTransactionKafkaWriter() {
        return new FailedTransactionKafkaWriter(batchKafkaTemplate(), analyzeTransactionTopic);
    }

    @Bean
    public Step processFailedTransactionsStep() {
        return stepBuilderFactory.get("processFailedTransactionsStep")
                .<Map<String, Object>, FailedTransactionEvent>chunk(chunkSize)
                .reader(failedTransactionReader(null, null))
                .processor(failedTransactionProcessor())
                .writer(failedTransactionKafkaWriter())
                .faultTolerant()
                .retry(OptimisticLockException.class)
                .retryLimit(3)
                .build();
    }

    @Bean
    public TaskExecutorPartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setStep(processFailedTransactionsStep());
        handler.setGridSize(gridSize);
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor("batch-partition-"));
        return handler;
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep")
                .partitioner("processFailedTransactionsStep", columnRangePartitioner())
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    public Job analyzeFailedTransactionsJob() {
        return jobBuilderFactory.get("analyzeFailedTransactionsJob")
                .incrementer(new RunIdIncrementer())
                .start(masterStep())
                .build();
    }
}
