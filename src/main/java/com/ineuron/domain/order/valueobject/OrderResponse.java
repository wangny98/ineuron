package com.ineuron.domain.order.valueobject;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.domain.order.entity.Order;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class OrderResponse {

	private Integer totalRecords;
	private List<Order> orders;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatus.class);

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	
}
