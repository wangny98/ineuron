package com.ineuron.domain.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.order.valueobject.OrderStatus;
import com.ineuron.domain.production.entity.Production;
import com.ineuron.domain.device.entity.Device;


public class OrderService {

	@Inject
	INeuronRepository repository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderService.class);


	public Order createOrder(Order order) throws RepositoryException {
		
		String prefix="SO";
		
		Date today=new Date();
		order.setOrderDateTime(today);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH,0);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		order.setOrderDate(today);		
		Order o = repository.selectOne("getLatestOrderByDate", format.format(Calendar.getInstance().getTime()));
		
		int newOrderSN = 0;
		if (o != null)
			newOrderSN = o.getSerialNumber() + 1;
		else
			newOrderSN = 1;
		order.setSerialNumber(newOrderSN);
		
		//Order number format: SO-[yyyymmdd]-xxxx. currently set the max number per day is 9999
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String todayDateforNumber = dateFormat.format(today);
	    order.setOrderNumber(prefix+"-"+todayDateforNumber+"-"+String.format("%04d", newOrderSN));	
		
	    //set the status to init (id=1)
	    order.setStatusId(1);
	    
	    //valid order is 1; deleted order is -1;
	    order.setValidFlag(1);
	    
	    //deprecated
	    //TO-DO: calculate the production period based on productionTicket's total period of this productId
	    //1. calculate the total production period of that productId in productionTicket table 
	    //2. compare this order's amount to specific device (type='production')'s volume; 
	    //if btw min and max, then use the total production period (add orders together
	    //if below min, use the total; if beyond max, use the mod
	    //3. set the productionperiod value of Order object
	    
		order.addOrder(repository);
		return order;
	}

	/*public Integer getOrderProductionPeriod(Integer productId) throws RepositoryException{
				
	}*/
	
	
	public Order updateOrder(Order order) throws RepositoryException {
		order.updateOrder(repository);
		return order;
	}
	

	public void updateOrderForStatus(Order order) throws RepositoryException {
		repository.update("updateOrderForStatus", order);
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
	
	
	public List<OrderStatus> getOrderStatusList() throws RepositoryException {
		List<OrderStatus> orderStatusList = repository.select("getOrderStatus", null);
		
		return orderStatusList;
	}


}
