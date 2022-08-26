package com.Bank.BankCredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BankCreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankCreditApplication.class, args);
	}

}
