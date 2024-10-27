package com.carwiki.carsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsearchApplication.class, args);
	}

}
