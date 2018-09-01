package com.gerrymander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@SpringBootApplication
@EnableAsync
public class GerrymanderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GerrymanderApplication.class, args);
	}
}
