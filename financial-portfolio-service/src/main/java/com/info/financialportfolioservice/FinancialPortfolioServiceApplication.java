package com.info.financialportfolioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FinancialPortfolioServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinancialPortfolioServiceApplication.class, args);
	}

	// To facilitate communication with external endpoints/APIs
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
