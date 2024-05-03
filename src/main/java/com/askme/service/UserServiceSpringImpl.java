package com.askme.service;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.askme.dao.UserDAOInt;
import com.askme.dto.UserDTO;
import com.askme.exception.DuplicateRecordException;
import com.askme.util.EmailBuilder;


@Service
public class UserServiceSpringImpl implements UserServiceInt {

	@Autowired
	private UserDAOInt dao;

//	@Autowired
//	private JavaMailSenderImpl mailSender;
 
	private static Logger log = Logger.getLogger(UserServiceSpringImpl.class);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(UserDTO dto) throws DuplicateRecordException {

		log.debug("Service User Add Start");

		UserDTO Exitsdto = dao.findByLogin(dto.getLogin());

		if (Exitsdto != null) {
			throw new DuplicateRecordException("User is already Exits");
		}

		log.debug("Service User Add End");
		return dao.add(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(UserDTO dto) {
		log.debug("Service User Delete Start");
		dao.delete(dto);

		log.debug("Service User Delete End");

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)

	public void update(UserDTO dto) throws DuplicateRecordException {

		log.debug("Service User update start");
		UserDTO Exitsdto = dao.findByLogin(dto.getLogin());

		if (Exitsdto != null && dto.getId() != Exitsdto.getId()) {
			throw new DuplicateRecordException("User is already Exits");
		}

		dao.update(dto);
		log.debug("Service User Update End");

	}

	@Transactional(readOnly = true)

	public UserDTO findByPK(long pk) {
		log.debug("User Service FindByPk Start");

		log.debug("User Service FindByPk End");

		return dao.findByPk(pk);
	}

	@Transactional(readOnly = true)
	public UserDTO findByLogin(String login) {
		log.debug("User Service FindBYLogin  start");

		log.debug("User Service FindBYLogin  End");

		return dao.findByLogin(login);
	}

	@Transactional(readOnly = true)
	public List<UserDTO> search(UserDTO dto) {
		// TODO Auto-generated method stub
		return dao.search(dto);
	}

	@Transactional(readOnly = true)
	public List search(UserDTO dto, int pageNo, int pageSize) {
		log.debug("User Service search start");

		log.debug("User Service search End");
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean changePassword(Long id, String oldPassword, String newPassword) {
		log.debug("Change User Service start");
		
		UserDTO dtoExist = findByPK(id);

		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {

			dtoExist.setPassword(newPassword);

			dao.update(dtoExist);

			HashMap<String, String> map = new HashMap<String, String>();

			map.put("login", dtoExist.getLogin());
			map.put("password", dtoExist.getPassword());
			map.put("firstName", dtoExist.getFirstName());
			map.put("lastName", dtoExist.getLastName());
			String message = EmailBuilder.getForgetPasswordMessage(map);
			  // Setup properties for the SMTP server
			String from = "onks8686192001@gmail.com"; 
            String password = "novnabvdrglqndil";
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
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(from));
	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dtoExist.getLogin()));
	            msg.setSubject("Password Change is successful for ORS Project SUNRAYS Technologies.");
	            msg.setText(message);
	            Transport.send(msg);
	            System.out.println("Email sent successfully!");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
			log.debug("Change User Service End");
			return true;
		} else {
			return false;
		}

	}

	@Transactional(readOnly = true)
	public UserDTO authenticate(UserDTO dto) {
		return dao.authenticate(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long registerUser(UserDTO dto) {
		long id = dao.add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		  // Setup properties for the SMTP server
		String from = "onks8686192001@gmail.com"; 
      String password = "novnabvdrglqndil";
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
          Message msg = new MimeMessage(session);
          msg.setFrom(new InternetAddress(from));
          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dto.getLogin()));
          msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies.");
          msg.setText(message);
          Transport.send(msg);
          System.out.println("Email sent successfully!");

      } catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Mail Sending Failed");
			log.error(e);
      }
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean forgetPassword(String login) {
		log.debug("Service forgetPassword Started");
		UserDTO dtoExist = dao.findByLogin(login);
		if (dtoExist != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("firstName", dtoExist.getFirstName());
			map.put("lastName", dtoExist.getLastName());
			map.put("login", dtoExist.getLogin());
			map.put("password", dtoExist.getPassword());

			String message = EmailBuilder.getForgetPasswordMessage(map);

			  // Setup properties for the SMTP server
			String from = "onks8686192001@gmail.com"; 
            String password = "novnabvdrglqndil";
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
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(from));
	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(login));
	            msg.setSubject("SunilOS ORS Password reset");
	            msg.setText(message);
	            Transport.send(msg);
	            System.out.println("Email sent successfully!");

	        } catch (MessagingException e) {
				e.printStackTrace();
				log.error(e);
				return false;
	        }
		} else {
			return false;
		}
		log.debug("Service forgetPassword End");
		return true;
	}

}
