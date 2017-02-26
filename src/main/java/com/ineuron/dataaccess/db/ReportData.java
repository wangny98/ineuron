package com.ineuron.dataaccess.db;

import java.util.ArrayList;
import java.util.List;


public class ReportData {

	private String key;
	private List<List<Object>> values;

	//private static final Logger LOGGER = LoggerFactory.getLogger(ReportData.class);

	public ReportData(){
		this.values=new ArrayList<List<Object>>();
	}
	
	public void Clear(){
		this.key=null;
		this.values=null;
		this.values=new ArrayList<List<Object>>();
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<List<Object>> getValues() {
		return values;
	}

	public void setValues(List<List<Object>> values) {
		this.values = values;
	}
	
}
