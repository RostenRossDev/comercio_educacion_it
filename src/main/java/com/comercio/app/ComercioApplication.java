package com.comercio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class ComercioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComercioApplication.class, args);
	}
}
