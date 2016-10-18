package com.allie.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
public class AllieDataConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(AllieDataConfiguration.class);

	public static void main(String[] args) {

		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		System.setProperty("spring.profiles.active", System.getenv("APPENV"));
		logger.info(System.getenv("APPENV"));
		logger.info(String.valueOf(source.containsProperty("spring.profiles.active")));
		SpringApplication.run(AllieDataConfiguration.class, args);
	}

}
