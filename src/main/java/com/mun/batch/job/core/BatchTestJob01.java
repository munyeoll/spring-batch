package com.mun.batch.job.core;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.mun.batch.domain.entity.BatchMaster;
import com.mun.batch.domain.entity.BatchMasterRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchTestJob01 {
    
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BatchMasterRepository batchMasterRepository;

    @Bean(name = "jobEx")
    public Job testJob() {
        return new JobBuilder("testJob", jobRepository)
            .start(testStep())
            .build();
    }

    @Transactional
    @Bean(name = "testStep")
    public Step testStep() {
        return new StepBuilder("testStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                Long batchId = Long.parseLong(chunkContext.getStepContext().getJobParameters().get("batchId").toString());
                log.info("######## batchId: {}", batchId);

                BatchMaster batchMaster = batchMasterRepository.findById(batchId).orElseThrow(
                    () -> new IllegalArgumentException("batchId not found")
                );
                
                batchMaster.getBatchParams().stream().forEach(batchParam -> {
                    log.info("######## batchParam name: {}, value: {}", batchParam.getParamName(), batchParam.getParamValue());
                });

                return RepeatStatus.FINISHED;
            }, platformTransactionManager).build();
    }
}
