package com.mun.batch.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BatchMasterTest {

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

        assertNotNull(batchMaster);
        assertEquals(batchNo, batchMaster.getBatchNo());
        assertEquals(batchName, batchMaster.getBatchName());
        assertEquals(cronString, batchMaster.getCronString());
    }
}
