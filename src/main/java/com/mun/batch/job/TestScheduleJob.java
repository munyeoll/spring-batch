package com.mun.batch.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.mun.batch.util.BeanUtil;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestScheduleJob extends QuartzJobBean {

    private final JobLauncher jobLauncher;
    private final BeanUtil beanUtil;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("TestScheduleJob run");
        
        Job jobEx = (Job) beanUtil.getBeanByName("jobEx");

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("id", new Date().getTime())
            .toJobParameters();
            
        jobLauncher.run(jobEx, jobParameters);
    }

}
