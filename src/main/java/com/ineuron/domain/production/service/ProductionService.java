package com.ineuron.domain.production.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.common.util.DateTraverse;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.production.entity.Production;
import com.ineuron.domain.production.valueobject.PackagePeriod;
import com.ineuron.domain.production.valueobject.ProductionCapacity;
import com.ineuron.domain.production.valueobject.ProductionPeriod;

public class ProductionService {

	@Inject
	INeuronRepository repository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductionService.class);

	public void createProductionCapacity(ProductionCapacity productionCapacity)
			throws RepositoryException {
		System.out.println("in create production capacity");
		repository.add("addProductionCapacity", productionCapacity);
	}

	public List<ProductionPeriod> getProductionPeriodsByProductId(
			Integer productId) throws RepositoryException {
		List<ProductionPeriod> productProductionPeriods = repository.select(
				"getProductionPeriodsByProductId", productId);
		return productProductionPeriods;
	}

	public ProductionCapacity getProductionCapacityByDate(Date date)
			throws RepositoryException {
		ProductionCapacity uPoductionCapacity = repository.selectOne(
				"getProductionCapacityByDate", date);
		return uPoductionCapacity;
	}

	public String getEstimatedDeliveryDate(Order order)
			throws RepositoryException {

		Date estimatedDeliveryDate = null;

		// get production period for this order Integer productionPeriod = 0;
		List<ProductionPeriod> pPeriods = this
				.getProductionPeriodsByProductId(order.getProductId());
		Integer productionPeriod=0;
		for (int i = 0; i < pPeriods.size(); i++) {
			if ((order.getAmount() >= pPeriods.get(i).getProductionMinVolume())
					&& (order.getAmount() >= pPeriods.get(i)
							.getProductionMinVolume())) {
				productionPeriod = pPeriods.get(i).getProductionPeriod();
				break;
			}
		}

		Date today = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(today);

		cal2.add(Calendar.MONTH, 3);
		Date endDate = cal2.getTime();

		List<ProductionCapacity> productionCapacities = this
				.getProductionCapacityByPeriod(endDate);

		boolean foundAvailProductionCapacity = false;
		boolean haveProductionInTheDate = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> listDate = DateTraverse.getDates(today, endDate);
		if (!listDate.isEmpty()) {
			for (Date date : listDate) {
				System.out.println("each date: " + date);
				for (ProductionCapacity p : productionCapacities) {
					// if find one in p: date is the same and
					// (consumedPeriod+this order production period) <24 hours
					//System.out.println("p.getDate()" + p.getDate());
					if (sdf.format(date).equals(sdf.format(p.getDate()))) {
						System.out.println("find equal date");
						haveProductionInTheDate = true;
						if ((p.getConsumedPeriod() + productionPeriod) < 24 * 3600) {
							estimatedDeliveryDate = date;
							foundAvailProductionCapacity = true;
							break;
						}
					}
				}
				if (foundAvailProductionCapacity)
					break;
				else if (!haveProductionInTheDate) {
					estimatedDeliveryDate = date;
					break;
				}
			}
		}

		// get package period
		String unit = "升";
		PackagePeriod packagePeriod = this.getPackagePeriodByUnit(unit);
		Double pDays = Math.ceil(packagePeriod.getPeriod() * order.getAmount()
				/ (3600 * 24));
		int packageDays = pDays.intValue();

		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(estimatedDeliveryDate);

		cal3.add(Calendar.DATE, packageDays);
		estimatedDeliveryDate = cal3.getTime();

		String estimatedDeliveryDateStr = sdf.format(estimatedDeliveryDate);
		return estimatedDeliveryDateStr;
	}

	public void updateProductionCapacity(Order order)
			throws RepositoryException {
		// update Production Capacity for the specific day with the new order
		// 1. search all the dates before expected date (expected date-
		// amount*package period per unit)
		// 1.1 search all the devices for that specific product;
		// 1.2 check if there is available period (device's
		// consumedperiod<24hour);
		// 1.3 if yes, then check productionTaskList, if there is the same
		// productId task,
		// if yes, then add the volume to that task, and update the production
		// period(check productionperiod table)
		// if no, then add a new task with this productId to the list
		// 1.4 if no available period, return result that "cannot deliver within
		// the expected period

		System.out.println("in update production capacity function: ");
		
		List<ProductionPeriod> productionPeriods = this
				.getProductionPeriodsByProductId(order.getProductId());

		List<ProductionCapacity> productionCapacities = this
				.getProductionCapacityByPeriod(order.getDeliveryDate());

		String[] taskList;
		String[] productVolume;
		boolean haveProductionForTheDate = false;
		boolean productExistedInTaskList = false;
		ProductionCapacity pc = new ProductionCapacity();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (ProductionCapacity p : productionCapacities) {
			// if find one in p: date is the same and (consumedPeriod+this order
			// production period) <24 hours
			//System.out.println("order delivery date: "+sdf.format(order.getDeliveryDate())+" p date: "+sdf.format(p.getDate()));
			if (sdf.format(order.getDeliveryDate()).equals(sdf.format(p.getDate()))) {
				//System.out.println("found equal date ");
				haveProductionForTheDate = true;
				taskList = p.getProductionTaskList().split(";");
				for (int t = 0; t < taskList.length; t++) {
					productVolume = taskList[t].split(":");
					if (Integer.parseInt(productVolume[0]) == order
							.getProductId()) {
						productExistedInTaskList=true;
						Float newVolume = Float.parseFloat(productVolume[1])
								+ order.getAmount();
						taskList[t] = productVolume[0] + ":"
								+ newVolume.toString();

						// update the consumed production period
						// firstly, subtract the old production period
						for (int j = 0; j < productionPeriods.size(); j++) {
							if (order.getAmount() >= productionPeriods.get(j)
									.getProductionMinVolume()
									&& order.getAmount() <= productionPeriods
											.get(j).getProductionMaxVolume()) {
								p.setConsumedPeriod(p.getConsumedPeriod()
										- productionPeriods.get(j)
												.getProductionPeriod());
								break;
							}
						}

						// secondly, add the new production period
						for (int j = 0; j < productionPeriods.size(); j++) {
							if (newVolume >= productionPeriods.get(j)
									.getProductionMinVolume()
									&& newVolume <= productionPeriods.get(j)
											.getProductionMaxVolume()) {
								p.setConsumedPeriod(p.getConsumedPeriod()
										+ productionPeriods.get(j)
												.getProductionPeriod());
								break;
							}
						}
						break;
					}
				}
				
				String newTaskList=StringUtils.join(taskList, ";");
				
				//no existed product task in the list, add a new one
				if(!productExistedInTaskList){
					newTaskList=newTaskList+";"+order.getProductId().toString()+":"+Float.toString(order.getAmount());
					
					//update consumed production period
					for (int j = 0; j < productionPeriods.size(); j++) {
						if (order.getAmount() >= productionPeriods.get(j)
								.getProductionMinVolume()
								&& order.getAmount() <= productionPeriods
										.get(j).getProductionMaxVolume()) {
							p.setConsumedPeriod(p.getConsumedPeriod()
									+ productionPeriods.get(j)
											.getProductionPeriod());
							break;
						}
					}
				}				
				
				p.setProductionTaskList(newTaskList);
				repository.update("updateProductionCapacity", p);
				break;
			}
		}
		// no existed production capacity available for that date; then create a
		// new production capacity
		if (!haveProductionForTheDate) {
			// insert a new production capacity
			pc.setDeviceId(1);
			pc.setDate(order.getDeliveryDate());

			for (int j = 0; j < productionPeriods.size(); j++) {
				if (order.getAmount() >= productionPeriods.get(j)
						.getProductionMinVolume()
						&& order.getAmount() <= productionPeriods.get(j)
								.getProductionMaxVolume()) {
					pc.setConsumedPeriod(productionPeriods.get(j)
							.getProductionPeriod());
					break;
				}
			}

			pc.setProductionTaskList(order.getProductId().toString() + ":"
					+ Float.toString(order.getAmount()));
			this.createProductionCapacity(pc);
		}
	}

	public List<ProductionCapacity> getProductionCapacityByPeriod(Date endDate)
			throws RepositoryException {
		List<ProductionCapacity> uProductionCapacities = repository.select(
				"getProductionCapacityByPeriod", endDate);
		return uProductionCapacities;
	}

	public PackagePeriod getPackagePeriodByUnit(String unit)
			throws RepositoryException {
		PackagePeriod pPeriod = repository.selectOne("getPackagePeriodByUnit",
				unit);
		return pPeriod;
	}

}
