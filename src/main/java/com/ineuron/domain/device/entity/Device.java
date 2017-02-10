package com.ineuron.domain.device.entity;

import java.util.List;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
//import com.ineuron.domain.device.repository.ProductionRepository;

public class Device {

	private Integer id;
	private String name;
	private Float minVolume;
	private Float maxVolume;
	private String type;
	private Integer status;
	private String description;
	//private static final Logger LOGGER = LoggerFactory.getLogger("Device");

	public void addDevice(INeuronRepository repository) throws RepositoryException {
		repository.add("addDevice", this);
	}

	public void updateDevice(INeuronRepository repository) throws RepositoryException {
		repository.update("updateDevice", this);
	}
	
	public void deleteDevice(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteDevice", this);
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
			
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(Float minVolume) {
		this.minVolume = minVolume;
	}

	public Float getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(Float maxVolume) {
		this.maxVolume = maxVolume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
		
}
