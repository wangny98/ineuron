package com.ineuron.domain.production.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class ProductionPeriod {

	private Integer id;
	private Integer productId;
	private Integer productionPeriod;
	private float productionMinVolume;
	private float productionMaxVolume;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductionPeriod.class);

	public void addProductionPeriod(INeuronRepository repository) throws RepositoryException {
		repository.add("addProductionPeriod", this);
		
	}

	public void updateProductionPeriod(INeuronRepository repository) throws RepositoryException {
		repository.update("updateProductionPeriod", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteProductionPeriod", this);
		
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductionPeriod() {
		return productionPeriod;
	}

	public void setProductionPeriod(Integer productionPeriod) {
		this.productionPeriod = productionPeriod;
	}

	public float getProductionMinVolume() {
		return productionMinVolume;
	}

	public void setProductionMinVolume(float productionMinVolume) {
		this.productionMinVolume = productionMinVolume;
	}

	public float getProductionMaxVolume() {
		return productionMaxVolume;
	}

	public void setProductionMaxVolume(float productionMaxVolume) {
		this.productionMaxVolume = productionMaxVolume;
	}


	
}
