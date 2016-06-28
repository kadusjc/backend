package com.ferreirocorrea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
@EnableAutoConfiguration
public class WebApplication extends SpringBootServletInitializer {
	public static void main(final String[] args) {
		SpringApplication.run(WebApplication.class);
	}

	@Override
	// To generate war
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

}