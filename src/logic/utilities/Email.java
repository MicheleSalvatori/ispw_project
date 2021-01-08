package logic.utilities;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private static final String FROM = AppProperties.getInstance().getProperty("email");
	private static final String PASS = AppProperties.getInstance().getProperty("emailpasswd");
	private static String TO;
	
	public static void password(String TO, String password) throws MessagingException {
		
		Email.TO = TO;
		if (TO == null) return;
		
		Properties prop = setProperties();
		Session session = Session.getInstance(prop, auth());
		MimeMessage message = setMessage(session, password);
		
		Transport.send(message);
	}
	
	private static Properties setProperties() {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", AppProperties.getInstance().getProperty("emailsmtp"));
        prop.put("mail.smtp.port", AppProperties.getInstance().getProperty("emailport"));
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
        return prop;
	}
	
	private static MimeMessage setMessage(Session session, String password) throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		message.setSubject("Account password");
		message.setText("Your password is: " + password);
		
		return message;
	}
	
	private static Authenticator auth() {
		return new Authenticator() {
			@Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(FROM, PASS);          
		    }  
		};
	}
}