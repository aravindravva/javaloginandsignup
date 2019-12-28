package loginpage;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class mail {


	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	public static void main(String args[]) throws AddressException,
			MessagingException {

		mail mail = new mail();

		mail.setMailServerProperties();
		mail.createEmailMessage();
		mail.sendEmail();
	}

	public void setMailServerProperties() {

		String emailPort = "587";//gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.ssl.trust","*");
	
		emailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		emailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");   
		emailProperties.setProperty("mail.smtp.port", "465");   
		emailProperties.setProperty("mail.smtp.socketFactory.port", "465"); 


	}

	public void createEmailMessage() throws AddressException,
			MessagingException {
		String[] toEmails = { "rvsaravindguptha@gmail.com" };
		String emailSubject = "Java Email";
		String emailBody = "This is an email sent by JavaMail api hard worked by me .";

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");//for a html email
		//emailMessage.setText(emailBody);// for a text email

	}

	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "bookmanagementrit@gmail.com";//just the id alone without @gmail.com
		String fromUserEmailPassword = "BookManagement";

		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}

}
