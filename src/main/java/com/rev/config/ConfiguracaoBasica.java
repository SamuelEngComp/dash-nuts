package com.rev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rev.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class ConfiguracaoBasica extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("CADASTRO_ATIVIDADE");

		  auth
		  .userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
 
		
	}

	@Override 
	public void configure(WebSecurity web) throws Exception { 
		web.ignoring()
			.antMatchers("/static/**")
			.antMatchers("/img/**")
			.antMatchers("/css/**")
			.antMatchers("/js/**")
			.antMatchers("/scss/**")
			.antMatchers("/vendor/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests().antMatchers("/").permitAll()
			.antMatchers("/usuario/registrar").permitAll()
			.antMatchers("/atividade/dados").permitAll()
			.antMatchers("/atividade/dados/participantes").permitAll()
			.antMatchers("/atividade/dados/formasconexao").permitAll()
			 .antMatchers("/atividade/nova").hasRole("CADASTRAR_ATIVIDADE")
			 .antMatchers("/atividade/editar/*").hasRole("CADASTRAR_ATIVIDADE")
			 .antMatchers("/atividade/{codigo}").hasRole("CADASTRAR_USUARIO")
			 .antMatchers("/banca/nova").hasRole("CADASTRAR_BANCA")
			 .antMatchers("/banca/editar/*").hasRole("CADASTRAR_BANCA")
			 .antMatchers("/banca/{codigo}").hasRole("CADASTRAR_USUARIO")
			 .antMatchers("/atividade/filtros").hasRole("FILTRO_ATIVIDADE")
			 .antMatchers("/banca/filtros").hasRole("FILTRO_BANCA")
			 .antMatchers("/usuario").hasRole("CADASTRAR_USUARIO")
			 .antMatchers("/usuario/editar/*").hasRole("CADASTRAR_USUARIO")
			 .antMatchers("/usuario/novo").hasRole("CADASTRAR_USUARIO")
			 .antMatchers("/usuario/filtros").hasRole("CADASTRAR_USUARIO")
			 .antMatchers("/usuario/filtros/status").hasRole("CADASTRAR_USUARIO")
				.anyRequest()
					.authenticated()
				.and()
					.formLogin()
						.loginPage("/login")
							.permitAll()
					.and()
					.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.and()
					.exceptionHandling().accessDeniedPage("/403")
					.and()
					.sessionManagement().invalidSessionUrl("/");
						
	}
	
	
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
