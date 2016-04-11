package br.ufc.petsi.service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.ufc.petsi.model.Email;

public interface EmailService {
	public void sendEmail(Email email) throws MessagingException;
	public void setMailSender(JavaMailSenderImpl mailSender);
	public JavaMailSender getMailSender();
}
