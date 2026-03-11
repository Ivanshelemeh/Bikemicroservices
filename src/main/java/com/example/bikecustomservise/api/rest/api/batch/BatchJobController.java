package com.example.bikecustomservise.api.rest.api.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rest/api/v1/batch")
@RequiredArgsConstructor
public class BatchJobController {

    private final Job analyzeFailedTransactionsJob;
    private final JobOperator operator;
    private final ObjectMapper mapper;

    @PostMapping("/analyze-transactions")
    public ResponseEntity<String> runAnalyzeFailedTransactions() {

        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("startedAt", System.currentTimeMillis())
                    .toJobParameters();
            String jobParament = mapper.writeValueAsString(params);
            operator.start(analyzeFailedTransactionsJob.getName(), jobParament);
            log.info("Analyze failed transactions job started successfully");
            return ResponseEntity.ok("Job started successfully");
        } catch (Exception e) {
            log.error("Failed to start analyze failed transactions job", e);
            return ResponseEntity.internalServerError()
                    .body("Failed to start job: " + e.getMessage());
        }
    }
}
