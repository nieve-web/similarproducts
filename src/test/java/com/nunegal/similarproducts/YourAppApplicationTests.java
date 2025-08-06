package com.nunegal.similarproducts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YourAppApplicationTests {
	
	@Autowired
	private WebClient webClient;
	
	@Test
	@DisplayName("Should load WebClient bean successfully")
	void contextLoads() {
		assertNotNull(webClient);
	}
	
	@Test
	@DisplayName("WebClient configuration - Should build requests with correct base URL")
	void webClient_shouldUseConfiguredBaseUrl() {
		String testPath = "/test-path";
		String expectedUri = "http://localhost:3001/product/test-path";
		
		String actualUri = webClient.get()
				.uri(testPath)
				.exchange()
				.block()
				.request()
				.getURI()
				.toString();
		
		assertEquals(expectedUri, actualUri);
	}
	
	@Test
	@DisplayName("WebClient operations - should support GET requests")
	void webClient_shouldSupportGetOperations() {
		assertDoesNotThrow(() -> {
			webClient.get()
					.uri("/dummy")
					.exchange()
					.block();
		});
	}
}