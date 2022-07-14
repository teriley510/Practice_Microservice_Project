package com.teriley510.departmentservice2.service;

import com.azure.core.annotation.Delete;
import com.azure.cosmos.models.PartitionKey;
import com.teriley510.departmentservice2.configuration.KafkaProducerConfig;
import com.teriley510.departmentservice2.entity.Department;
import com.teriley510.departmentservice2.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.reactivestreams.Subscriber;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
//@CacheConfig(cacheNames = {"Departments"})
public class DepartmentService {
    @Autowired
    private KafkaProducerConfig producerConfig = new KafkaProducerConfig();

    //Kafka producer for the saveDepartment

    KafkaProducer<String, String> saveProducer = producerConfig.newProducer();

    private RMapCacheReactive<String, Department> departmentMap;
    @Autowired
    private DepartmentRepository departmentRepository;


    public DepartmentService(RedissonReactiveClient client) {
        this.departmentMap = client.getMapCache("departmentId", new TypedJsonJacksonCodec(String.class, Department.class));
    }

    public Mono<Department> saveDepartment(Department department) {
        // Kafka Producer
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("department_topic",
                "New department created with: \n" +
                        "departmentId: " + department.getDepartmentId() + "\n" +
                        "departmentName: " + department.getDepartmentName() + "\n" +
                        "departmentAddress: " + department.getDepartmentAddress() + "\n" +
                        "departmentCode: " + department.getDepartmentCode()
        );
        saveProducer.send(producerRecord);
        // flush and close... flush is like block()... close does a flush by default
        saveProducer.flush();
        saveProducer.close();
        log.info("Inside saveDepartment of departmentService");
        // Save department in Redis Cache before saving to database
        departmentMap.fastPut("departmentId", department, 5, TimeUnit.MINUTES ).subscribe();
        return departmentRepository.save(department);
    }
    public Mono<Department> findByDepartmentId(String departmentId) {
        // searching for the department in Redis cache before looking in the database for the department
        return this.departmentMap.get(departmentId)
                .switchIfEmpty(this.departmentRepository.findByDepartmentId(departmentId)
                        .flatMap(returnedDepartment -> departmentMap.put(returnedDepartment.getDepartmentId(), returnedDepartment,
                                        5, TimeUnit.MINUTES )
                        .thenReturn(returnedDepartment))
                );
    }
    public Mono<Department> updateDepartment(Department department) {
        // Saving the department in Redis Cache before saving to the database
        departmentMap.fastPut("departmentId", department, 5, TimeUnit.MINUTES ).subscribe();
        // saving user to CosmosDb
        return departmentRepository.save(department);
    }
}
