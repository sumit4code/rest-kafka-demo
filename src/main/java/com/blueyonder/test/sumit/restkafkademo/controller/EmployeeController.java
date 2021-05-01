package com.blueyonder.test.sumit.restkafkademo.controller;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/employee")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        log.debug("Received request for creating employee {}", employee);
        Employee saved = employeeService.create(employee);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Employee> update(@Valid @RequestBody Employee employee) {
        log.debug("Received request for updating employee {}", employee);
        Employee saved = employeeService.update(employee);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> get(@PathVariable(value = "id") String id) {
        log.debug("Received request for getting employee employee {}", id);
        Employee employee = employeeService.get(id);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
        log.debug("Received request for deleting employee {}", id);
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
