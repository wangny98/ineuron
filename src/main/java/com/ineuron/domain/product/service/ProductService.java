package com.ineuron.domain.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.product.entity.Formula;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.valueobject.AttributeCategory;
import com.ineuron.domain.product.valueobject.FormulaMaterial;
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
		product.addProduct(productRepository);
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
	
	public Product getProductById(Integer productId) throws RepositoryException {
		Product product = repository.selectOne("getProductById", productId);
		if(product != null){
			product.init(repository);
		}	
		return product;
	}
	
	public Product getProductByName(String name) throws RepositoryException{		
		Product product = repository.selectOne("getProductByName", name);
		product.init(repository);
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
	
	public Formula getFormulaById(String formulaId) throws RepositoryException {
		Formula formula = repository.selectOne("getFormulaById", formulaId);
		if(formula != null){
			formula.init(repository);
		}
		return formula;
	}


	public void saveProcesses(List<ManufacturingProcess> processes, boolean hasFormula) throws RepositoryException {
		if(processes != null && !processes.isEmpty()){
			Product product = getProductById(processes.get(0).getProductId());
			if(!hasFormula){
				Formula formula = createFormulaWithProcesses(processes, product);	
				productRepository.addFormula(formula);
				product.updateProduct(repository);
			}
			
			productRepository.saveProcesses(processes);
		}
		
		
	}

	private Formula createFormulaWithProcesses(List<ManufacturingProcess> processes, Product product) {
		Formula formula = new Formula();
		String formulaName = product.getName() + "的配方-" + UUID.randomUUID().toString();
		formula.setName(formulaName);
		product.setFormulaId(formula.getId());
		List<FormulaMaterial> materialSettings = new ArrayList<FormulaMaterial>();
		float totalQuantity = 0;
		Integer materialId;
		for(ManufacturingProcess process : processes){
			materialId=process.getMaterialId();
			if(materialId!=0&&materialId!=null){
				FormulaMaterial formulaMaterial = new FormulaMaterial();
				formulaMaterial.setFormulaId(formula.getId());
				formulaMaterial.setMaterialId(process.getMaterialId());
				formulaMaterial.setMaterialQuantity(process.getMaterialQuantity());
				materialSettings.add(formulaMaterial);
				totalQuantity += process.getMaterialQuantity();
			}
		}
		for(FormulaMaterial formulaMaterial : materialSettings){
			formulaMaterial.setMaterialPercent((float)(Math.round(formulaMaterial.getMaterialQuantity()*100/totalQuantity*100)/100));
		}
		formula.setMaterialSettings(materialSettings);
		return formula;
	}

	public void addFormula(Formula formula) throws RepositoryException {
		formula.addFormula(productRepository);
		
	}

	public void updateFormula(Formula formula) throws RepositoryException {
		formula.updateFormula(productRepository);
		
	}


	public void deleteFormula(String id) throws RepositoryException {
		Formula formula = new Formula();
		formula.setId(id);
		formula.deleteFormula(productRepository);
		
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
	

	/*
	 * NLP based Search for Products 
	 */
	
	public List<Product> getProductsByNLPWords(String words) throws RepositoryException{
		String[] strArray=words.split(";");
		String attributeStr=strArray[0];
		String[] attributeWords=attributeStr.split("-");
		//String productCategoryStr=strArray[1];
		//String productStr=strArray[2];
		
		List<Product> productsResult=new ArrayList<Product>();		
		List<Product> allProducts = repository.select("getProducts", null);
		
		//get all the products which code includes the matched attribute's code
		for(int i=0;i<attributeWords.length;i++){
			List<Attribute> attributeList = repository.select("getAttributesByNLPWord", "%"+attributeWords[i]+"%");
			for(int j = 0;j<attributeList.size();j++){
				for(int k = 0;k<allProducts.size();k++){
					//product's code include attribute's code
					if(allProducts.get(k).getCode().indexOf(attributeList.get(j).getCode())>-1){
						{
							Product p=allProducts.get(k);
							if(productsResult.indexOf(p)==-1) productsResult.add(p);
						}
					}
				}
			}
		}
		
		//get all the products which productcategory equals to the matched productcategory
		/*List<ProductCategory> productCategoryList = repository.select("getProductCategoriesByNLPWord", productCategoryStr);
		for(int j = 0;j<productCategoryList.size();j++){
			for(int k = 0;k<allProducts.size();k++){
				//product's pc id equals to matched pc id
				if(allProducts.get(k).getProductCategoryId()==productCategoryList.get(j).getId()){
					Product p=allProducts.get(k);
					if(productsResult.indexOf(p)==-1) productsResult.add(p);
				}
			}
		}*/
		
		/*List<Product> productList = repository.select("getProductsByNLPWord", productStr);
		for(int k = 0;k<productList.size();k++){
				Product p=allProducts.get(k);
				if(productsResult.indexOf(p)==-1) productsResult.add(p);
			}*/
		
		for(int i = 0; i < productsResult.size(); i++)  
        {  
			productsResult.get(i).initForProductCategory(repository);
        }
		return productsResult;
	}
	
	
}
