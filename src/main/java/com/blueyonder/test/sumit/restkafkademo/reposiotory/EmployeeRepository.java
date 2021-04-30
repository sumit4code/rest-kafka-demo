package com.blueyonder.test.sumit.restkafkademo.reposiotory;

import com.blueyonder.test.sumit.restkafkademo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
