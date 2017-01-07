package com.ineuron;


import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class INeuronConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String environment;
    
    @NotEmpty
    @JsonProperty
    private String nlpEnabled;


    @JsonProperty
    public String getEnvironment() {
        return environment;
    }

    @JsonProperty
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @JsonProperty
	public String getNlpEnabled() {
		return nlpEnabled;
	}

    @JsonProperty
	public void setNlpEnabled(String nlpEnabled) {
		this.nlpEnabled = nlpEnabled;
	}
    
    
}
