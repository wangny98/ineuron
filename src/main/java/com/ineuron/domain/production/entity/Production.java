package com.ineuron.domain.production.entity;

import java.util.List;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
//import com.ineuron.domain.production.repository.ProductionRepository;

public class Production {

	private Integer id;
	private Integer orderId;
	private Integer productId;
	private Integer manufacturingProcessOrderId;
	private Integer deviceId;
	private Integer productionPeriod;
	private Integer materialId;
	private Float materialQuantity;
	private Integer status;
	//private static final Logger LOGGER = LoggerFactory.getLogger("Production");

	public void addProduction(INeuronRepository repository) throws RepositoryException {
		repository.add("addProduction", this);
	}

	public void updateProduction(INeuronRepository repository) throws RepositoryException {
		repository.update("updateProduction", this);
	}
	
	public void deleteProduction(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteProduction", this);
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getProductionPeriod() {
		return productionPeriod;
	}

	public void setProductionPeriod(Integer productionPeriod) {
		this.productionPeriod = productionPeriod;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Float getMaterialQuantity() {
		return materialQuantity;
	}

	public void setMaterialQuantity(Float materialQuantity) {
		this.materialQuantity = materialQuantity;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getManufacturingProcessOrderId() {
		return manufacturingProcessOrderId;
	}

	public void setManufacturingProcessOrderId(
			Integer manufacturingProcessOrderId) {
		this.manufacturingProcessOrderId = manufacturingProcessOrderId;
	}
		
}
