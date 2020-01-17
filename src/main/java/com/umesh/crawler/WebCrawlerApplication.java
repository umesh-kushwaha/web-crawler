package com.umesh.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApplication.class, args);
	}

	@Bean
	public ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(10);
	}
}
