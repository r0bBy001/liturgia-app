package com.example.demo;

import javax.imageio.ImageIO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiturgiaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiturgiaBackendApplication.class, args);
		ImageIO.scanForPlugins();
	}

}
