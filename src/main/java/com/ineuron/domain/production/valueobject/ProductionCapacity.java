package com.ineuron.domain.production.valueobject;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class ProductionCapacity {

	private Integer id;
	private Integer deviceId;
	private Date date;
	private Integer consumedPeriod;
	private String productionTaskList;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductionCapacity.class);

	public void addProductionPeriod(INeuronRepository repository) throws RepositoryException {
		repository.add("addProductionCapacity", this);
		
	}

	public void updateProductionPeriod(INeuronRepository repository) throws RepositoryException {
		repository.update("updateProductionCapacity", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteProductionCapcity", this);
		
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getConsumedPeriod() {
		return consumedPeriod;
	}

	public void setConsumedPeriod(Integer consumedPeriod) {
		this.consumedPeriod = consumedPeriod;
	}

	public String getProductionTaskList() {
		return productionTaskList;
	}

	public void setProductionTaskList(String productionTaskList) {
		this.productionTaskList = productionTaskList;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	
}
