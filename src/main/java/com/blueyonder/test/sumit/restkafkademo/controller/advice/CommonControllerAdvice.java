package com.blueyonder.test.sumit.restkafkademo.controller.advice;

import com.blueyonder.test.sumit.restkafkademo.exception.BlueYonderException;
import com.blueyonder.test.sumit.restkafkademo.exception.EntityNotFoundException;
import com.blueyonder.test.sumit.restkafkademo.exception.ValidationException;
import com.blueyonder.test.sumit.restkafkademo.model.ErrorModel;
import com.blueyonder.test.sumit.restkafkademo.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler(BlueYonderException.class)
    public ResponseEntity<Object> handleApplicationSpecificGenericException(BlueYonderException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "There is some problem in the system");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptionException(ValidationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", NestedExceptionUtils.getMostSpecificCause(ex));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(ErrorResponse.builder().errorMessage(errorMessages).build());
    }
}

