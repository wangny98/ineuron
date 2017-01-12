package com.ineuron.domain.nlp.valueobject;

import java.util.ArrayList;
import java.util.List;

public class ProductSelection {
	//产品
	private String productName;
	//颜色
	private List<String> colors;
	//形态
	private List<String> forms;
	//功能
	private List<String> functions;
	//用途
	private List<String> scopes;	
	//品质
	private List<String> qualities;
	//其他属性
	private List<String> otherAttributes;
	
	public String toString(){
		
		StringBuilder ps = new StringBuilder();
		ps.append("productName = ")
			.append(productName)
			.append("\n")
			.append("color = ");
		
		if(colors != null){
			for(String color : colors){
				ps.append(color).append(",");
			}
		}

		ps.append("\n")
			.append("form = ");
		if(forms != null){
			for(String form : forms){
				ps.append(form).append(",");
			}
		}	

		ps.append("\n")
			.append("scope = ");
		if(scopes != null){
			for(String scope : scopes){
				ps.append(scope).append(",");
			}
		}
		
		ps.append("\n")
			.append("function = ");	
		if(functions != null){
			for(String function : functions){
				ps.append(function).append(",");
			}
		}
		
		ps.append("\n")
			.append("quality = ");
		if(qualities != null){
			for(String quality : qualities){
				ps.append(quality).append(",");
			}
		}
		
		ps.append("\n")
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
	
	public List<String> getColors() {
		return colors;
	}

	public void addColor(String color) {
		if(colors == null){
			colors = new ArrayList<String>();
		}
		colors.add(color);
	}

	public List<String> getForms() {
		return forms;
	}

	public void addForm(String form) {
		if(forms == null){
			forms = new ArrayList<String>();
		}
		forms.add(form);
	}

	public List<String> getFunctions() {
		return functions;
	}

	public void addFunction(String function) {
		if(functions == null){
			functions = new ArrayList<String>();
		}
		functions.add(function);
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void addScope(String scope) {
		if(scopes == null){
			scopes = new ArrayList<String>();
		}
		scopes.add(scope);
	}

	public List<String> getQualities() {
		return qualities;
	}

	public void addQuality(String quality) {
		if(qualities == null){
			qualities = new ArrayList<String>();
		}
		qualities.add(quality);
	}

	public List<String> getOtherAttributes() {
		return otherAttributes;
	}
	public void setOtherAttributes(List<String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}
	

}
