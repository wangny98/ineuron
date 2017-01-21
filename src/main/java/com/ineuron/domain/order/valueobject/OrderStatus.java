package com.ineuron.domain.order.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class OrderStatus {

	private Integer id;
	private String name;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatus.class);

	public void addOrderStatus(INeuronRepository repository) throws RepositoryException {
		repository.add("addOrderStatus", this);
		
	}

	public void updateOrderStatus(INeuronRepository repository) throws RepositoryException {
		repository.update("updateOrderStatus", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteOrderStatus", this);
		
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
