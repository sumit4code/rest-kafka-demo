package com.blueyonder.test.sumit.restkafkademo;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EmbeddedKafka
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RestKafkaDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
@ActiveProfiles("test")
public class DemoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = Employee.builder().firstName("SUMIT").lastName("DAS").mobileNumber("8795748380").build();
        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/employee", employee, Employee.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testCreateEmployeeShouldReturnError() {
        Employee employee = Employee.builder().id("1").firstName("SUMIT").lastName("DAS").mobileNumber("7795748380").build();
        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/employee", employee, Employee.class);
        assertThat(postResponse.getStatusCode().is4xxClientError(),  is(true));
    }


    @Test
    public void testCreateEmployeeShouldReturnErrorWhenFirstNameAndLastNameIsEmpty() {
        Employee employee = Employee.builder().mobileNumber("8795748380").build();
        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/employee", employee, Employee.class);
        assertThat(postResponse.getStatusCode().is4xxClientError(),  is(true));
    }
}
