package com.ineuron.domain.order.entity;

import java.util.Date;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.order.valueobject.OrderStatus;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.valueobject.ProductPrice;
import com.ineuron.domain.user.entity.User;

public class Order {

	private Integer id;
	private Integer serialNumber;
	private String orderNumber;
	private Integer productId;
	private Integer userId;
	private Integer productionPeriod;
	private float amount;
	private float total;
	private Integer statusId;
	private String customizedInfo;
	private Date orderDate;
	private Date orderDateTime;
	private Date deliveryDate;
	private String customer;
	private float payment;
	private String picFile;
	private Integer validFlag;

	private Product product;
	private ProductPrice productPrice;
	private User user;
	private OrderStatus orderStatus;

	// private static final Logger LOGGER = LoggerFactory.getLogger("Product");

	public void addOrder(INeuronRepository repository)
			throws RepositoryException {
		repository.add("addOrder", this);

	}

	public void updateOrder(INeuronRepository repository)
			throws RepositoryException {
		repository.update("updateOrder", this);
	}

	public void deleteAttibute(INeuronRepository repository)
			throws RepositoryException {
		repository.delete("deleteOrder", this);

	}

	public void init(INeuronRepository repository) throws RepositoryException {
		if (productId != null) {
			product = repository.selectOne("getProductById", productId);
			if (product != null) {
				product.initForProductPrice(repository);
			}
			
			productPrice=repository.selectOne("getProductPriceByProductId", productId);
		}

		if (userId != null)
			user = repository.selectOne("getUserById", userId);

		if (statusId != null)
			orderStatus=repository.selectOne("getOrderStatusById", statusId);
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

	public String getCustomizedInfo() {
		return customizedInfo;
	}

	public void setCustomizedInfo(String customizedInfo) {
		this.customizedInfo = customizedInfo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
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

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPicFile() {
		return picFile;
	}

	public void setPicFile(String picFile) {
		this.picFile = picFile;
	}

	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

}
