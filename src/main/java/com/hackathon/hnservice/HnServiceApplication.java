package com.hackathon.hnservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class HnServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HnServiceApplication.class, args);
	}

	@Bean
	AsyncTaskExecutor applicationTaskExecutor() {
		// enable async servlet support
		ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
		return new TaskExecutorAdapter(executorService::execute);
	}

	@Bean
	TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {

		return protocolHandler -> {
			protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
		};
	}
}
