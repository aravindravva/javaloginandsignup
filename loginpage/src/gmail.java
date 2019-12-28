

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import loginpage.mail;


@WebServlet("/gmail")
public class gmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     public gmail() {
        super();
        
    }
    Properties emailProperties;
 	Session mailSession;
 	MimeMessage emailMessage;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		gmail mail = new gmail();
		String email=request.getParameter("gmail");
		HttpSession s=request.getSession(true);
		s.setAttribute("mail",email);

		mail.setMailServerProperties();
		try {
			mail.createEmailMessage(email);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mail.sendEmail();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("otpverification.html");
				
		
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
	public void createEmailMessage(String email) throws AddressException,
	MessagingException, ClassNotFoundException, SQLException
	{
		String[] toEmails = { email };
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    String emailSubject = "Java Email";
	    String emailBody = "Yout OTP is "+number;
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","aravind");
	    PreparedStatement ps1=con.prepareStatement("insert into otp values(?,?)");
	    //System.out.println("after"+email);
	    ps1.setString(1,email);
	    ps1.setInt(2,number);
	    ps1.executeUpdate();	
	    mailSession = Session.getDefaultInstance(emailProperties, null);
	    emailMessage = new MimeMessage(mailSession);
	    for (int i = 0; i < toEmails.length; i++)
        {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }
 
	    emailMessage.setSubject(emailSubject);
	    emailMessage.setContent(emailBody, "text/html");//for a html email
//emailMessage.setText(emailBody);// for a text email

}
	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "youremail";//just the id alone without @gmail.com
		String fromUserEmailPassword = "password";
		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
		
	}





	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
