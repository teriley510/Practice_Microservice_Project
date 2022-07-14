package com.teriley510.userservice.controller;

import com.teriley510.userservice.entity.User;
import com.teriley510.userservice.service.UserService;
import com.teriley510.userservice.valueObject.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public Mono<User> saveUser(@RequestBody User user) {
        log.info("Inside saveUser method of userController");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseTemplateVO getUserWithDepartment(@PathVariable("id")String id) {
        log.info("Inside getUserWithDepartment of userController");
        return userService.getUserWithDepartment(id);
    }

    @DeleteMapping("/{userId}")
    private Mono<User> deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable("id") String id, @RequestBody User user ) {
        return userService.updateUser(user);
    }
}
