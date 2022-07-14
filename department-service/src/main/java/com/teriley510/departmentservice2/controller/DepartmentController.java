package com.teriley510.departmentservice2.controller;

import com.teriley510.departmentservice2.entity.Department;
import com.teriley510.departmentservice2.repository.DepartmentRepository;
import com.teriley510.departmentservice2.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository repository;

    @PostMapping("/")
    public Mono<Department> saveDepartment(@RequestBody Department department) {
        log.info("Inside of saveDepartment of departmentController");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/{id}")
    public Mono<Department> findDepartmentById(@PathVariable("id") String id) {
        return departmentService.findByDepartmentId(id);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("departmentId") String id) {
       Mono<Department> monoDepartment = repository.findByDepartmentId(id);
       Department department = monoDepartment.block();
        System.out.println(department);
        //This code is a little confusing to me but it made it work. Hope to figure out a cleaner way
        //to do this in the future
       Mono<Void> voided = repository.delete(department);
       Void voided1 = voided.block();
       return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public Mono<Department> updateDepartment(@PathVariable("id") String id, @RequestBody Department department) {
        return departmentService.updateDepartment(department);
    }
}
