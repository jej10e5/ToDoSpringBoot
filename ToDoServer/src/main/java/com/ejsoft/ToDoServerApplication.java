package com.ejsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ToDoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoServerApplication.class, args);
	}

}
