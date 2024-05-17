package com.mun.batch.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BatchMasterTest {

    @Autowired
    private BatchMasterRepository batchMasterRepository;

    @Autowired
    private BatchLogRespository batchLogRespository;

    @Test
    @Transactional
    void testBatchMasterCreation() {
        String batchNo = "BATCH001";
        String batchName = "Daily Job";
        String cronString = "0 0 12 * * ?";
        
        BatchMaster batchMaster = BatchMaster.builder()
                                             .batchNo(batchNo)
                                             .batchName(batchName)
                                             .cronString(cronString)
                                             .build();

        batchMasterRepository.save(batchMaster);

        BatchMaster saveBatchMaster = batchMasterRepository.findById(batchMaster.getId())
                                        .orElseThrow(() -> new RuntimeException("BatchMaster not found"));

        assertNotNull(saveBatchMaster);
        assertEquals(batchNo, saveBatchMaster.getBatchNo());
        assertEquals(batchName, saveBatchMaster.getBatchName());
        assertEquals(cronString, saveBatchMaster.getCronString());

        String jobId = "JOB001";
        String logType = "INFO";
        String logMsg = "Batch job started successfully";

        BatchLog batchLog = BatchLog.builder()
                                    .jobId(jobId)
                                    .logType(logType)
                                    .logMsg(logMsg)
                                    .batchMaster(saveBatchMaster)
                                    .build();

        batchLogRespository.save(batchLog);

        BatchLog saveBatchLog = batchLogRespository.findById(batchLog.getId())
                                                    .orElseThrow(() -> new RuntimeException("BatchLog not found"));
        
        BatchMaster testBatchMaster = saveBatchLog.getBatchMaster();
        assertNotNull(saveBatchLog);
        assertEquals(jobId, saveBatchLog.getJobId());
        assertEquals(logType, saveBatchLog.getLogType());
        assertEquals(logMsg, saveBatchLog.getLogMsg());
        assertEquals(saveBatchMaster.getBatchNo(), testBatchMaster.getBatchNo());
    }
}
