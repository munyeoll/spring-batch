package com.mun.batch.job.schedule;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@DisallowConcurrentExecution // 중복 실행 방지
@Component
public class TestScheduleJob2 extends QuartzJobBean {

    private final DefaultJobLauncher defaultJobLauncher;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {
        defaultJobLauncher.run(context);
    }
}
