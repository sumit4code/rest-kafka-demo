package com.blueyonder.test.sumit.restkafkademo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Data
@Document(collection = "employee")
public class Employee implements Serializable {

    @Id
    private final String id;
    @NotNull(message = "first name must not be null")
    private final String firstName;
    @NotNull(message = "last name must not be null")
    private final String lastName;
    @Indexed(unique = true)
    @NotNull(message = "mobile number must not be null")
    private final String mobileNumber;
}
