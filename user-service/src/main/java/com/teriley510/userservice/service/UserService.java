package com.teriley510.userservice.service;

import com.teriley510.userservice.configuration.KafkaProducerConfig;
import com.teriley510.userservice.entity.User;
import com.teriley510.userservice.repository.UserRepository;
import com.teriley510.userservice.valueObject.Department;
import com.teriley510.userservice.valueObject.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Cacheable
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private RMapCacheReactive<String, User> userMap;
    @Autowired
    private KafkaProducerConfig producerConfig = new KafkaProducerConfig();

    //Kafka producer for the saveUser
    KafkaProducer<String, String> saveProducer = producerConfig.newProducer();


    public UserService(RedissonReactiveClient client) {
        this.userMap = client.getMapCache("userId", new TypedJsonJacksonCodec(String.class, User.class));
    }
     WebClient.Builder webClient = WebClient.builder();
    public Mono<User> saveUser(User user) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("department_topic",
                "New user created with: \n" +
                        "userId: " + user.getId() + "\n" +
                        "firstName: " + user.getFirstName()+ "\n" +
                        "lastName: " + user.getLastName()+ "\n" +
                        "departmentId: " + user.getDepartmentId()
        );
        saveProducer.send(producerRecord);
        // flush and close... flush is like block()... close does a flush by default
        saveProducer.flush();
        saveProducer.close();
        log.info("Inside saveUser of userService");
        userMap.fastPut("userId", user, 5, TimeUnit.MINUTES).subscribe();
        return userRepository.save(user);
    }


    public Mono<User> findById(String id) {
        log.info("Inside findById of userService");
        Mono <User> user = this.userMap.get(id)
                .switchIfEmpty(this.userRepository.findById(id)
                        .flatMap(returnedUser -> userMap.put(returnedUser.getId(), returnedUser, 5, TimeUnit.MINUTES)
                                .thenReturn(returnedUser)));
        return user;
    }

    public ResponseTemplateVO getUserWithDepartment(String userId) {
        log.info("Inside getUserWithDepartment of userService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Mono<User> user = findById(userId);
        User userObj = user.block();

        // Reactive request to department service
        Department department = webClient.build()
                .get()
                .uri("http://localhost:9011/departments/" + userObj.getDepartmentId())
                .retrieve()
                .bodyToMono(Department.class)
                .block();
        vo.setUser(userObj);
        vo.setDepartment(department);
        return vo;
    }


    public Mono<User> deleteUser(String userId) {
        Mono<User> user = userRepository.findById(userId);
        User pojoUser = user.block();
        // Crazy code to delete a user...Hopefully I can find something better
        Mono<Void> voided = userRepository.delete(pojoUser);
        Void voided1 = voided.block();
        return user;
    }

    public Mono<User> updateUser(User user) {
        //Saving to Redis before updating in database
        userMap.fastPut("departmentId", user, 5, TimeUnit.MINUTES ).subscribe();
        return userRepository.save(user);

    }
}

