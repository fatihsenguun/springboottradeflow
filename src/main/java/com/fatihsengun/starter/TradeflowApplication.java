package com.fatihsengun.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fatihsengun"})
@EnableJpaRepositories(basePackages = {"com.fatihsengun"})
@EntityScan(basePackages = {"com.fatihsengun"})
public class TradeflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeflowApplication.class, args);
	}

}
