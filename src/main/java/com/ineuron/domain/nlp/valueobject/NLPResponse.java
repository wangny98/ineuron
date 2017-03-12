package com.ineuron.domain.nlp.valueobject;


public class NLPResponse {
	
	String originalText;
	ProductSelection value;
	String errorMessage;
	
	public NLPResponse(){}
	
	
	public String getOriginalText() {
		return originalText;
	}


	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public ProductSelection getValue() {
		return value;
	}
	public void setValue(ProductSelection value) {
		this.value = value;
	}
	
	

}
