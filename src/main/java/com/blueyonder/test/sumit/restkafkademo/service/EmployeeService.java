package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;

public interface EmployeeService {

    Employee create(Employee employee);

    Employee update(Employee employee);

    void delete(String employeeId);

    Employee get(String employeeId);
}
