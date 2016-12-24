package com.ineuron.domain.product.valueobject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.INeuronException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class Attribute {

	private Integer id;
	private String name;
	private String code;
	private String description;
	private Integer attributeCategoryId;
	
	private AttributeCategory attributeCategory;

	private static final Logger LOGGER = LoggerFactory.getLogger(Attribute.class);

	public void addAttribute(INeuronRepository repository) throws RepositoryException {
		repository.add("addAttribute", this);
		
	}

	public void updateAttribute(INeuronRepository repository) throws RepositoryException {
		repository.update("updateAttribute", this);
	}

	
	public void deleteAttibute(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteAttribute", this);
		
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
		
		attributeCategory = repository.selectOne("getAttributeCategoryById", attributeCategoryId);	
	
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAttributeCategoryId() {
		return attributeCategoryId;
	}

	public void setAttributeCategoryId(Integer attributeCategoryId) {
		this.attributeCategoryId = attributeCategoryId;
	}
	
	public AttributeCategory getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttributeCategory(AttributeCategory attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

}
