package com.bpe.springboot.data.rest.dao;

import java.util.List;

import com.bpe.springboot.data.rest.entity.Order;

public interface OrderDao {

	List<Order> findByStatus(String status);

	List<Order> findByDateSentIsNull();
	
	Order save(Order order);

}