package com.blueyonder.test.sumit.restkafkademo.reposiotory;

import com.blueyonder.test.sumit.restkafkademo.model.OperationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRecordRepository extends MongoRepository<OperationRecord, String> {
}
