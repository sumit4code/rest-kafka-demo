package com.blueyonder.test.sumit.restkafkademo.consumer;

import com.blueyonder.test.sumit.restkafkademo.model.EventData;
import com.blueyonder.test.sumit.restkafkademo.model.EventMessage;
import com.blueyonder.test.sumit.restkafkademo.model.OperationRecord;
import com.blueyonder.test.sumit.restkafkademo.model.OperationType;
import com.blueyonder.test.sumit.restkafkademo.reposiotory.OperationRecordRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EventMessageConsumerTest {
    @MockBean
    private OperationRecordRepository operationRecordRepository;

    @Autowired
    private EventMessageConsumer eventMessageConsumer;

    @Test
    void consumerShouldCallRepositoryServiceForPersist() {
        EventData eventData = new EventData(OperationType.CREATE, "foo");
        eventMessageConsumer.consumeMessage(eventData);

        verify(operationRecordRepository, times(1)).save(OperationRecord.builder().operationType(eventData.getOperationType()).payload(eventData.getPayload()).build());
    }
}