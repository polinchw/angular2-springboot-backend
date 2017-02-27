package com.bpe.springboot.data.rest.processor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.log4j.Logger;

/**
 * Picks up an order file and emails it.
 * 
 * @author polinchakb
 *
 */
public class SendEmailProcessor implements Processor {
	
	private final static Logger logger = Logger.getLogger(SendEmailProcessor.class.getName());

	private CamelContext context;
		
    private String smtpHost;	
	private String smtpUsername;
	private String smtpPassword;	
	private String toEmail;		

	public SendEmailProcessor(CamelContext context, String smtpHost, String smtpUsername, String smtpPassword,
			String toEmail) {		
		this.context = context;
		this.smtpHost = smtpHost;
		this.smtpUsername = smtpUsername;
		this.smtpPassword = smtpPassword;
		this.toEmail = toEmail;
	}

	/**
	 * Get the file object from the exchange and email as an attachment.
	 */
	@Override
	public void process(Exchange ex) throws Exception {
		logger.info("Emailing the order....");
		logger.info("toEmail: "+toEmail);
		File file = ex.getIn().getBody(File.class);
		// create an exchange with a normal body and attachment to be produced as email
		Endpoint endpoint = context.getEndpoint("smtps://"+smtpUsername+"@"+smtpHost+"?password="+smtpPassword);
		 
		// create the exchange with the mail message that is multipart with a file and a Hello World text/plain message.
		Exchange exchange = endpoint.createExchange();
		Message in = exchange.getIn();
		in.setBody("New Orders are attached.");
		Map<String, Object> headers = new HashMap<>();
		headers.put("from", this.smtpUsername+"@gmail.com");
		headers.put("to", this.toEmail);
		headers.put("subject", "New Orders");
		headers.put("contentType", "text/plain;charset=UTF-8");
		in.setHeaders(headers);
		in.setBody("See attachement for new orders.");

		in.addAttachment("order.txt", new DataHandler(new FileDataSource(file)));			
		 
		// create a producer that can produce the exchange (= send the mail)
		Producer producer = endpoint.createProducer();
		// start the producer
		producer.start();
		// and let it go (processes the exchange by sending the email)
		producer.process(exchange);
        logger.info("Done sending emails.");		
	}
}
