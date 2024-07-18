package com.metaphorce.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.metaphorce.databaseLib.*")
public class MsUserWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserWebApplication.class, args);
	}

}
