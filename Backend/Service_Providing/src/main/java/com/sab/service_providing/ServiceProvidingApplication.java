package com.sab.service_providing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceProvidingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProvidingApplication.class, args);
	}

}
