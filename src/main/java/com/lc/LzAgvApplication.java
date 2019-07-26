package com.lc;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class LzAgvApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LzAgvApplication.class, args);
		SpringApplication springApplication = new SpringApplication(LzAgvApplication.class);
        springApplication.setBannerMode(Mode.CONSOLE);
        springApplication.run(args);
	}

}
