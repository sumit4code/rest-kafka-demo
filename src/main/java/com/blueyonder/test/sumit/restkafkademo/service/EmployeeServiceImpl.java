package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.exception.EntityNotFoundException;
import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.insert(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            return employeeRepository.save(employee);
        }
        log.error("employee doesn't exist with id {}", employee.getId());
        throw new EntityNotFoundException("Entity not found with id :" + employee.getId());
    }

    @Override
    public void delete(String employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            log.info("Employee {} successfully deleted", employeeId);
        } else {
            log.error("employee doesn't exist with id {}", employeeId);
            throw new EntityNotFoundException("Entity not found with id :" + employeeId);
        }
    }

    @Override
    public Employee get(String employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Entity not found with id :" + employeeId));
    }
}
