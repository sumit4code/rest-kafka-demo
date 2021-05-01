package com.blueyonder.test.sumit.restkafkademo.reposiotory;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void insertShouldPersistEntity() {
        Employee employee = employeeRepository.save(Employee.builder().firstName("SUMIT").mobileNumber("7795748380").build());

        assertThat(employee, is(notNullValue()));
        assertThat(employee.getId(), is(notNullValue()));
    }

    @Test
    void insertShouldPersistEntityShouldNotAllowDuplicate() {
        Employee employee = employeeRepository.save(Employee.builder().firstName("SUMIT").mobileNumber("7795748380").build());
        Employee employee2 = employeeRepository.save(Employee.builder().firstName("SUMIT").mobileNumber("7795748380").build());

        assertThat(employee, is(notNullValue()));
        assertThat(employee.getId(), is(notNullValue()));
    }

    @Test
    void insertShouldUpdateEntity() {
    }


    @Test
    void deleteShouldRemoveData() {
    }


    @Test
    void deleteShouldThrowException() {
    }

    @Test
    void getShouldRetrieveDataFrom() {
    }


}