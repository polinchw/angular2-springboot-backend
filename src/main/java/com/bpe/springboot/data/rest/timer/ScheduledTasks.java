package com.bpe.springboot.data.rest.timer;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bpe.springboot.data.rest.service.OrderProcessor;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private final Logger logger = Logger.getLogger(ScheduledTasks.class.getName());
    
    @Autowired
    OrderProcessor orderProcessor;

    /**
     * Check email for order updates.
     */
    //@Scheduled(fixedRate = 20000)
    public void checkOrders() {       
        logger.info("Checking orders.");
        orderProcessor.checkOrders();
    }
    
    /**
     * Check the database for new orders.
     */
    //@Scheduled(fixedRate=20000)
    public void sendOrders() {
    	logger.info("Sending orders...");
    	orderProcessor.sendOrders();
    }
}
