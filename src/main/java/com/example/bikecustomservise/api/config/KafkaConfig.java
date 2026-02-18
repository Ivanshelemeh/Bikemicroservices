package com.example.bikecustomservise.api.config;

import com.example.bikecustomservise.api.dto.analyze.AnalyzeEventDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value("${spring.kafka.producer.key-serializer}")
    private String produceKeySerializer;

    @Value("${recommendation.events.topic.name}")
    private String recommendationOrderTopic;

    private final static Integer RECOMMENDATION_TOPIC_PARTITION = 3;
    private final static Integer RECOMMENDATION_TOPIC_REPLICATION = 3;


    private ProducerFactory<String, AnalyzeEventDto> producerFactory() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, produceKeySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "trx-producer-1");
        props.put(ProducerConfig.RETRIES_CONFIG, 20);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "50000");
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(
            ProducerFactory<String, Object> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, AnalyzeEventDto> kafkaTemplate() {
        KafkaTemplate<String, AnalyzeEventDto> kafkaTemplate =
                new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setTransactionIdPrefix("trx-producer-");
        return kafkaTemplate;
    }

    @Bean
    NewTopic createRecommendationTopic() {
        return TopicBuilder.name(recommendationOrderTopic)
                .partitions(RECOMMENDATION_TOPIC_PARTITION)
                .replicas(RECOMMENDATION_TOPIC_REPLICATION)
                .build();
    }


}
