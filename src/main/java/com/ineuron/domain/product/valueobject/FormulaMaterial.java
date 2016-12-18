package com.ineuron.domain.product.valueobject;

public class FormulaMaterial {
	
	private Integer id;
	private Integer formulaId;
	private Integer materialId;
	private float materialQuantity;
	private float materialPercent;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFormulaId() {
		return formulaId;
	}
	public void setFormulaId(Integer formulaId) {
		this.formulaId = formulaId;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public float getMaterialQuantity() {
		return materialQuantity;
	}
	public void setMaterialQuantity(float materialQuantity) {
		this.materialQuantity = materialQuantity;
	}
	public float getMaterialPercent() {
		return materialPercent;
	}
	public void setMaterialPercent(float materialPercent) {
		this.materialPercent = materialPercent;
	}
	

}
