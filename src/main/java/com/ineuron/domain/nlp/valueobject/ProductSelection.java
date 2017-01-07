package com.ineuron.domain.nlp.valueobject;

import java.util.List;

public class ProductSelection {
	//产品
	private String productName;
	//颜色
	private String color;
	//形态
	private String form;
	//功能
	private String function;
	//用途
	private String purpose;	
	//品质
	private String quality;
	//其他属性
	private List<String> otherAttributes;
	
	public String toString(){
		
		StringBuilder ps = new StringBuilder();
		ps.append("productName = ")
			.append(productName)
			.append("\n")
			.append("color = ")
			.append(color)
			.append("\n")
			.append("form = ")
			.append(form)
			.append("\n")
			.append("purpose = ")
			.append(purpose)
			.append("\n")
			.append("function = ")
			.append(function)
			.append("\n")
			.append("quality = ")
			.append(quality)
			.append("\n")
			.append("otherAttributes = ");
		
		if(otherAttributes != null){
			for(String attr : otherAttributes){
				ps.append(attr).append(",");
			}
		}
		
		return ps.toString();
		
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public List<String> getOtherAttributes() {
		return otherAttributes;
	}
	public void setOtherAttributes(List<String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}
	

}
