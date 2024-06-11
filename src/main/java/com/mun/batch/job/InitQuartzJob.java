package com.mun.batch.job;

import java.util.HashMap;
import java.util.List;
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

import com.mun.batch.domain.entity.BatchMaster;
import com.mun.batch.domain.entity.BatchMasterRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class InitQuartzJob {

    private final Scheduler scheduler;
    private final BatchMasterRepository batchMasterRepository;

    @PostConstruct
    public void initQuartz() {
        List<BatchMaster> batchMasters = batchMasterRepository.findByUseYnOrderByBatchNo("Y");
        for (BatchMaster batchMaster : batchMasters) {
            try{
                // BatchMaster info
                Long batchId = batchMaster.getId();
                String batchNo = batchMaster.getBatchNo();
                String jobClassPath = batchMaster.getJobClassPath();
                String jobBeanName = batchMaster.getJobBeanName();
                String cronExp = batchMaster.getCronExp();

                // BatchParam info
                Map<String, Object> params = new HashMap<>();
                params.put("batchId", batchId);
                params.put("jobBeanName", jobBeanName);

                Class<? extends Job> jobClass = Class.forName(jobClassPath).asSubclass(Job.class);

                JobDetail jobDetail = buildJobDetail(jobClass, batchNo, "batch", params);
                Trigger trigger = buildJobTrigger(cronExp);

                // Schedule 등록
                scheduler.scheduleJob(jobDetail, trigger);

                log.info(">>>> Init quartz job: {}", jobClassPath);
            } catch (SchedulerException se) {
                log.error("Error scheduling job", se);
            } catch (ClassNotFoundException ce) {
                log.error("Error loading job class", ce);
            }
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