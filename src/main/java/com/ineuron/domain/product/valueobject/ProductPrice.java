package com.ineuron.domain.product.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class ProductPrice {

	private Integer id;
	private Integer productId;
	private float price;
	private String unit;
	private float cost;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPrice.class);

	public void addProductPrice(INeuronRepository repository) throws RepositoryException {
		repository.add("addProductPrice", this);
		
	}

	public void updateProductPrice(INeuronRepository repository) throws RepositoryException {
		repository.update("updateProductPrice", this);
	}

	

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

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
	
	

}
