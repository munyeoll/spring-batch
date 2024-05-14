package com.mun.batch.job.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class TaskletExample {

    @Bean
    public Job TaskeltJob(
            JobRepository jobRepository,
            TaskletStep taskletStep
    ) {
        Job job = new JobBuilder("taskletJob", jobRepository)
                .start(taskletStep)
                .build();

        return job;
    }

    @Bean
    public TaskletStep taskletStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("taskeltStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>> Tasklet step");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager).build();
    }
}
