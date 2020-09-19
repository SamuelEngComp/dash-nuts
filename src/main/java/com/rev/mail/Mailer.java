package com.rev.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rev.modelo.Usuario;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void enviar(Usuario usuario) {

		// Essa forma envia e-mail simples

		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("samuel.farias@ufba.br");
		mensagem.setTo(usuario.getEmail());
		mensagem.setSubject("Cadastro realizado com sucesso");
		mensagem.setText(" Obrigado " + usuario.getPrimeiroNome() + " " + usuario.getUltimoNome() + ", seu cadastro "
				+ "foi realizado com sucesso !!! " + "Sua permissão inicial é de VISITANTE");
		javaMailSender.send(mensagem);

	}
}
