package com.mun.batch.job;

import java.util.HashMap;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestJobRunner extends QuartzJobRunner {

    private final Scheduler scheduler;

    @Override
    protected void doRun(ApplicationArguments args) {
        log.info("TestQuartzJob run");

        JobDetail jobDetail = buildJobDetail(TestScheduleJob.class, "testJob", "batch", new HashMap<String, Object>());
        Trigger trigger = buildJobTrigger("0/1 * * * * ?");
        try{
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
