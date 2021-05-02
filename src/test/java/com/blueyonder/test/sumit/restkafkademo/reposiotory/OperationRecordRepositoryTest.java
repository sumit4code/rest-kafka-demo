package com.blueyonder.test.sumit.restkafkademo.reposiotory;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.model.OperationRecord;
import com.blueyonder.test.sumit.restkafkademo.model.OperationType;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class OperationRecordRepositoryTest {

    @Autowired
    private OperationRecordRepository operationRecordRepository;

    @BeforeEach
    void setUp() {
        Employee employee1 = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar1").lastName("foo1").mobileNumber("7795748380").build();
        Employee employee2 = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar2").lastName("foo2").mobileNumber("7795748382").build();
        Employee employee3 = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar3").lastName("foo3").mobileNumber("7795748383").build();
        operationRecordRepository.insert(Arrays.asList(
                OperationRecord.builder().operationType(OperationType.CREATE).payload(employee1).build(),
                OperationRecord.builder().operationType(OperationType.UPDATE).payload(employee2).build(),
                OperationRecord.builder().operationType(OperationType.DELETE).payload(employee3).build()
        ));
    }

    @Test
    void findAllShouldReturnAllRecords() {
        List<OperationRecord> operationRecordList = operationRecordRepository.findAll();

        assertThat(operationRecordList.size(), is(3));

        assertThat(operationRecordList.stream().map(OperationRecord::getId).filter(StringUtils::isNoneEmpty).count(), is(3L));
        assertThat(operationRecordList.stream().anyMatch(operationRecord -> operationRecord.getOperationType() == OperationType.CREATE), is(true));
        assertThat(operationRecordList.stream().anyMatch(operationRecord -> operationRecord.getOperationType() == OperationType.DELETE), is(true));
        assertThat(operationRecordList.stream().anyMatch(operationRecord -> operationRecord.getOperationType() == OperationType.UPDATE), is(true));
    }
}