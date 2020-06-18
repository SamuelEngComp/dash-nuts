package com.rev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rev.config.ConfigProducaoHttps;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProducaoHttps.class)
public class RevSpringBootApplication {

	public static void main(String[] args) {		
		SpringApplication.run(RevSpringBootApplication.class, args);
	}

}
