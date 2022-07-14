package com.teriley510.departmentservice2.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.teriley510.departmentservice2.entity.Department;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DepartmentRepository extends ReactiveCosmosRepository<Department, String> {

    Flux<Department> findByDepartmentName(String departmentName);
    Mono<Department> findByDepartmentId(String departmentId);

    }


