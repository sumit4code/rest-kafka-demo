package com.blueyonder.test.sumit.restkafkademo.controller;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.service.EmployeeService;
import com.blueyonder.test.sumit.restkafkademo.service.helper.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final Converter converter = new Converter(new ObjectMapper());

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void shouldBeAbleToCreateEmployee() throws Exception {
        Employee employee = Employee.builder().firstName("bar1").lastName("foo1").mobileNumber("7795748380").build();
        Employee createdEmployeeObject = Employee.builder().id("1").firstName("bar1").lastName("foo1").mobileNumber("7795748380").build();
        String expectedResponseBody = converter.convert(createdEmployeeObject);

        when(employeeService.create(employee)).thenReturn(createdEmployeeObject);

        this.mockMvc.perform(post("/api/v1/employee").content(toJson(employee)).contentType("application/json"))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString(expectedResponseBody)));
    }


    @Test
    public void shouldAbleToUpdate() throws Exception {
        Employee employee = Employee.builder().id("1").firstName("bar1").lastName("foo1").mobileNumber("7795748380").build();
        String expectedResponseBody = converter.convert(employee);
        when(employeeService.update(employee)).thenReturn(employee);

        this.mockMvc.perform(put("/api/v1/employee").content(toJson(employee)).contentType("application/json"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponseBody)));
    }


    @Test
    public void shouldAbleToDelete() throws Exception {
        doNothing().when(employeeService).delete("1");

        this.mockMvc.perform(delete("/api/v1/employee/1").contentType("application/json")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldAbleToRetrieve() throws Exception {
        Employee expectedResponseObject = Employee.builder().id("1").firstName("bar1").lastName("foo1").mobileNumber("7795748380").build();
        when(employeeService.get("1")).thenReturn(expectedResponseObject);
        String expectedResponseBody = converter.convert(expectedResponseObject);

        this.mockMvc.perform(get("/api/v1/employee/1").contentType("application/json")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponseBody)));
    }

    private String toJson(Object object) {
        return converter.convert(object);
    }

}