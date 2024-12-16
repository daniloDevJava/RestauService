package com.projet.foodgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FoodGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodGoApplication.class, args);
	}

}
