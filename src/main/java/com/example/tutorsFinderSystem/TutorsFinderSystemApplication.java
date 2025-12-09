package com.example.tutorsFinderSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TutorsFinderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorsFinderSystemApplication.class, args);
	}

}
