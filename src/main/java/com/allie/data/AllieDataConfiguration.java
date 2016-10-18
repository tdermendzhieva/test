package com.allie.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
public class AllieDataConfiguration {

	public static void main(String[] args) {
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		System.out.println(System.getenv("SPRING_PROFILES_ACTIVE"));
		System.out.println(source.containsProperty("spring.profiles.active"));
		SpringApplication.run(AllieDataConfiguration.class, args);
	}

}
