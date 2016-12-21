package com.ineuron.domain.product.repository;

import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronDBConnection;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.entity.Formula;
import com.ineuron.domain.product.valueobject.FormulaMaterial;
import com.ineuron.domain.product.valueobject.ManufacturingProcess;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductRepository {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductRepository.class);

	public ProductRepository() {

	}

	
	public void addProduct(Product product) throws RepositoryException {
		SqlSession session = INeuronDBConnection.getSession();
		try {
			int productSN=0;
			Product p=session.selectOne("getMaxProductSNByCategoryId", product.getProductCategoryId());
			if(p!=null) productSN=p.getSerialNumber()+1;
			else productSN=1;
			product.setSerialNumber(productSN);
			//System.out.println("productSN: "+productSN);
			String productCode=product.getCode()+"-"+String.format("%03d", productSN);
			product.setCode(productCode);
			
			System.out.println("product code: "+productCode);
			session.insert("addProduct", product);
			session.commit();
			System.out.println("insert product by using mybatis!");
		} finally {
			session.close();
		}
	}

		
	public void deleteProduct(Product product) throws RepositoryException {
		SqlSession session = INeuronDBConnection.getSession();
		try {
			session.delete("deleteProduct", product);
			session.commit();
			System.out.println("delete product by using mybatis!");
		} finally {
			session.close();
		}
	}



	public void saveProcesses(List<ManufacturingProcess> processes) throws RepositoryException {
		SqlSession session = INeuronDBConnection.getSession();
		try {
			if(processes != null && processes.size() > 0){
				session.delete("deleteProcesses", processes.get(0).getProductId());
				for(int i = 0; i < processes.size(); i ++){
					ManufacturingProcess process = processes.get(i);
					process.setOrderId(i);
					session.insert("insertProcess", process);
				}
				session.commit();
			}
			
		} finally {
			session.close();
		}
	}


	public void addFormula(Formula formula)  throws RepositoryException {
		SqlSession session = INeuronDBConnection.getSession();
		
		try{
			if(formula != null){
				session.insert("addFormula", formula);
				
				List<FormulaMaterial> materials = formula.getMaterialSettings();
				if(materials != null && materials.size() > 0){
					for(FormulaMaterial material : materials){
						session.insert("addFormulaMaterial", material);
					}
				}
				session.commit();
			}
			
		} finally {
			session.close();
		}
		
	}


	public void updateFormula(Formula formula) throws RepositoryException {
		SqlSession session = INeuronDBConnection.getSession();
		
		try{
			if(formula != null){
				session.update("updateFormula", formula);
				session.delete("deleteFormulaMaterial", formula);
				List<FormulaMaterial> materials = formula.getMaterialSettings();
				if(materials != null && materials.size() > 0){
					for(FormulaMaterial material : materials){
						session.insert("addFormulaMaterial", material);
					}
				}
				session.commit();
			}
			
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute sql!", e);
		} finally {
			session.close();
		}
		
	}


	public void deleteFormula(Formula formula) throws RepositoryException {
		SqlSession session = INeuronDBConnection.getSession();
		try{
			if(formula != null){
				session.delete("deleteFormula", formula);
				session.commit();
			}
			
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute sql: deleteFormula!", e);
		} finally {
			session.close();
		}
	}

}
