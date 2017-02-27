package com.bpe.springboot.data.rest.service;

public interface OrderProcessor {

	/**
	 * Check email for order updates.
	 */
	void checkOrders();

	/**
	 * Updates the orders.  Read the contexts of the file for information.
	 * @param fileContents
	 */
	void updateOrder(byte[] fileContents);

	/**
	 * Check the database for new orders.  Write the new order to an out bound file.
	 */
	void sendOrders();

}