package com.blueyonder.test.sumit.restkafkademo.reposiotory;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void insertShouldPersistEntity() {
        Employee employee = employeeRepository.insert(Employee.builder().firstName("SUMIT").mobileNumber("7795748385").build());

        assertThat(employee, is(notNullValue()));
        assertThat(employee.getId(), is(notNullValue()));
    }

    @Test
    void insertShouldPersistEntityShouldNotAllowDuplicate() {
        assertThrows(DuplicateKeyException.class, () -> {
            employeeRepository.insert(Employee.builder().firstName("SUMIT").mobileNumber("7795748386").build());
            employeeRepository.insert(Employee.builder().firstName("SUMIT").mobileNumber("7795748386").build());
        });
    }

    @Test
    void insertShouldUpdateEntity() {
        Employee sumit = employeeRepository.insert(Employee.builder().firstName("SUMIT").mobileNumber("7795748387").build());
        assertThat(sumit.getFirstName(), is("SUMIT"));

        Employee updatedEmployee = employeeRepository.save(Employee.builder().id(sumit.getId()).firstName("rahul").mobileNumber("7795748387").build());
        assertThat(updatedEmployee.getFirstName(), is("rahul"));
    }


    @Test
    void deleteShouldRemoveData() {
        employeeRepository.deleteAll();
        Employee entity = employeeRepository.insert(Employee.builder().firstName("SUMIT").mobileNumber("7785748388").build());
        assertThat(employeeRepository.findAll().size(), is(1));

        employeeRepository.deleteById(entity.getId());
        assertThat(employeeRepository.findAll().isEmpty(), is(true));
    }

    @Test
    void getShouldRetrieveDataFrom() {
        Employee entity = employeeRepository.insert(Employee.builder().firstName("SUMIT").mobileNumber("7795748388").build());
        Employee employee = employeeRepository.findById(entity.getId()).get();

        assertThat(entity, is(employee));
    }
}