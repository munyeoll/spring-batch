package com.mun.batch.domain.dto;

import com.mun.batch.domain.entity.BatchMaster;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BatchMasterDto {

    private Long id;
    private String batchNo;
    private String batchName;
    private String cronExp;
    private String jobClassPath;
    private String jobBeanName;
    private String useYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BatchMasterDto(BatchMaster entity) {
        this.id = entity.getId();
        this.batchNo = entity.getBatchNo();
        this.batchName = entity.getBatchName();
        this.cronExp = entity.getCronExp();
        this.jobClassPath = entity.getJobClassPath();
        this.jobBeanName = entity.getJobBeanName();
        this.useYn = entity.getUseYn();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
