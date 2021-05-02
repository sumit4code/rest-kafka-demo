package com.blueyonder.test.sumit.restkafkademo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Builder
@Data
@Document(collection = "operationRecord")
public class OperationRecord implements Serializable {

    @Id
    private final String id;

    @Field(name = "operationType")
    private final OperationType operationType;

    @Field(name = "payload")
    private final Object payload;
}
