package com.teriley510.userservice;

//import com.teriley510.userservice.entity.User;
//import com.teriley510.userservice.repository.UserRepository;
import com.teriley510.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

import java.util.Optional;
@ComponentScan("com.teriley510.userservice")
@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

//	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceApplication.class);
//

//	@Autowired
//	private UserRepository repository;
	@Bean
	public WebClient.Builder webClient() {
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	public void run(String... var1) {
//		this.repository.deleteAll().block();
//		LOGGER.info("Deleted all data in container.");
//
//		final User testUser = new User("testId", "testFirstName", "testLastName", "test address line one");
//
//		// Save the User class to Azure Cosmos DB database.
//		final Mono<User> saveUserMono = repository.save(testUser);
//
//		final Flux<User> firstNameUserFlux = repository.findByFirstName("testFirstName");
//
//		//  Nothing happens until we subscribe to these Monos.
//		//  findById will not return the user as user is not present.
//		final Mono<User> findByIdMono = repository.findById(testUser.getId());
//		final User findByIdUser = findByIdMono.block();
//		Assert.isNull(findByIdUser, "User must be null");
//
//		final User savedUser = saveUserMono.block();
//		Assert.state(savedUser != null, "Saved user must not be null");
//		Assert.state(savedUser.getFirstName().equals(testUser.getFirstName()), "Saved user first name doesn't match");
//
//		firstNameUserFlux.collectList().block();
//
//		final Optional<User> optionalUserResult = repository.findById(testUser.getId()).blockOptional();
//		Assert.isTrue(optionalUserResult.isPresent(), "Cannot find user.");
//
//		final User result = optionalUserResult.get();
//		Assert.state(result.getFirstName().equals(testUser.getFirstName()), "query result firstName doesn't match!");
//		Assert.state(result.getLastName().equals(testUser.getLastName()), "query result lastName doesn't match!");
//
//		LOGGER.info("findOne in User collection get result: {}", result.toString());
	}
}
