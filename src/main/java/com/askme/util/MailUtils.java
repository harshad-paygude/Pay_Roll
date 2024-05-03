package com.askme.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	public static void sendEmail(String to,String userName,String usernewPassword) {
        // Email configuration
        String from = "onks8686192001@gmail.com"; 
        String password = "novnabvdrglqndil"; 
        String subject="Password Reset Successfully";
        
        String htmlContent = "\n"
                + "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Password Reset Successful</title>\n"
                + "</head>\n"
                + "\n"
                + "<body style=\"font-family: 'Arial', sans-serif; background-color: #f4f4f4; background-image: url('/KIPL-ART-WORK/WebContent/META-INF/resources/images/krish-new-logo.jpg'); background-size: cover;\">\n"
                + "\n"
                + "    <div style=\"max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; background-color: #fff;\">\n"
                + "        <img src='/WebContent/WEB-INF/resources/images/krish-new-logo.jpg' alt='Company Logo' style='max-width: 100%; height: auto; margin-bottom: 20px;'>\n"
                + "\n"
                + "        <h2 style=\"color: #007BFF;\">Your password has been changed successfully.</h2>\n"
                + "\n"
                + "        <p>Hello [UserFirstName],</p>\n"
                + "        <p>Your password has been changed successfully. If you didn't make this change, please contact support.</p>\n"
                + "\n"
                + "        <h3 style=\"color: #007BFF;\">Changed Password Details:</h3>\n"
                + "        <p><strong>New Password:</strong> [usernewPassword]</p>\n"
                + "\n"
                + "        <h3 style=\"color: #007BFF;\">Thank you for choosing us!</h3>\n"
                + "        <p> If you have any questions, feel free to contact our support team.</p>\n"
                + "        <p style=\"font-size: 0.8em;\">This is an automated message. Please do not reply to this email.</p>\n"
                + "\n"
                + "        <p style=\"margin-top: 20px; font-size: 0.8em; text-align: center;\">&copy; 2024 KRISH INTRATRADE PVT LTD. All rights reserved.</p>\n"
                + "\n"
                + "    </div>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";

        // Setup properties for the SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "587"); 
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Create a Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            
            htmlContent = htmlContent.replace("[UserFirstName]", userName);
            htmlContent = htmlContent.replace("[usernewPassword]", usernewPassword);
            message.setContent(htmlContent, "text/html");
            
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }	
}
