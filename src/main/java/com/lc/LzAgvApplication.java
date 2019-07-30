package com.lc;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LzAgvApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LzAgvApplication.class, args);
		SpringApplication springApplication = new SpringApplication(LzAgvApplication.class);
        springApplication.setBannerMode(Mode.CONSOLE);
        springApplication.run(args);
	}

}
