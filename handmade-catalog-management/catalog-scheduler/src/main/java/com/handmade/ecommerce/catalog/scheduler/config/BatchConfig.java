package com.handmade.ecommerce.catalog.scheduler.config;

import com.handmade.ecommerce.catalog.model.CollectionProductMapping;
import com.handmade.ecommerce.catalog.service.integration.ExternalProductDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfig {

    // Simple in-memory reader for demo. Real world: Use ItemReader<ExternalProductDto> with pagination
    @Bean
    public ItemReader<ExternalProductDto> productItemReader(com.handmade.ecommerce.catalog.service.integration.ProductServiceClient client) {
        return new ListItemReader<>(client.getAllProducts());
    }

    @Bean
    public Job refreshCollectionsJob(JobRepository jobRepository, Step evaluationStep) {
        return new JobBuilder("refreshCollectionsJob", jobRepository)
                .start(evaluationStep)
                .build();
    }

    @Bean
    public Step evaluationStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemReader<ExternalProductDto> reader,
                               ItemProcessor<ExternalProductDto, List<CollectionProductMapping>> processor,
                               ItemWriter<List<CollectionProductMapping>> writer) {
        return new StepBuilder("evaluateRulesStep", jobRepository)
                .<ExternalProductDto, List<CollectionProductMapping>>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
