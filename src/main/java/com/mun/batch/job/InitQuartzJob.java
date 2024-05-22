package com.mun.batch.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class InitQuartzJob {

    private final Scheduler scheduler;

    @PostConstruct
    public void initQuartz() {
        JobDetail jobDetail = buildJobDetail(TestScheduleJob.class, "testJob", "batch", new HashMap<String, Object>());
        Trigger trigger = buildJobTrigger("0/1 * * * * ?");
        try{
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
            .build();
    }

    public JobDetail buildJobDetail(Class<? extends Job> job, String name, String group, Map<String, Object> params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return JobBuilder.newJob(job).withIdentity(name, group)
            .usingJobData(jobDataMap)
            .build();
    }
}