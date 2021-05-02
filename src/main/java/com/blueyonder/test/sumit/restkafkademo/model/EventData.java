package com.blueyonder.test.sumit.restkafkademo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class EventData {

    private OperationType operationType;
    private Object payload;
}
