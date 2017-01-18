package com.ineuron.domain.order.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.nlp.service.NLPService;
import com.ineuron.domain.order.entity.Order;

public class OrderService {

	@Inject
	INeuronRepository repository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderService.class);


	public Order createOrder(Order order) throws RepositoryException {
		
		String prefix="SO";
		
		Date today=new Date();
		order.setOrderDateTime(today);
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String todayDate = dateFormat.format(today); 
		order.setOrderDate(todayDate);		
		Order o = repository.selectOne("getLatestOrderByDate", todayDate);
		
		int newOrderSN = 0;
		if (o != null)
			newOrderSN = o.getSerialNumber() + 1;
		else
			newOrderSN = 1;
		order.setSerialNumber(newOrderSN);
		
		//Order number format: SO-[todayDate]-xxxx. currently set the max number per day is 9999
	    order.setOrderNumber(prefix+"-"+todayDate+"-"+String.format("%04d", newOrderSN));	
		
	    order.setStatus("init");
	    //valid order is 1; deleted order is -1;
	    order.setValidFlag(1);
	    
		order.addOrder(repository);;
		return order;
	}

	public Order updateOrder(Order order) throws RepositoryException {
		order.updateOrder(repository);
		return order;
	}

	public void deleteOrder(Order order) throws RepositoryException {
		repository.delete("deleteOrder", order);
	}

	public List<Order> getOrderList() throws RepositoryException {
		List<Order> orderList = repository.select("getOrders", null);
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).init(repository);
		}
		return orderList;
	}


	public Order getOrderById(Integer orderId) throws RepositoryException {
		Order order = repository.selectOne("getOrderById", orderId);
		if (order != null) {
			order.init(repository);
		}
		return order;
	}

	public Order getOrderByName(String name) throws RepositoryException {
		Order order = repository.selectOne("getOrderByName", name);
		//System.out.println("get order by name in service: success");
		if(order!=null) order.init(repository);
		return order;
	}


}
