package com.bpe.springboot.data.rest.bean;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.apache.log4j.Logger;

/**
 * This program demonstrates how to download e-mail messages and save
 * attachments into files on disk.
 *
 * @author www.codejava.net
 *
 */
public class EmailAttachmentReceiver {
	
	private final static Logger logger = Logger.getLogger(EmailAttachmentReceiver.class.getName());
	
	private String saveDirectory;
	private String host;
	private String port;
	private String userName;
	private String password;  	

	public EmailAttachmentReceiver(String saveDirectory, String host, String port, String userName, String password) {
		this.saveDirectory = saveDirectory;
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Downloads new messages and saves attachments to disk if any.
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 */
	public void downloadEmailAttachments() {
		logger.info("host: "+host);
		logger.info("port: "+port);
		logger.info("userName: "+userName);
		Properties props = new Properties();
		props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.pop3.socketFactory.fallback", "false");
		props.put("mail.pop3.socketFactory.port", port);
		props.put("mail.pop3.port", port);
		props.put("mail.pop3.host", host);
		props.put("mail.pop3.user", userName);
		props.put("mail.store.protocol", "pop3");

		Session session = Session.getDefaultInstance(props,null);

		try {
			// connects to the message store
			Store store =  session.getStore("pop3");
			store.connect("pop.gmail.com", userName, password);
			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);

			// fetches new messages from server
			Message[] arrayMessages = folderInbox.getMessages();

			for (int i = 0; i < arrayMessages.length; i++) {
				Message message = arrayMessages[i];
				Address[] fromAddress = message.getFrom();
				String from = fromAddress[0].toString();
				String subject = message.getSubject();
				String sentDate = message.getSentDate().toString();

				String contentType = message.getContentType();
				String messageContent = "";

				// store attachment file name, separated by comma
				String attachFiles = "";

				if (contentType.contains("multipart")) {
					// content may contain attachments
					Multipart multiPart = (Multipart) message.getContent();
					int numberOfParts = multiPart.getCount();
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart
								.getBodyPart(partCount);
						if (Part.ATTACHMENT.equalsIgnoreCase(part
								.getDisposition())) {
							// this part is attachment
							String fileName = part.getFileName();
							attachFiles += fileName + ", ";
							part.saveFile(saveDirectory + File.separator
									+ fileName);
						} else {
							// this part may be the message content
							messageContent = part.getContent().toString();
						}
					}

					if (attachFiles.length() > 1) {
						attachFiles = attachFiles.substring(0,
								attachFiles.length() - 2);
					}
				} else if (contentType.contains("text/plain")
						|| contentType.contains("text/html")) {
					Object content = message.getContent();
					if (content != null) {
						messageContent = content.toString();
					}
				}

				// print out details of each message
				logger.info("Message #" + (i + 1) + ":");
				logger.info("\t From: " + from);
				logger.info("\t Subject: " + subject);
				logger.info("\t Sent Date: " + sentDate);
				logger.info("\t Message: " + messageContent);
				logger.info("\t Attachments: " + attachFiles);
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			logger.warn("No provider for pop3.",ex);
			ex.printStackTrace();
		} catch (MessagingException ex) {
			logger.warn("Could not connect to the message store",ex);			
		} catch (IOException ex) {
			logger.warn(ex.getMessage(), ex);
		}
	}
}
