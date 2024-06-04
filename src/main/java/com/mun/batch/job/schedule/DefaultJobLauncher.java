package com.mun.batch.job.schedule;

import com.mun.batch.domain.enums.BatchLogType;
import com.mun.batch.util.BeanUtil;
import com.mun.batch.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DefaultJobLauncher {

    private final JobLauncher jobLauncher;
    private final BeanUtil beanUtil;
    private final LogUtil logUtil;

    public void run(JobExecutionContext context) {
        // JobDataMap 조회
        Map<String, Object> jobDataMap = context.getMergedJobDataMap();

        String batchId = jobDataMap.get("batchId").toString();
        String jobBeanName = jobDataMap.get("jobBeanName").toString();

        Long execTime = new Date().getTime();
        String jobId = batchId + "." + execTime;

        try {
            // JobBen 가져오기
            Job jobEx = (Job) beanUtil.getBeanByName(jobBeanName);

            // Job 에 전달할 파라미터 생성
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobId", jobId)
                    .addString("batchId", batchId)
                    .toJobParameters();

            jobLauncher.run(jobEx, jobParameters);

            // 종료 로그 기록
            logUtil.log(BatchLogType.SUCCESS.getType(), "성공", jobId, batchId);
        } catch(Exception e) {
            String errMsg = "Job 실행중 오류가 발생하였습니다.\n" + e.getMessage();
            logUtil.log(BatchLogType.ERROR.getType(), errMsg, jobId, batchId);
        }
    }
}
