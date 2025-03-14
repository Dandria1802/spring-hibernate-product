package org.dandria.warehouse.batch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dandria.warehouse.batch.util.DataGenerator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job warehouseDataProcessingJob;
    private final DataGenerator dataGenerator;

    @PostMapping("/generate-data")
    public ResponseEntity<String> generateTestData(
            @RequestParam(defaultValue = "1000000") int numberOfRecords,
            @RequestParam(defaultValue = "10000") int batchSize) {
        try {
            dataGenerator.generateTestData(numberOfRecords, batchSize);
            return ResponseEntity.ok("Generated " + numberOfRecords + " test records");
        } catch (Exception e) {
            log.error("Error generating test data", e);
            return ResponseEntity.internalServerError().body("Error generating test data: " + e.getMessage());
        }
    }

    @PostMapping("/process")
    public ResponseEntity<String> startBatchProcessing() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("startTime", new Date())
                    .toJobParameters();

            jobLauncher.run(warehouseDataProcessingJob, jobParameters);
            return ResponseEntity.ok("Batch job started successfully");
        } catch (Exception e) {
            log.error("Error starting batch job", e);
            return ResponseEntity.internalServerError().body("Error starting batch job: " + e.getMessage());
        }
    }
} 