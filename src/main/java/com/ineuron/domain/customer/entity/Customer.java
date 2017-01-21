package com.ineuron.domain.customer.entity;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class Customer {

	private Integer id;
	private String name;
	private Integer size;
	private String location;

	//private static final Logger LOGGER = LoggerFactory.getLogger("Product");

	public void addCustomer(INeuronRepository repository) throws RepositoryException {
		repository.add("addCustomer", this);
		
	}

	public void updateCustomer(INeuronRepository repository) throws RepositoryException {
		repository.update("updateCustomer", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteCustomer", this);
		
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
