package com.lc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "SpringDateAuditorAware")
@SpringBootApplication
public class LzAgvApplication {

	public static void main(String[] args) {
		SpringApplication.run(LzAgvApplication.class, args);
	}

}
