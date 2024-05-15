package com.mun.batch.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BatchMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String batchNo;

    @Column(nullable = false)
    private String batchName;

    @Column(nullable = false)
    private LocalDateTime registDate;

    @Builder
    public BatchMaster(String batchNo, String batchName, LocalDateTime registDate) {
        this.batchNo = batchNo;
        this.batchName = batchName;
        this.registDate = registDate;
    }
}
