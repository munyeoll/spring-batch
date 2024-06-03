package com.mun.batch.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BatchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jobId;

    @Column(nullable = false, length = 50)
    private String logType;

    @Column(length = 1000)
    private String logMsg;

    @Column(nullable = false)
    private LocalDateTime logDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_master_id")
    private BatchMaster batchMaster;

    @Builder
    public BatchLog(String jobId, String logType, String logMsg, BatchMaster batchMaster) {
        this.jobId = jobId;
        this.logType = logType;
        this.logMsg = logMsg;
        this.batchMaster = batchMaster;
        this.logDate = LocalDateTime.now();
    }
}
