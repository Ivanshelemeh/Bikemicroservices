package com.example.bikecustomservise.api.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ColumnRangePartitioner implements Partitioner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Long min = jdbcTemplate.queryForObject(
                "SELECT MIN(id) FROM cus_transaction WHERE transaction_status = 'fail' AND description_detail IS NOT NULL",
                Long.class
        );
        Long max = jdbcTemplate.queryForObject(
                "SELECT MAX(id) FROM cus_transaction WHERE transaction_status = 'fail' AND description_detail IS NOT NULL",
                Long.class
        );

        Map<String, ExecutionContext> result = new HashMap<>();

        if (min == null || max == null) {
            log.info("No failed transactions found for partitioning");
            return result;
        }

        long targetSize = (max - min) / gridSize + 1;

        long start = min;
        long end = start + targetSize - 1;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putLong("minId", start);
            context.putLong("maxId", Math.min(end, max));

            result.put("partition" + i, context);
            log.debug("Partition {}: minId={}, maxId={}", i, start, Math.min(end, max));

            start += targetSize;
            end += targetSize;

            if (start > max) {
                break;
            }
        }

        log.info("Created {} partitions for id range [{}, {}]", result.size(), min, max);
        return result;
    }
}
