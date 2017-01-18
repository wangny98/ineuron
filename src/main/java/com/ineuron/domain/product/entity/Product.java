package com.ineuron.domain.product.entity;

import java.util.List;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.product.repository.ProductRepository;
import com.ineuron.domain.product.valueobject.ManufacturingProcess;
import com.ineuron.domain.product.valueobject.Operation;
import com.ineuron.domain.product.valueobject.OperationType;
import com.ineuron.domain.product.valueobject.ProductCategory;
import com.ineuron.domain.product.valueobject.ProductPrice;

public class Product {

	private Integer id;
	private String name;
	private String code;
	private Integer productCategoryId;
	private Integer serialNumber;
	private String formulaId;
	private String description;
	
	private ProductCategory productCategory;
	private Formula formula;
	private List<ManufacturingProcess> manufacturingProcesses;
	private List<Operation> operations;
	private List<OperationType> operationTypes;
	private ProductPrice productPrice;

	//private static final Logger LOGGER = LoggerFactory.getLogger("Product");

	public void addProduct(ProductRepository repository) throws RepositoryException {
		repository.addProduct(this);
	}

	public void updateProduct(INeuronRepository repository) throws RepositoryException {
		repository.update("updateProduct", this);
	}
	
	public void deleteProduct(INeuronRepository repository) throws RepositoryException {
		repository.delete("deleteProduct", this);
	}
	
	public void init(INeuronRepository repository) throws RepositoryException{
		if(formulaId != null){
			formula = repository.selectOne("getFormulaById", formulaId.toString());
			if(formula == null){
				formula = new Formula();
				formulaId = null;
			}
		}else{
			formula = new Formula();		
		}
		
		formula.init(repository);
		productCategory=repository.selectOne("getProductCategoryById", productCategoryId.toString());	
		operations = repository.select("getOperations", null);
		manufacturingProcesses = repository.select("getProcesses", id);
		operationTypes = repository.select("getOperationTypes", null);
		
		//System.out.println("before get product price by id");
		productPrice=repository.selectOne("getProductPriceByProductId", id);
		
	}
	
	public void initForProductCategory(INeuronRepository repository) throws RepositoryException{
	
		productCategory=repository.selectOne("getProductCategoryById", productCategoryId.toString());
		
	}
	
	public void initForProductPrice(INeuronRepository repository) throws RepositoryException{
		
		productPrice=repository.selectOne("getProductPriceByProductId", id);
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	public List<ManufacturingProcess> getManufacturingProcesses() {
		return manufacturingProcesses;
	}

	public void setManufacturingProcesses(List<ManufacturingProcess> manufacturingProcesses) {
		this.manufacturingProcesses = manufacturingProcesses;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<OperationType> getOperationTypes() {
		return operationTypes;
	}

	public void setOperationTypes(List<OperationType> operationTypes) {
		this.operationTypes = operationTypes;
	}

	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}
	
	
}
