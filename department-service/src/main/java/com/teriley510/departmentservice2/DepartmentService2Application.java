package com.teriley510.departmentservice2;

import com.teriley510.departmentservice2.entity.Department;
import com.teriley510.departmentservice2.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class DepartmentService2Application implements CommandLineRunner {

	@Autowired
	DepartmentRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(DepartmentService2Application.class, args);



	}


	@Override
	public void run(String... args) {
//		This is code that I got from Azure Cosmos documentation that helped me
//		be able to save items to the database. I saved it just as a reminder.

//		this.repository.deleteAll().block();
//		repository.delete(department.block());
//		LOGGER.info("Deleted all data in container");
//
//
//		final Department department = new Department(
//				"departmentId",
//				"departmentName",
//				"departmentAddress",
//				"departmentCode" );
//
//		//Save the Department class to Azure Cosmos DB database.
//		final Mono<Department> saveDepartmentMono = repository.save(department);
//
//		final Flux<Department> departmentNameDepartmentFLux = repository.findByDepartmentName("departmentName");
//
////		final Mono<Void> deleteById = repository.delete(department);
////		final Void returned = deleteById.block();
////		//  Nothing happens until we subscribe to these Monos.
////		//  findById will not return the department as department is not present.
////
//		final Mono<Department> findByIdMono = repository.findById(department.getDepartmentId());
//		final Department findByIdDepartment = findByIdMono.block();

////		Assert.isNull(findByIdDepartment, "Department must be null");
//
//		final Department savedDepartment = saveDepartmentMono.block();
//		final Void department1 = deleteById.block();
////		Assert.state(savedDepartment != null, "Saved department must not be null");
////		Assert.state(savedDepartment.getDepartmentName().equals(department.getDepartmentName()), "Saved department first name doesn't match" );
//
//		departmentNameDepartmentFLux.collectList().block();
//
//		final Optional<Department> optionalDepartmentResult = repository.findById(department.getDepartmentId()).blockOptional();
////		Assert.isTrue(optionalDepartmentResult.isPresent(), "Cannot find department");
//
//		final Department result = optionalDepartmentResult.get();
////		Assert.state(result.getDepartmentName().equals(department.getDepartmentName()), "query result departmentName doesn't match!");
////		Assert.state(result.getDepartmentCode().equals(department.getDepartmentCode()), "query result departmentCode doesn't match");
//
////		LOGGER.info("findOne in Department collection get result: {}", result.toString());
	}
}
