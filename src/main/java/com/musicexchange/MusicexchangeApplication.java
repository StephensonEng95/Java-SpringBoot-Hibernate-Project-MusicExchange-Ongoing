package com.musicexchange.musicexchange;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages="com.musicexchange.musicexchange")
//@EntityScan(basePackages = "com.musicexchange.models")
public class MusicexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicexchangeApplication.class, args);
	}

}
