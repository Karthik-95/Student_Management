package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class StudRestTemplate {
	
	@Bean
	RestTemplate restTemp() {
	return new RestTemplate();
	}

}
