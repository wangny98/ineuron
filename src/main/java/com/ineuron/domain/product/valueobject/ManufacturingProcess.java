package com.ineuron.domain.product.valueobject;

public class ManufacturingProcess {
	
	private Integer stepId;
	private Integer productId;
	private Integer orderId;
	private Integer operationId;
	private Integer materialId;
	private Float materialQuantity;
	private Float errorRange;

	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
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
	public Integer getOperationId() {
		return operationId;
	}
	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
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
	public Float getErrorRange() {
		return errorRange;
	}
	public void setErrorRange(Float errorRange) {
		this.errorRange = errorRange;
	}
	

}
