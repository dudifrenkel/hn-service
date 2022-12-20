package com.hackathon.hnservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hackathon")
public class HnServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HnServiceApplication.class, args);
	}

}
