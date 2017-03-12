package com.ineuron.domain.production.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.production.entity.Production;
import com.ineuron.domain.production.valueobject.ProductionCapacity;
import com.ineuron.domain.production.valueobject.ProductionPeriod;


public class ProductionService {

	@Inject
	INeuronRepository repository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductionService.class);



	public List<ProductionPeriod> getProductionPeriodsByProductId(Integer productId) throws RepositoryException {
		//System.out.println("productId: "+productId);
		List<ProductionPeriod> productProductionPeriods = repository.select("getProductionPeriodsByProductId", productId);		
		//System.out.println("production period "+productProductionPeriods.get(0).getProductionPeriod());
		return productProductionPeriods;
	}
	
	public ProductionCapacity getProductionCapacityByDate(Date date) throws RepositoryException {
		//System.out.println("productId: "+productId);
		ProductionCapacity uPoductionCapacity = repository.selectOne("getProductionCapacityByDate", date);		
		//System.out.println("production period "+productProductionPeriods.get(0).getProductionPeriod());
		return uPoductionCapacity;
	}
	
	public List<ProductionCapacity> getProductionCapacityByPeriod(List<Date> dates) throws RepositoryException {
		//System.out.println("productId: "+productId);
		List<ProductionCapacity> uPoductionCapacities = repository.select("getProductionCapacityByPeriod", dates);		
		//System.out.println("production period "+productProductionPeriods.get(0).getProductionPeriod());
		return uPoductionCapacities;
	}

	

}
