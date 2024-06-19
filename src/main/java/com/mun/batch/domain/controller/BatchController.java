package com.mun.batch.domain.controller;

import com.mun.batch.domain.dto.BatchMasterDto;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchController {

    private final BatchService batchService;

    @GetMapping
    public List<BatchMasterDto> findAllBatchList() {
        List<BatchMaster> list = batchService.findAllBatchList();
        return list.stream().map(BatchMasterDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{batchName}")
    public List<BatchMasterDto> getBatchListByBatchName(@PathVariable(required = false) String batchName) {
        List<BatchMaster> list = batchService.getBatchListByBatchName(batchName);
        return list.stream().map(BatchMasterDto::new).collect(Collectors.toList());
    }
}
