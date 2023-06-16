package com.jungsuk_2_1.postory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PostoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostoryApplication.class, args);
	}

}
