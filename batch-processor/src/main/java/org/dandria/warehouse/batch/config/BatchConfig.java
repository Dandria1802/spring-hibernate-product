package org.dandria.warehouse.batch.config;

import lombok.RequiredArgsConstructor;
import org.dandria.warehouse.batch.model.WarehouseItem;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    
    @Value("${batch.chunk-size:1000}")
    private int chunkSize;
    
    @Value("${batch.max-threads:4}")
    private int maxThreads;

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("batch-thread-");
        executor.setConcurrencyLimit(maxThreads);
        return executor;
    }

    @Bean
    public Job warehouseDataProcessingJob(
            Step warehouseDataStep) {
        return jobBuilderFactory.get("warehouseDataProcessingJob")
                .incrementer(new RunIdIncrementer())
                .flow(warehouseDataStep)
                .end()
                .build();
    }

    @Bean
    public Step warehouseDataStep(
            ItemReader<WarehouseItem> reader,
            ItemProcessor<WarehouseItem, WarehouseItem> processor,
            ItemWriter<WarehouseItem> writer) {
        return stepBuilderFactory.get("warehouseDataStep")
                .<WarehouseItem, WarehouseItem>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .throttleLimit(maxThreads)
                .build();
    }
} 