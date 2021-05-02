package com.blueyonder.test.sumit.restkafkademo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {

    private String fieldName;
    private Object rejectedValue;
    private String messageError;
}
