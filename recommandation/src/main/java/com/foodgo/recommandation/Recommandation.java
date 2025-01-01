package com.foodgo.recommandation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class Recommandation {

	public static void main(String[] args) {
		SpringApplication.run(Recommandation.class, args);
	}

	
}
