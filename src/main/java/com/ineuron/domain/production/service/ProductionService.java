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
import com.ineuron.domain.production.valueobject.PackagePeriod;
import com.ineuron.domain.production.valueobject.ProductionCapacity;
import com.ineuron.domain.production.valueobject.ProductionPeriod;


public class ProductionService {

	@Inject
	INeuronRepository repository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductionService.class);



	public List<ProductionPeriod> getProductionPeriodsByProductId(Integer productId) throws RepositoryException {
		List<ProductionPeriod> productProductionPeriods = repository.select("getProductionPeriodsByProductId", productId);		
		return productProductionPeriods;
	}
	
	public ProductionCapacity getProductionCapacityByDate(Date date) throws RepositoryException {
		ProductionCapacity uPoductionCapacity = repository.selectOne("getProductionCapacityByDate", date);		
		return uPoductionCapacity;
	}
	
	public List<ProductionCapacity> getProductionCapacityByPeriod(List<Date> dates) throws RepositoryException {
		List<ProductionCapacity> uPoductionCapacities = repository.select("getProductionCapacityByPeriod", dates);		
		return uPoductionCapacities;
	}
		
	public PackagePeriod getPackagePeriodByUnit(String unit) throws RepositoryException {
			PackagePeriod pPeriod = repository.selectOne("getPackagePeriodByUnit", unit);		
			return pPeriod;
		}

}
