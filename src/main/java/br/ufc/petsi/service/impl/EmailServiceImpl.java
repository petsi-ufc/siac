package br.ufc.petsi.service.impl;

import java.util.List;

import javax.activation.DataSource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import br.ufc.petsi.model.Attachment;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Email;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.service.EmailService;

@Named
public class EmailServiceImpl implements EmailService 
{
	
	@Inject
	private JavaMailSender mailSender;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void sendEmail(Consultation cons, String message) {
		final Email email = new Email();
		email.setFrom("siac@quixada.ufc.br");
		
		
		if(cons.getPatient() != null){
			Patient patient = cons.getPatient();
			email.setTo(patient.getEmail());
		}else{
			if(cons.getGroup().getPatients().size() > 0){
				String [] emails = new String[cons.getGroup().getPatients().size()];
				for (int i = 0; i < cons.getGroup().getPatients().size(); i++) {
					if(cons.getGroup().getPatients().get(i).getEmail() != null){
						emails[i] = cons.getGroup().getPatients().get(i).getEmail(); 	
					}
				}
				email.setTo(emails);
			}
		}
		
		Professional prof = cons.getProfessional();
		
		email.setText(message);
		email.setSubject("SIAC - "+prof.getSocialService().getName());
		try {
			this.sendEmail(email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendEmail(final Email email) throws MessagingException 
	{
		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		boolean hasAttachments = (email.getAttachments() != null && email.getAttachments().size()>0);
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, hasAttachments);
		
		helper.setTo(email.getTo());
		helper.setFrom(email.getFrom());
		helper.setSubject(email.getSubject());
		helper.setText(email.getText());
		
		if(hasAttachments)
		{
			List<Attachment> attachments = email.getAttachments();
			for (Attachment attachment : attachments) 
			{
				DataSource dataSource = new ByteArrayDataSource(attachment.getData(), attachment.getMimeType());
				if (attachment.isInline())
					helper.addInline(attachment.getFilename(), dataSource);
				else
					helper.addAttachment(attachment.getFilename(), dataSource);
			}
		}
		
		Runnable enviarEmail = new Runnable() {

			@Override
			public void run() {
				mailSender.send(mimeMessage);
				email.setSent(true);
				logger.info("Email sent successfully to {}. Subject: {}", email.getTo(), email.getSubject());
			}

		};

		Thread threadEnviarEmail = new Thread(enviarEmail);
		threadEnviarEmail.start();
		
		
	}

	@Override
	public void setMailSender(JavaMailSenderImpl mailSender) 
	{
		this.mailSender = mailSender;
	}

	@Override
	public JavaMailSender getMailSender() 
	{
		return mailSender;
	}

}
