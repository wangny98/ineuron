package com.ineuron.api;

import java.util.List;

import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.product.entity.Product;

public class NLPSearchResponse {
	
	private List<Product> products;
	private Order order;
	
	/*
	 * to do: add Production & Device for NLP search
	 */
	
	public NLPSearchResponse(){}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	

}
