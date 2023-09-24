package com.forever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.forever.*")
@EnableJpaRepositories(basePackages = "com.forever.*")
public class TicketForever1Application {

	public static void main(String[] args) {
		SpringApplication.run(TicketForever1Application.class, args);
	}

}
