package com.mun.batch.domain.service;

import com.mun.batch.domain.entity.BatchMaster;
import com.mun.batch.domain.entity.BatchMasterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BatchService {

    private final BatchMasterRepository batchMasterRepository;

    public List<BatchMaster> findAllBatchList() {
        return batchMasterRepository.findAllByOrderByBatchNo();
    }

    public List<BatchMaster> getBatchListByBatchName(String batchName) {
        return batchMasterRepository.findByBatchNameContainingOrderByBatchNo(batchName);
    }
}
