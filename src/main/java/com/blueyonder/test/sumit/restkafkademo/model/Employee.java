package com.blueyonder.test.sumit.restkafkademo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Data
@Document(collection = "employee")
public class Employee implements Serializable {

    @Id
    private final String id;

    @NotNull(message = "first name must not be null")
    @Size(min = 1, max = 50, message = "length is not valid")
    private final String firstName;

    @NotNull(message = "last name must not be null")
    @Size(min = 1, max = 50, message = "length is not valid")
    private final String lastName;

    @Indexed(unique = true)
    @NotNull(message = "mobile number must not be null")
    @Pattern(regexp="[7-9][0-9]{9}",message="invalid mobile number")
    @Size(max=10,message="digits should be 10")
    private final String mobileNumber;
}
