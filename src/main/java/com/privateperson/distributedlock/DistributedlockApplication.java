package com.privateperson.distributedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DistributedlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedlockApplication.class, args);
	}

}
