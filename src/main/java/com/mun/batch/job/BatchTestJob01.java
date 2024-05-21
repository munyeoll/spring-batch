package com.mun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchTestJob01 {
    
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean(name = "jobEx")
    public Job testJob() {
        return new JobBuilder("testJob", jobRepository)
            .start(testStep())
            .build();
    }

    @Bean(name = "testStep")
    public Step testStep() {
        return new StepBuilder("testStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("################ Execute test step #################");
                return RepeatStatus.FINISHED;
            }, platformTransactionManager).build();
    }
}
