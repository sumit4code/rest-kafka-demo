package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.model.EventMessage;
import org.springframework.kafka.support.SendResult;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

@FunctionalInterface
public interface EventPublisher {

    Future<SendResult<String, String>> publish(@NotNull EventMessage eventMessage);
}
