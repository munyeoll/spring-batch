package com.mun.batch.job.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
//@Configuration
public class ExampleJobConfig {

    @Bean
    public Job job1(
            JobRepository jobRepository,
            Step step1,
            Step step2,
            Step step3
    ) {
        return new JobBuilder("simpleJob", jobRepository)
                .start(step1)
                .next(step2)
                .next(step3)
                .build();
    }

    @Bean
    public Step step1(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ){
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step1");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step step2(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ){
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step2");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step step3(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ){
        return new StepBuilder("step3", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step3");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }
}
