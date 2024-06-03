package com.mun.batch.job;

import com.mun.batch.domain.entity.BatchLog;
import com.mun.batch.domain.entity.BatchLogRespository;
import com.mun.batch.domain.entity.BatchMaster;
import com.mun.batch.domain.entity.BatchMasterRepository;
import com.mun.batch.domain.enums.BatchLogType;
import com.mun.batch.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@DisallowConcurrentExecution // 중복 실행 방지
@Component
public class TestScheduleJob extends QuartzJobBean {

    private final JobLauncher jobLauncher;
    private final BeanUtil beanUtil;
    private final BatchLogRespository batchLogRespository;
    private final BatchMasterRepository batchMasterRepository;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {
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

            if("1".equals(batchId)) throw new Exception("오류 발생 ~!~!~!");

            // 종료 로그 기록
            batchLogging(BatchLogType.SUCCESS.getType(), "성공", jobId, batchId);
        } catch(Exception e) {
            log.error(e.getMessage());
            String errMsg = "Job 실행중 오류가 발생하였습니다.\n" + e.getMessage();
            batchLogging(BatchLogType.ERROR.getType(), errMsg, jobId, batchId);
        }
    }

    /**
     * 배치 로깅
     * @param logType
     * @param logMsg
     * @param jobId
     * @param batchId
     */
    public void batchLogging(
            String logType,
            String logMsg,
            String jobId,
            String batchId
    ) {
        BatchMaster batchMaster = batchMasterRepository.findById(Long.parseLong(batchId))
                    .orElseThrow(() -> new RuntimeException("BatchMaster not found"));
        BatchLog batchLog = BatchLog.builder()
                .jobId(jobId)
                .logType(logType)
                .logMsg(logMsg)
                .batchMaster(batchMaster)
                .build();
        batchLogRespository.save(batchLog);
    }
}
