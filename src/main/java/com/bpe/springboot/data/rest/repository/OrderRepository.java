package com.bpe.springboot.data.rest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bpe.springboot.data.rest.dao.OrderDao;
import com.bpe.springboot.data.rest.entity.Order;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, OrderDao {
	
    List<Order> findByStatus(@Param("status") String status);

	List<Order> findByDateSentIsNull();
}
