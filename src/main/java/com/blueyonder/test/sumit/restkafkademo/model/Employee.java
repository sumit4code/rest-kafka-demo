package com.blueyonder.test.sumit.restkafkademo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Data
@Document(collection = "employee")
public class Employee {

    @Id
    private final String employeeId;
    private final String firstName;
    private final String lastName;
    private final Date dob;
    private final Date doj;
}
