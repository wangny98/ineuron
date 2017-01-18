package com.ineuron.domain.order.entity;

import java.util.Date;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class Order {

	private Integer id;
	private Integer serialNumber;
	private String orderNumber;
	private Integer productId;
	private Integer userId;
	private Integer productionPeriod;
	private float amount;
	private String status;
	private String customizedInfo;
	private String orderDate;
	private Date orderDateTime;
	private String deliveryDate;
	private String customer;
	private float payment;
	private Integer validFlag;

	//private static final Logger LOGGER = LoggerFactory.getLogger("Product");

	public void addOrder(INeuronRepository repository) throws RepositoryException {
		repository.add("addOrder", this);
		
	}

	public void updateOrder(INeuronRepository repository) throws RepositoryException {
		repository.update("updateOrder", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteOrder", this);
		
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{	
	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductionPeriod() {
		return productionPeriod;
	}

	public void setProductionPeriod(Integer productionPeriod) {
		this.productionPeriod = productionPeriod;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomizedInfo() {
		return customizedInfo;
	}

	public void setCustomizedInfo(String customizedInfo) {
		this.customizedInfo = customizedInfo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public Date getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(Date orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	
	
}
