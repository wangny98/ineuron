package com.ineuron.dataaccess.db;

import java.util.List;

public class DataTablePageParameters {
	
	private Integer currentPage;
	private Integer startPosition;
	private Integer itemsPerPage;
	private String orderingOption;
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public String getOrderingOption() {
		return orderingOption;
	}
	public void setOrderingOption(String orderingOption) {
		this.orderingOption = orderingOption;
	}
	

}
