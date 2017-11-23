package com.gerrymander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GerrymanderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerrymanderApplication.class, args);
	}
}
