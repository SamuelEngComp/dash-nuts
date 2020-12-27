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

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**").antMatchers("/img/**").antMatchers("/css/**").antMatchers("/js/**")
				.antMatchers("/scss/**").antMatchers("/vendor/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/usuario/registrar").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/participantes").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2015").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2016").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2017").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2018").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2019").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2020").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados2021").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/comparativoAnual").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/linhas").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/comparativopublicolocal").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/comparativopontosconectadosanual").permitAll()
				
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/pontosConectados").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2015").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2016").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2017").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2018").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2019").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2020").permitAll()
				.antMatchers("/atividade/dados").permitAll().antMatchers("/atividade/dados/Publico2021").permitAll()

				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2015bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2015porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2015porano").permitAll()
				
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2016bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2016porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2016porano").permitAll()
				
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2017bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2017porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2017porano").permitAll()
				
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2018bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2018porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2018porano").permitAll()
				
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2019bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2019porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2019porano").permitAll()
				
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2020bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2020porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2020porano").permitAll()
				
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/categoria2021bancas").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/tiposdebancas2021porano").permitAll()
				.antMatchers("/banca/dados").permitAll().antMatchers("/banca/dados/valoresbancas2021porano").permitAll()
				
				.antMatchers("/atividade/dados/formasconexao").permitAll().antMatchers("/atividade/nova")
				.hasRole("CADASTRAR_ATIVIDADE").antMatchers("/atividade/editar/*").hasRole("CADASTRAR_ATIVIDADE")
				.antMatchers("/atividade/{codigo}").hasRole("CADASTRAR_USUARIO").antMatchers("/banca/nova")
				.hasRole("CADASTRAR_BANCA").antMatchers("/banca/editar/*").hasRole("CADASTRAR_BANCA")
				.antMatchers("/banca/{codigo}").hasRole("CADASTRAR_USUARIO").antMatchers("/atividade/filtros")
				.hasRole("FILTRO_ATIVIDADE").antMatchers("/banca/filtros").hasRole("FILTRO_BANCA")
				.antMatchers("/usuario").hasRole("CADASTRAR_USUARIO").antMatchers("/usuario/editar/*")
				.hasRole("CADASTRAR_USUARIO").antMatchers("/usuario/novo").hasRole("CADASTRAR_USUARIO")
				.antMatchers("/usuario/filtros").hasRole("CADASTRAR_USUARIO").antMatchers("/usuario/filtros/status")
				.hasRole("CADASTRAR_USUARIO").anyRequest().authenticated().and().formLogin().loginPage("/login")
				.permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()
				.exceptionHandling().accessDeniedPage("/403").and().sessionManagement().invalidSessionUrl("/");

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
