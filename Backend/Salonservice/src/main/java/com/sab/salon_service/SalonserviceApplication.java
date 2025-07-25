package com.sab.salon_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SalonserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalonserviceApplication.class, args);
	}
}
