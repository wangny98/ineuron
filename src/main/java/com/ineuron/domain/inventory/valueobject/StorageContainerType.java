package com.ineuron.domain.inventory.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageContainerType {

	private Integer id;
	private Integer volume;
	private String type;
	private Integer productPackageId;

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageContainerType.class);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductPackageId() {
		return productPackageId;
	}

	public void setProductPackageId(Integer productPackageId) {
		this.productPackageId = productPackageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	
}
