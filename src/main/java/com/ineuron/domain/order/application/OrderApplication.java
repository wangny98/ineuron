package com.ineuron.domain.order.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.common.util.ChineseNumberConverter;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.dataaccess.db.DataTablePageParameters;
import com.ineuron.dataaccess.db.ReportData;
import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.order.valueobject.OrderReportGroupByProduct;
import com.ineuron.domain.order.valueobject.OrderResponse;
import com.ineuron.domain.order.valueobject.OrderStatus;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.valueobject.Attribute;
import com.ineuron.domain.product.valueobject.ProductCategory;
import com.ineuron.domain.product.valueobject.ProductPackageType;
import com.ineuron.domain.production.service.ProductionService;
import com.ineuron.domain.production.valueobject.ProductionCapacity;

public class OrderApplication {

	@Inject
	INeuronRepository repository;

	@Inject
	@Named("nlpEnabled")
	String nlpEnabled;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderApplication.class);

	/*public Order createOrder(Order order) throws RepositoryException {

		
	}*/

	

}
