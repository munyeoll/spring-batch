package com.mun.batch.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchMasterRepository extends JpaRepository<BatchMaster, Long> {
    List<BatchMaster> findByUseYnOrderByBatchNo(String useYn);
    List<BatchMaster> findAllByOrderByBatchNo();
    List<BatchMaster> findByBatchNameContainingOrderByBatchNo(String batchName);
}
