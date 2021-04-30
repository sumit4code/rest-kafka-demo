package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;

public interface EmployeeService {

    void create(Employee employee);

    void update(Employee employee);

    void delete(String employeeId);

    Employee get(String employeeId);
}
