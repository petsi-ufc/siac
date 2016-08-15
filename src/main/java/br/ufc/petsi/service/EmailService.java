package br.ufc.petsi.service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Email;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;

public interface EmailService {
	public void sendEmail(Consultation cons, String message);
	public void sendEmail(Email email) throws MessagingException;
	public void setMailSender(JavaMailSenderImpl mailSender);
	public JavaMailSender getMailSender();
}
