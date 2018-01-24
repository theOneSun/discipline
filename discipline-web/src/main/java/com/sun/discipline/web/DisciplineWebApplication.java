package com.sun.discipline.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sun.discipline.*")
public class DisciplineWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisciplineWebApplication.class, args);
	}
}
