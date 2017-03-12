package com.ineuron.domain.nlp.service;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ineuron.domain.nlp.valueobject.NLPResponse;
import com.ineuron.domain.nlp.valueobject.ProductSelection;

public class NLPService {

	private static NLPService nlpService;
	
	public static NLPService getInstance() {
		if (nlpService == null) {
			nlpService = new NLPService();
		}
		return nlpService;

	}

	public ProductSelection parseText(String text) {
		
		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target("http://localhost:9080/nlp").queryParam("text", text);

		javax.ws.rs.client.Invocation.Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();
		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    //System.out.println(response.readEntity(NLPResponse.class));
		    
		    ObjectMapper mapper = new ObjectMapper(); 
		    //NLPResponse nlpRes = mapper.readValue(response.getEntity().toString(), NLPResponse.class);
			NLPResponse nlpRes = response.readEntity(NLPResponse.class);
			return (ProductSelection) nlpRes.getValue(); 
		} else {
		    System.out.println("ERROR! " + response.getStatus());    
		    System.out.println(response.getEntity());
		}
		return null;
		
	}
	

}
