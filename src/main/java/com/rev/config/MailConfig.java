package com.rev.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.rev.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource("classpath:email-password.properties") })
public class MailConfig {

	@Autowired
	private Environment env;

	// SOLUUÇÃO QUE EU PENSEI PARA PEGAR O USERNAME EXTERNO, POIS TAVA DANDO
	// PROBLEMAS
	@Value("${spring.mail.username}")
	private String username;

	@Bean
	public JavaMailSender emailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(587);
		mailSender.setUsername(this.username);
		mailSender.setPassword(env.getProperty("password"));

		/*
		 * System.out.println(env.getProperty("password"));
		 * System.out.println("USERNAME : " + this.username);
		 */

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // miliseconds
		props.put("mail.smtp.user", mailSender.getUsername());

		mailSender.setJavaMailProperties(props);

		return mailSender;
	}

}
