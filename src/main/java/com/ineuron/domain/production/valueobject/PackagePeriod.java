package com.ineuron.domain.production.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class PackagePeriod {

	private Integer id;
	private Integer period;
	private String unit;
	private Integer volume;

	private static final Logger LOGGER = LoggerFactory.getLogger(PackagePeriod.class);

	public void addProductionPeriod(INeuronRepository repository) throws RepositoryException {
		repository.add("addPackagePeriod", this);
		
	}

	public void updateProductionPeriod(INeuronRepository repository) throws RepositoryException {
		repository.update("updatePackagePeriod", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deletePackagePeriod", this);
		
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	
}
