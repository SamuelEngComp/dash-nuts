package com.rev.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		System.out.println(pass.encode("admin"));
		
	}

}
