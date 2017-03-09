package com.ineuron.domain.product.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPackageType {

	private Integer id;
	private float price;
	private float cost;
	private Integer volume;
	private String unit;
	private String type;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPackageType.class);



	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	

}
