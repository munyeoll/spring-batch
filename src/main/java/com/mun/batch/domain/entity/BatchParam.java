package com.mun.batch.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
    name = "batch_param",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_batch_param",
            columnNames = {"paramName", "paramValue"}
        )
    }
)
public class BatchParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String paramName;

    @Column(nullable = false, length = 500)
    private String paramValue;

    @Column(nullable = false, length = 1)
    private String useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_master_id")
    private BatchMaster batchMaster;

    @Builder
    public BatchParam(String paramName, String paramValue, String useYn, BatchMaster batchMaster) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.useYn = useYn;
        this.batchMaster = batchMaster;
    }
}
