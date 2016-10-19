package com.allie.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AllieDataConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(AllieDataConfiguration.class);

	public static void main(String[] args) {
		SpringApplication.run(AllieDataConfiguration.class, args);
	}

}
