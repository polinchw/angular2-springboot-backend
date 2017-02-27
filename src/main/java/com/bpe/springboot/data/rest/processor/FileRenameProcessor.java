package com.bpe.springboot.data.rest.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileRenameProcessor implements Processor {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");

	public void process(Exchange exchange) throws Exception {
		String filename = new String();		
		filename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);		
		exchange.getIn().setHeader(Exchange.FILE_NAME, filename+"."+dateFormat.format(new Date()));
	}

}
