package com.nunegal.similarproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class YourAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(YourAppApplication.class, args);
	}
	
	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl("http://localhost:3001/product").build();
	}
	
}
