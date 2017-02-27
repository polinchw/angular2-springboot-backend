package com.bpe.springboot.data.rest.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bpe.springboot.data.rest.dao.OrderDao;
import com.bpe.springboot.data.rest.entity.Order;
import com.bpe.springboot.data.rest.service.OrderProcessor;

/**
 * A bean for creating and updating orders based on the context found in a file.
 * 
 * @author polinchakb
 *
 */
public class TextFileOrderProcessor implements OrderProcessor {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
	
	private final static Logger logger = Logger.getLogger(TextFileOrderProcessor.class.getName());
	
	private OrderDao orderDao;
        
    private EmailAttachmentReceiver emailReciever;
    
    private String createOrderOutbox;  

    @Autowired
    public TextFileOrderProcessor(CamelContext camelContext, 
    		                      OrderDao orderDao, 
    		                      EmailAttachmentReceiver emailAttachmentReceiver, 
    		                      String createOrderOutbox,
    		                      String smtpHost,
    		                      String smtpUsername,
    		                      String smtpPassword,
    		                      String toEmail) {
    	
    	this.orderDao = orderDao;
    	this.emailReciever = emailAttachmentReceiver;
    	this.createOrderOutbox = createOrderOutbox;
    }

	@Override
	public void checkOrders(){
		emailReciever.downloadEmailAttachments();
	}

	@Override
	public void updateOrder(byte[] fileContents) {
		logger.info("updateOrder invoked");
		String decrypted = new String(fileContents);
		logger.info("file contents: "+decrypted);
	}

    @Override
	public void sendOrders() {
       List<Order> orders = orderDao.findByDateSentIsNull();
       StringBuilder content = new StringBuilder();
       for(Order order : orders) {
           content.append("Order : "+order.getId()+" information: "+order.getOrderInfo()); 
           content.append("\n");
           order.setDateSent(Calendar.getInstance().getTime());
           orderDao.save(order);
       }
       if(orders.size() == 0) {
    	   logger.info("There are no new orders to send.");
    	   return;
       }
       BufferedWriter bw = null;
       try {			
            String timestamp = dateFormat.format(Calendar.getInstance().getTime());
			File file = new File(createOrderOutbox+"/order.txt."+timestamp);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content.toString());
			bw.close();

			logger.info("Done writing order.");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}    
}
