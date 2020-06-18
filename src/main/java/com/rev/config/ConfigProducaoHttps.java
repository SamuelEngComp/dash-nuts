package com.rev.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.rev.controller.SegurancaController;

@ConfigurationProperties("nuts")
public class ConfigProducaoHttps {

	
	private final Seguranca seguranca = new Seguranca();
	
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public static class Seguranca{
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	
	}
}
