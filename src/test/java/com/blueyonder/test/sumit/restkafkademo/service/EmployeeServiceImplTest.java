package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.exception.EntityNotFoundException;
import com.blueyonder.test.sumit.restkafkademo.exception.ValidationException;
import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.requestreply.RequestReplyFuture;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@SpringBootTest
class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EventPublisher eventPublisher;

    @Autowired
    private EmployeeService employeeService;
    private Future future =new RequestReplyFuture<>();

    @Test
    void createShouldCreateEmployee() {
        Employee employee = Employee.builder().firstName("bar").lastName("foo").mobileNumber("7795748380").build();
        Employee savedEmployee = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar").lastName("foo").mobileNumber("7795748380").build();
        when(employeeRepository.insert(employee)).thenReturn(savedEmployee);
        when(eventPublisher.publish(any())).thenReturn(future);

        Employee employee1 = employeeService.create(employee);
        assertThat(employee1, is(savedEmployee));

        verify(employeeRepository, times(1)).insert(employee);
    }

    @Test
    void createShouldFailCreateEmployeeWhenIdIsProvided() {
        Employee employee = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar").lastName("foo").mobileNumber("7795748380").build();

        assertThrows(ValidationException.class, () -> employeeService.create(employee));
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void updateShouldUpdateSuccessfully() {
        Employee employee = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar").lastName("foo").mobileNumber("7795748380").build();
        when(employeeRepository.existsById(employee.getId())).thenReturn(Boolean.TRUE);
        Employee toBeUpdated = Employee.builder().id(employee.getId()).firstName("bar").lastName("foo2").mobileNumber("7795748380").build();
        when(employeeRepository.save(toBeUpdated)).thenReturn(toBeUpdated);
        when(eventPublisher.publish(any())).thenReturn(future);

        Employee updatedEmployee = employeeService.update(toBeUpdated);

        assertThat(updatedEmployee.getLastName(), is("foo2"));
        verify(employeeRepository, times(1)).save(toBeUpdated);
    }

    @Test
    void updateShouldFailUpdateWhenEntityIsNotPresent() {
        Employee employee = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar").lastName("foo").mobileNumber("7795748380").build();
        when(employeeRepository.existsById(employee.getId())).thenReturn(Boolean.FALSE);
        Employee toBeUpdated = Employee.builder().id(employee.getId()).firstName("bar").lastName("foo2").mobileNumber("7795748380").build();

        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> employeeService.update(toBeUpdated));

        assertThat(entityNotFoundException.getMessage(), is("Entity not found with id :" + employee.getId()));
        verify(employeeRepository, times(1)).existsById(employee.getId());
        verifyNoInteractions(eventPublisher);
    }

    @Test
    void deleteShouldRemoveFromDocument() {
        String employeeId = UUID.randomUUID().toString();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.delete(employeeId);
        verify(employeeRepository, times(1)).existsById(employeeId);
    }

    @Test
    void deleteShouldFailWhenEntityNotPresent() {
        String employeeId = UUID.randomUUID().toString();
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> employeeService.delete(employeeId));

        assertThat(entityNotFoundException.getMessage(), is("Entity not found with id :" + employeeId));
        verify(employeeRepository, times(1)).existsById(employeeId);
    }

    @Test
    void getShouldReturnEmployee() {
        Employee employee = Employee.builder().id(UUID.randomUUID().toString()).firstName("bar").lastName("foo").mobileNumber("7795748380").build();
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee employeeRetrieved = employeeService.get(employee.getId());

        assertThat(employeeRetrieved.getLastName(), is("foo"));
        assertThat(employeeRetrieved.getFirstName(), is("bar"));
        assertThat(employeeRetrieved.getMobileNumber(), is("7795748380"));
        assertThat(employeeRetrieved.getId(), is(employee.getId()));
    }


    @Test
    void getShouldFailWhenNotPresent() {
        String employeeId = UUID.randomUUID().toString();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> employeeService.get(employeeId));

        assertThat(entityNotFoundException.getMessage(), is("Entity not found with id :" + employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }
}