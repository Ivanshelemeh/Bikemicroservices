package com.example.bikecustomservise.api.config.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FailedTransactionJobScheduler {

    private final Job analyzeFailedTransactionsJob;
    private final JobOperator jobOperator;
    private final ObjectMapper objectMapper;

    @Scheduled(cron = "${batch.analyze-transaction.cron}")
    public void runAnalyzeFailedTransactionsJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("scheduledAt", System.currentTimeMillis())
                    .toJobParameters();
            var jobParamentr = objectMapper.writeValueAsString(params);
            jobOperator.start(analyzeFailedTransactionsJob.getName(), jobParamentr);
            log.info("Scheduled analyze failed transactions job completed");
        } catch (Exception e) {
            log.error("Scheduled analyze failed transactions job failed", e);
        }
    }
}
