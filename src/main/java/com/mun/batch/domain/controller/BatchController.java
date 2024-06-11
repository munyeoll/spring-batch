package com.mun.batch.domain.controller;

import com.mun.batch.domain.entity.BatchMaster;
import com.mun.batch.domain.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchController {

    private final BatchService batchService;

    @GetMapping
    public List<BatchMaster> findAllBatchList() {
        return batchService.findAllBatchList();
    }

    @GetMapping("/{batchName}")
    public List<BatchMaster> getBatchListByBatchName(@PathVariable(required = false) String batchName) {
        return batchService.getBatchListByBatchName(batchName);
    }
}
