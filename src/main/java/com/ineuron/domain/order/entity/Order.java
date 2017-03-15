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
	private Date estimatedDeliveryDate;
	private float amount;
	private float productCharge;
	private float productCost;
	private float packageCharge;
	private float packageCost;
	private float labelPackageCharge;
	private float labelPackageCost;
	private float totalCharge;
	private Integer productPackageTypeId;
	private Integer packageAmount;
	private Integer storageContainerAmount;
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

	
	public Integer getPackageAmount() {
		return packageAmount;
	}

	public void setPackageAmount(Integer packageAmount) {
		this.packageAmount = packageAmount;
	}

	public Integer getStorageContainerAmount() {
		return storageContainerAmount;
	}

	public void setStorageContainerAmount(Integer storageContainerAmount) {
		this.storageContainerAmount = storageContainerAmount;
	}

	public Integer getProductPackageTypeId() {
		return productPackageTypeId;
	}

	public void setProductPackageTypeId(Integer productPackageTypeId) {
		this.productPackageTypeId = productPackageTypeId;
	}

	public float getProductCharge() {
		return productCharge;
	}

	public void setProductCharge(float productCharge) {
		this.productCharge = productCharge;
	}

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	public float getPackageCharge() {
		return packageCharge;
	}

	public void setPackageCharge(float packageCharge) {
		this.packageCharge = packageCharge;
	}

	public float getPackageCost() {
		return packageCost;
	}

	public void setPackageCost(float packageCost) {
		this.packageCost = packageCost;
	}

	public float getLabelPackageCharge() {
		return labelPackageCharge;
	}

	public void setLabelPackageCharge(float labelPackageCharge) {
		this.labelPackageCharge = labelPackageCharge;
	}

	public float getLabelPackageCost() {
		return labelPackageCost;
	}

	public void setLabelPackageCost(float labelPackageCost) {
		this.labelPackageCost = labelPackageCost;
	}

	public float getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(float totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

}
