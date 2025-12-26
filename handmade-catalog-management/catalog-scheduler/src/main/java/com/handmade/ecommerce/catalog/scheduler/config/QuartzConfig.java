package com.handmade.ecommerce.catalog.scheduler.config;

import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail refreshCollectionsJobDetail() {
        return JobBuilder.newJob(RefreshCollectionsQuartzJob.class)
                .withIdentity("refreshCollectionsJobDetail")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger refreshCollectionsJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(refreshCollectionsJobDetail())
                .withIdentity("refreshCollectionsJobTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .build();
    }

    // The Bridge between Quartz and Spring Batch
    public static class RefreshCollectionsQuartzJob extends QuartzJobBean {
        @Autowired
        private JobLauncher jobLauncher;
        @Autowired
        private Job refreshCollectionsJob;

        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
            try {
                JobParameters params = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters();
                jobLauncher.run(refreshCollectionsJob, params);
            } catch (Exception e) {
                throw new JobExecutionException("Failed to run Batch Job", e);
            }
        }
    }
}
