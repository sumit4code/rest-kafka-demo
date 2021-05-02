package com.blueyonder.test.sumit.restkafkademo.service;

import com.blueyonder.test.sumit.restkafkademo.kafka.MessagePublisher;
import com.blueyonder.test.sumit.restkafkademo.model.EventMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor
@Service
public class EventPublisherImpl implements EventPublisher {
    @Autowired
    private final MessagePublisher messagePublisher;

    @Override
    public Future<SendResult<String, String>> publish(EventMessage eventMessage) {
        log.trace("Sending message {}", eventMessage.getHeader());
        return messagePublisher.publish(eventMessage);
    }
}
