package com.mun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BatchTestScheduler {
    
    private final JobLauncher jobLauncher;
    private final Job testJob;

    @Scheduled(cron = "*/5 * * * * ?")
    public void runJob() {
        try {
            log.info("################ runJob #################");
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
            jobLauncher.run(testJob, jobParameters);
        } catch (Exception e) {
            log.error("Failed to execute job", e);
        }
    }
}
