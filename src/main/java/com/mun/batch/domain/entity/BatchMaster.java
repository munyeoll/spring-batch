package com.mun.batch.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BatchMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String batchNo;

    @Column(nullable = false, length = 100)
    private String batchName;

    @Column(nullable = false, length = 100)
    private String cronString;

    @Column(nullable = false, length = 500)
    private String jobBeanName;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "batchMaster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BatchLog> batchLogs;

    @Builder
    public BatchMaster(String batchNo, String batchName, String cronString, String jobBeanName) {
        this.batchNo = batchNo;
        this.batchName = batchName;
        this.cronString = cronString;
        this.jobBeanName = jobBeanName;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }
}
