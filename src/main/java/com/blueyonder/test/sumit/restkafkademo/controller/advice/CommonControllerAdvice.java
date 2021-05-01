package com.blueyonder.test.sumit.restkafkademo.controller.advice;

import com.blueyonder.test.sumit.restkafkademo.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplicateException(DuplicateKeyException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Already entity present");
        log.error("Entity present already {}", NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleDuplicateException(EntityNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Entity not found");
        log.error("Entity not found {}", NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
