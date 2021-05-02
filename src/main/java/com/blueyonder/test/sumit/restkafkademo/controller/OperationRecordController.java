package com.blueyonder.test.sumit.restkafkademo.controller;

import com.blueyonder.test.sumit.restkafkademo.model.OperationRecord;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.OperationRecordRepository;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/operation")
@Api(value = "Track record history", tags = {"records"})
public class OperationRecordController {

    @Autowired
    private final OperationRecordRepository operationRecordRepository;

    @GetMapping
    public ResponseEntity<List<OperationRecord>> retrieve() {
        List<OperationRecord> all = operationRecordRepository.findAll();
        return ResponseEntity.of(Optional.of(all));
    }
}
