package com.mun.batch.util;

import com.mun.batch.domain.entity.BatchLog;
import com.mun.batch.domain.entity.BatchLogRespository;
import com.mun.batch.domain.entity.BatchMaster;
import com.mun.batch.domain.entity.BatchMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogUtil {

    private final BatchLogRespository batchLogRespository;
    private final BatchMasterRepository batchMasterRepository;

    /**
     * 배치 로깅
     * @param logType
     * @param logMsg
     * @param jobId
     * @param batchId
     */
    public void log(
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
