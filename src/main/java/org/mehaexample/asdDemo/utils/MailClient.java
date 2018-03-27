package org.mehaexample.asdDemo.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailClient
{
	public static void sendPasswordResetEmail(String toEmail, String registrationKey) {
        final String fromEmail = "te97399@gmail.com"; //requires valid gmail id
        final String password = "Test#786"; // correct password for gmail id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        String resetLink = "http://localhost:8080/alignWebsite/webapi/student-facing/password-create";
        sendEmail(session, toEmail,"Password Reset Email Northeastern Account", 
        		"Hello! \n \n" +
        		"Your Registration key is: " + registrationKey + "\n" +
        		"Password Reset link: " + resetLink + "\n" +
        		"Thanks," + "\n" + 
        		"Neu Team");
    }

	public static void sendRegistrationEmail(String toEmail, String registrationKey) {
        final String fromEmail = "te97399@gmail.com"; //requires valid gmail id
        final String password = "Test#786"; // correct password for gmail id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        String resetLink = "http://localhost:8080/alignWebsite/webapi/student-facing/password-create";
        sendEmail(session, toEmail,"Registration Email Northeastern Account",
        		"Hello! \n \n" +
        		"Your Registration key is: " + registrationKey + "\n" +
        		"Registeration link: " + resetLink + "\n" +
        		"Thanks," + "\n" + 
        		"Neu Team");
    }
	
    private static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);

            msg.setSubject(subject);

            msg.setText(body);

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


