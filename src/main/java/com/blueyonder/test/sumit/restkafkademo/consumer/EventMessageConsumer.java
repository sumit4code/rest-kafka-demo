package com.blueyonder.test.sumit.restkafkademo.consumer;

import com.blueyonder.test.sumit.restkafkademo.model.EventData;
import com.blueyonder.test.sumit.restkafkademo.model.OperationRecord;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.OperationRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Reads the message from kafka topic and stores into mongo document
 */
@Slf4j
@AllArgsConstructor
@Service
public class EventMessageConsumer {

    @Autowired
    private final OperationRecordRepository operationRecordRepository;

    @KafkaListener(id = "event-message-consumer", topics = "${kafka.message.consumer.topic}", groupId = "group_id")
    public void consumeMessage(@Payload EventData eventData, MessageHeaders messageHeaders) {
        log.debug("Message received {}", eventData);
        operationRecordRepository.save(
                OperationRecord.builder().operationType(eventData.getOperationType())
                        .payload(eventData.getPayload())
                        .build()
        );
        log.info("Message persisted successfully");

    }
}
