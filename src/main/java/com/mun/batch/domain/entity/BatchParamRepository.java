package com.mun.batch.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchParamRepository extends JpaRepository<BatchParam, Long> {

}
