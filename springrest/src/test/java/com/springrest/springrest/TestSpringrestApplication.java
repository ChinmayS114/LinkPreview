package com.springrest.springrest;

import org.springframework.boot.SpringApplication;

public class TestSpringrestApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringrestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
