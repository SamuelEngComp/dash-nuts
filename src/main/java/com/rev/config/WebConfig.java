
package com.rev.config;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

/*import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;
*/

import com.rev.config.format.BigDecimalFormatter;

//import nz.net.ultraq.thymeleaf.LayoutDialect;

import com.rev.controller.AtividadeController;
import com.rev.controller.BancaController;
import com.rev.controller.SegurancaController;
import com.rev.controller.UsuarioController;
import com.rev.controller.converter.ConversorGlobal;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;

@Configuration
@ComponentScan(basePackageClasses = { AtividadeController.class, BancaController.class, UsuarioController.class,
		SegurancaController.class })
@EnableWebMvc
@EnableAsync
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/*
	 * @Bean public ViewResolver jasperReportsViewResolver(DataSource datasource) {
	 * JasperReportsViewResolver resolver = new JasperReportsViewResolver();
	 * resolver.setPrefix("classpath:/relatorios/"); resolver.setSuffix(".jasper");
	 * resolver.setViewNames("relatorio_*");
	 * resolver.setViewClass(JasperReportsMultiFormatView.class);
	 * resolver.setJdbcDataSource(datasource); resolver.setOrder(0); return
	 * resolver; }
	 */

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
//		resolver.setOrder(1);
		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		// engine.addDialect(new LayoutDialect());

		return engine;
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setCharacterEncoding("UTF-8");
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Primary
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

		conversionService.addConverter(new ConversorGlobal());

//		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00"); //
		BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);

//		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		BigDecimalFormatter integerFormatter = new BigDecimalFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);

		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.registerFormatters(conversionService);

		return conversionService;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("classpath:/messages");
		bundle.setDefaultEncoding("UTF-8"); // http://www.utf8-chartable.de
		return bundle;

	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
