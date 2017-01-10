package com.ineuron.api;

import javax.ws.rs.core.HttpHeaders;

import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.domain.user.service.SecurityService;

public class INeuronResponse {
	
	String apiToken;
	Object value;
	
	public INeuronResponse(){}
	
	public INeuronResponse(SecurityService securityService, HttpHeaders httpHeader, boolean isDebug) throws InvalidAPITokenException{
		apiToken = securityService.validateAndUpdateApiToken(httpHeader, isDebug);
		if(apiToken == null){
			throw new InvalidAPITokenException("Failed to validate And Update ApiToken!", null);
		}
	}
	
	public String getApiToken() {
		return apiToken;
	}
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	

}
