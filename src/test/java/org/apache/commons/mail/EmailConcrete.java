package org.apache.commons.mail;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class EmailConcrete extends Email {
	
	private EmailConcrete email;
	
	@Override
	public Email setMsg(String msg) throws EmailException {
		
		email = new EmailConcrete();
		
		Properties props = null;
		try {
		  props = new Properties (System.getProperties());
		} catch(SecurityException ex) {
		  props = new Properties();
		}
		
		Authenticator auth = null;
		
		Session ssn = Session.getInstance(props, auth);
		
		try {
			email.message = new MimeMessage(ssn, new ByteArrayInputStream(msg.getBytes()));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return email;
	}

}
