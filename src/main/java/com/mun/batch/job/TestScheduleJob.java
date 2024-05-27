package com.mun.batch.job;

import java.util.Date;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
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

@RequiredArgsConstructor
@DisallowConcurrentExecution // 중복 실행 방지
@Component
public class TestScheduleJob extends QuartzJobBean {

    private final JobLauncher jobLauncher;
    private final BeanUtil beanUtil;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // JobDataMap 조회
        Map<String, Object> jobDataMap = context.getMergedJobDataMap();

        String batchId = jobDataMap.get("batchId").toString();
        String jobBeanName = jobDataMap.get("jobBeanName").toString();

        // JobBen 가져오기
        Job jobEx = (Job) beanUtil.getBeanByName(jobBeanName);
        
        // Job 에 전달할 파라미터 생성
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("jobId", new Date().getTime())
            .addString("batchId", batchId)
            .toJobParameters();
            
        jobLauncher.run(jobEx, jobParameters);
    }
}
