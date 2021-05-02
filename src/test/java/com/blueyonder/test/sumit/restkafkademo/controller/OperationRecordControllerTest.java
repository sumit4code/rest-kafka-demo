package com.blueyonder.test.sumit.restkafkademo.controller;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.model.OperationRecord;
import com.blueyonder.test.sumit.restkafkademo.model.OperationType;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.OperationRecordRepository;
import com.blueyonder.test.sumit.restkafkademo.service.helper.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OperationRecordController.class)
class OperationRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final Converter converter = new Converter(new ObjectMapper());

    @MockBean
    private OperationRecordRepository operationRecordRepository;

    @Test
    public void getShouldReturnAllRecords() throws Exception {
        Employee employee1 = Employee.builder().id("1").firstName("bar1").lastName("foo1").mobileNumber("7795748380").build();
        Employee employee2 = Employee.builder().id("2").firstName("bar2").lastName("foo2").mobileNumber("7795748382").build();
        Employee employee3 = Employee.builder().id("3").firstName("bar3").lastName("foo3").mobileNumber("7795748383").build();
        List<OperationRecord> operationRecords = Arrays.asList(OperationRecord.builder().operationType(OperationType.CREATE).payload(employee1).build(),
                OperationRecord.builder().operationType(OperationType.UPDATE).payload(employee2).build(),
                OperationRecord.builder().operationType(OperationType.DELETE).payload(employee3).build());
        String expectedResponseBody = converter.convert(operationRecords);

        when(operationRecordRepository.findAll()).thenReturn(operationRecords);
        this.mockMvc.perform(get("/api/v1/operation"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponseBody)));
    }
}