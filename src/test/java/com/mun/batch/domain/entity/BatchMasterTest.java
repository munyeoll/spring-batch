package com.mun.batch.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BatchMasterTest {

    @Autowired
    private BatchMasterRepository batchMasterRepository;

    @Test
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
    }
}
