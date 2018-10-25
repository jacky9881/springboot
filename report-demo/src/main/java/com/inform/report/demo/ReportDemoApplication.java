package com.inform.report.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 报表Demo
 *
 */
@SpringBootApplication
public class ReportDemoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ReportDemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ReportDemoApplication.class, args);
	}
}
