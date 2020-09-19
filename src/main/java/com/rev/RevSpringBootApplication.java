package com.rev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.rev.config.ConfigProducaoHttps;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProducaoHttps.class)
public class RevSpringBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RevSpringBootApplication.class, args);
	}

}
