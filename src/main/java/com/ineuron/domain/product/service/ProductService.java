package com.ineuron.domain.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.product.entity.Formula;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.valueobject.AttributeCategory;
import com.ineuron.domain.product.valueobject.Attribute;
import com.ineuron.domain.product.valueobject.ProductCategory;
import com.ineuron.domain.product.repository.ProductRepository;
import com.ineuron.domain.product.valueobject.ManufacturingProcess;
import com.ineuron.domain.product.valueobject.Material;
import com.ineuron.domain.product.valueobject.Operation;

public class ProductService {

	@Inject
	INeuronRepository repository;
	
	@Inject
	ProductRepository productRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
	
	public ProductCategory createProductCategory(ProductCategory productCategory) throws RepositoryException {
		productCategory.addProductCategory(repository);
		return productCategory;
	}
	
	public List<ProductCategory> getProductCategoryList() throws RepositoryException{		
		List<ProductCategory> productCategoryList = repository.select("getProductCategories", null);
		return productCategoryList;
	}
	
	public ProductCategory getProductCategoryByName(String name) throws RepositoryException{		
		ProductCategory productCategory = repository.selectOne("getProductCategoryByName", name);
		return productCategory;
	}
	
	public ProductCategory getProductCategoryById(Integer id) throws RepositoryException{		
		ProductCategory productCategory = repository.selectOne("getProductCategoryById", id.toString());
		return productCategory;
	}
	
	public ProductCategory getProductCategoryByCode(String code) throws RepositoryException{		
		ProductCategory productCategory = repository.selectOne("getProductCategoryByCode", code);
		return productCategory;
	}

	public ProductCategory updateProductCategory(ProductCategory productCategory) throws RepositoryException {
		repository.update("updateProductCategory", productCategory);
		return productCategory;
	}
	
	public void deleteProductCategory(ProductCategory productCategory) throws RepositoryException {
		repository.delete("deleteProductCategory", productCategory);
		//return productCategory;
	}
	
	public Product createProduct(Product product) throws RepositoryException {
		product.addProduct(repository);
		return product;
	}
	
	public Product updateProduct(Product product) throws RepositoryException {
		product.updateProduct(repository);
		return product;
	}
	
	public void deleteProduct(Product product) throws RepositoryException {
	    repository.delete("deleteProduct", product);
	}
	
	public List<Product> getProductList() throws RepositoryException{		
		List<Product> productList = repository.select("getProducts", null);
		for(int i = 0; i < productList.size(); i++)  
        {  
			productList.get(i).init(repository);
        } 
		return productList;
	}
	
	public List<Product> getProductListByCategory(Integer productCategoryId) throws RepositoryException{		
		List<Product> productList = repository.select("getProductByCategory", productCategoryId);
		for(int i = 0; i < productList.size(); i++)  
        {  
			productList.get(i).init(repository);
        } 
		return productList;
	}
	
	public Product getProductByName(String name) throws RepositoryException{		
		Product product = repository.selectOne("getProductByName", name);
		return product;
	}
	
	public void createAttribute(Attribute attribute) throws RepositoryException {
		attribute.addAttribute(repository);
	}
	
	public void updateAttribute(Attribute attribute) throws RepositoryException {
		attribute.updateAttribute(repository);
	}
	
	public void deleteAttribute(Attribute attribute) throws RepositoryException {
		attribute.deleteAttibute(repository);
	}

	public List<Attribute> getAttributeList() throws RepositoryException{		
		List<Attribute> attributeList = repository.select("getAttributes", null);
		for(int i = 0; i < attributeList.size(); i++)  
        {  
			attributeList.get(i).init(repository);
        } 
		return attributeList;
	}
	
	public List<Attribute> getAttributesByCategoryId(Integer attributeCategoryId) throws RepositoryException{		
		List<Attribute> attributeList = repository.select("getAttributesByCategoryId", attributeCategoryId);
		return attributeList;
	}
	
	public Attribute getAttributeByName(String name) throws RepositoryException{		
		Attribute attribute = repository.selectOne("getAttributeByName", name);
		return attribute;
	}

	public List<AttributeCategory> getAttributeCategoryList() throws RepositoryException{		
		List<AttributeCategory> attributeCategoryList = repository.select("getAttributeCategories", null);
		return attributeCategoryList;
	}
	
	public AttributeCategory getAttributeCategoryById(Integer attributeCategoryId) throws RepositoryException{		
		AttributeCategory attributeCategory = repository.selectOne("getAttributeCategoryById", attributeCategoryId);
		return attributeCategory;
	}

	public List<ManufacturingProcess> getProcessList(Integer productId) throws RepositoryException {
		List<ManufacturingProcess> processes = repository.select("getProcesses", productId);
		return processes;
	}

	public List<Operation> getOperations() throws RepositoryException {
		List<Operation> operationList = repository.select("getOperations", null);
		return operationList;
	}
	
		
	public List<Formula> getFormulas() throws RepositoryException {
		List<Formula> formulaList = repository.select("getFormulas", null);
		return formulaList;
	}
	
	public Formula getFormulaById(int formulaId) throws RepositoryException {
		Formula formula = repository.selectOne("getFormulaById", formulaId);
		if(formula != null){
			formula.init(repository);
		}
		return formula;
	}


	public void saveProcesses(List<ManufacturingProcess> processes) throws RepositoryException {
		productRepository.saveProcesses(processes);
		
	}

	public void addFormula(Formula formula) throws RepositoryException {
		formula.addFormula(productRepository);
		
	}

	public void updateFormula(Formula formula) throws RepositoryException {
		formula.updateFormula(productRepository);
		
	}


	public void deleteFormula(int id) throws RepositoryException {
		Formula formula = new Formula();
		formula.setId(id);
		formula.deleteFormula(productRepository);
		
	}

	public Product getProductById(Integer productId) throws RepositoryException {
		Product product = repository.selectOne("getProductById", productId);
		if(product != null){
			product.init(repository);
		}	
		return product;
	}
	
	
	//Material
	
	public Material createMaterial(Material material) throws RepositoryException {
		repository.add("addMaterial", material);
		return material;
	}
	
	public void updateMaterial(Material material) throws RepositoryException {
		repository.update("updateMaterial", material);
	}
	
	public void deleteMaterial(Material material) throws RepositoryException {
		repository.delete("deleteMaterial", material);
	}

	public List<Material> getMaterials() throws RepositoryException {
		List<Material> materialList = repository.select("getMaterials", null);
		return materialList;
	}
	
	
	public Material getMaterialByName(String name) throws RepositoryException{		
		Material material = repository.selectOne("getMaterialByName", name);
		return material;
	}

}
