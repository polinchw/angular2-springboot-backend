package com.bpe.springboot.data.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.bpe.springboot.data.rest.processor.FileRenameProcessor;

/**
 * Camel route for processing incoming order update files.
 * 
 * @author polinchakb
 *
 */
@Component
public class OrderUpdateRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {		
		from("file://{{order-update-inbox}}?delete=true")
		  .convertBodyTo(byte[].class, "iso-8859-1")
		  .to("bean:orderProcessor?method=updateOrder").process(new FileRenameProcessor())
		  .to("file://{{order-update-inbox-processed}}");
	}

}
