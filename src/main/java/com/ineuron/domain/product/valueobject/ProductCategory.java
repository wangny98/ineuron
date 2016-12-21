package com.ineuron.domain.product.valueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;

public class ProductCategory {

	private Integer id;
	private String name;
	private String code;
	private String description;
	private String characters;
	private String techParameters;
	private String scope;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategory.class);

	public void addProductCategory(INeuronRepository repository) throws RepositoryException {
		repository.add("addProductCategory", this);
		
	}

	/*public void updateProductCategory(INeuronRepository repository) throws RepositoryException {
		repository.updateProductCategory(this);
	}

	
	public void deleteProductCategory(INeuronRepository repository) throws RepositoryException {
		repository.deleteProductCategory(this);
		
	}*/
	
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

	public String getCharacters() {
		return characters;
	}
	
	public void setCharacters(String characters) {
		this.characters = characters;
	}
	
	public String getTechParameters() {
		return techParameters;
	}
	
	public void setTechParameters(String techParameters) {
		this.techParameters = techParameters;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
}
