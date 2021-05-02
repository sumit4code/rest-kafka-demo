package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.exception.EntityNotFoundException;
import com.blueyonder.test.sumit.restkafkademo.exception.ValidationException;
import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.model.EventData;
import com.blueyonder.test.sumit.restkafkademo.model.EventMessage;
import com.blueyonder.test.sumit.restkafkademo.model.MessageHeader;
import com.blueyonder.test.sumit.restkafkademo.model.OperationType;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final String destinationTopic;
    private final EmployeeRepository employeeRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    public EmployeeServiceImpl(@Value(value = "${kafka.message.destination.topic}") String destinationTopic, EmployeeRepository employeeRepository, EventPublisher eventPublisher) {
        this.destinationTopic = destinationTopic;
        this.employeeRepository = employeeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Employee create(Employee employee) {
        if (StringUtils.isNoneEmpty(employee.getId())) {
            throw new ValidationException("id should not be populated during create");
        }
        Employee savedEmployee = employeeRepository.insert(employee);
        log.info("employee stored successfully");
        this.createEvent(savedEmployee.getId(), OperationType.CREATE, savedEmployee);
        return savedEmployee;
    }

    @Override
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            Employee savedEmployee = employeeRepository.save(employee);
            this.createEvent(savedEmployee.getId(), OperationType.UPDATE, savedEmployee);
            return savedEmployee;
        }
        log.error("employee doesn't exist with id {}", employee.getId());
        throw new EntityNotFoundException("Entity not found with id :" + employee.getId());
    }

    @Override
    public void delete(String employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            log.info("Employee {} successfully deleted", employeeId);
            this.createEvent(employeeId, OperationType.DELETE, String.format("%s employee deleted", employeeId));
        } else {
            log.error("employee doesn't exist with id {}", employeeId);
            throw new EntityNotFoundException("Entity not found with id :" + employeeId);
        }
    }

    @Override
    public Employee get(String employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Entity not found with id :" + employeeId));
    }


    private EventMessage eventMessage(String messageId, OperationType operationType, Object payload) {
        MessageHeader messageHeader = MessageHeader.builder().topic(destinationTopic).messageId(messageId).build();
        EventData eventData = new EventData(operationType, payload);
        return new EventMessage(messageHeader, eventData);
    }

    private Future<SendResult<String, String>> createEvent(String messageId, OperationType operationType, Object payload) {
        final EventMessage eventMessage = eventMessage(messageId, operationType, payload);
        return this.eventPublisher.publish(eventMessage);
    }
}
