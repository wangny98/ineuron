package com.ineuron.domain.order.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.common.util.ChineseNumberConverter;
import com.ineuron.common.util.DateTraverse;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.dataaccess.db.DataTablePageParameters;
import com.ineuron.dataaccess.db.ReportData;
import com.ineuron.domain.nlp.service.NLPService;
import com.ineuron.domain.nlp.valueobject.ProductSelection;
import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.order.valueobject.OrderReportGroupByProduct;
import com.ineuron.domain.order.valueobject.OrderResponse;
import com.ineuron.domain.order.valueobject.OrderStatus;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.valueobject.Attribute;
import com.ineuron.domain.product.valueobject.ProductCategory;
import com.ineuron.domain.product.valueobject.ProductPackageType;
import com.ineuron.domain.product.valueobject.ProductPrice;
import com.ineuron.domain.production.service.ProductionService;
import com.ineuron.domain.production.valueobject.PackagePeriod;
import com.ineuron.domain.production.valueobject.ProductionCapacity;
import com.ineuron.domain.production.valueobject.ProductionPeriod;
public class OrderService {

	@Inject
	INeuronRepository repository;

	@Inject
	ProductionService productionService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderService.class);

	public Order createOrder(Order order) throws RepositoryException {

		String prefix = "SO";

		Date today = new Date();
		order.setOrderDateTime(today);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		order.setOrderDate(today);
		Order o = repository.selectOne("getLatestOrderByDate",
				format.format(Calendar.getInstance().getTime()));

		int newOrderSN = 0;
		if (o != null)
			newOrderSN = o.getSerialNumber() + 1;
		else
			newOrderSN = 1;
		order.setSerialNumber(newOrderSN);

		// Order number format: SO-[yyyymmdd]-xxxx. currently set the max number
		// per day is 9999
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String todayDateforNumber = dateFormat.format(today);
		order.setOrderNumber(prefix + "-" + todayDateforNumber + "-"
				+ String.format("%04d", newOrderSN));

		// set the status to init (id=1)
		order.setStatusId(1);

		// valid order is 1; deleted order is -1;
		order.setValidFlag(1);	

		order.addOrder(repository);

		productionService.updateProductionCapacity(order);

		return order;
	}

	/*
	 * public Integer getOrderProductionPeriod(Integer productId) throws
	 * RepositoryException{
	 * 
	 * }
	 */

	public Order updateOrder(Order order) throws RepositoryException {
		order.updateOrder(repository);
		return order;
	}

	public void updateOrderForStatus(Order order) throws RepositoryException {
		repository.update("updateOrderForStatus", order);
	}

	public void deleteOrder(Order order) throws RepositoryException {
		repository.delete("deleteOrder", order);
	}

	public List<Order> getOrderList() throws RepositoryException {
		List<Order> orderList = repository.select("getOrders", null);
		for (int i = 0; i < orderList.size(); i++) {
			orderList.get(i).init(repository);
		}
		return orderList;
	}

	public Order getOrderById(Integer orderId) throws RepositoryException {
		Order order = repository.selectOne("getOrderById", orderId);
		if (order != null) {
			order.init(repository);
		}
		return order;
	}

	public Order getOrderByName(String name) throws RepositoryException {
		Order order = repository.selectOne("getOrderByName", name);
		// System.out.println("get order by name in service: success");
		if (order != null)
			order.init(repository);
		return order;
	}

	public List<Order> getOrdersByProductIds(Integer productIds)
			throws RepositoryException {
		System.out.println("productId list: " + productIds);
		List<Order> orders = repository.select("getOrdersByProductIds",
				productIds);
		// System.out.println("get order by name in service: success");
		for (int i = 0; i < orders.size(); i++) {
			orders.get(i).init(repository);
		}
		return orders;
	}

	/*
	 * for data table paging
	 */
	public OrderResponse getOrdersByPage(
			DataTablePageParameters dtPageParameters)
					throws RepositoryException {
		OrderResponse orderResponse = new OrderResponse();

		Integer total = repository.selectOne("getTotalNumberOfOrders", null);
		orderResponse.setTotalRecords(total);
		// System.out.println("total: "+orderResponse.getTotalRecords());

		/*
		 * List<Integer> productIds; if(dtPageParameters.getProductIds()==null){
		 * productIds=repository.select("getProductIds",null); }
		 */

		Integer startP = (dtPageParameters.getCurrentPage() - 1)
				* dtPageParameters.getItemsPerPage();
		dtPageParameters.setStartPosition(startP);
		System.out.println("ordering option: "
				+ dtPageParameters.getOrderingOption());
		List<Order> orders;
		if (startP == 0) {
			orders = repository
					.select("getOrdersOfFirstPage", dtPageParameters);
		} else {
			orders = repository.select("getOrdersByPage", dtPageParameters);
		}

		for (int i = 0; i < orders.size(); i++) {
			orders.get(i).init(repository);
		}
		orderResponse.setOrders(orders);

		return orderResponse;
	}

	public List<OrderStatus> getOrderStatusList() throws RepositoryException {
		List<OrderStatus> orderStatusList = repository.select("getOrderStatus",
				null);

		return orderStatusList;
	}

	/*
	 * NLP based Search for ordering products
	 */

	public List<Product> getProductsAndOrderInfoByNLPWords(String words)
			throws RepositoryException {

		// NLPSearchResponse nlpSearchResponse=new NLPSearchResponse();

		ProductSelection parsedResult = new ProductSelection();

		parsedResult = NLPService.getInstance().parseText(words);

		// Get all the nlp'ed attribute words
		List<String> attributeWords = new ArrayList<String>();
		if (parsedResult.getScopes() != null)
			attributeWords.addAll(parsedResult.getScopes());
		if (parsedResult.getColors() != null)
			attributeWords.addAll(parsedResult.getColors());
		if (parsedResult.getForms() != null)
			attributeWords.addAll(parsedResult.getForms());

		// Get productName; function; other attribute; quality
		List<String> productWords = new ArrayList<String>();
		if (parsedResult.getProductName() != null
				&& parsedResult.getProductName().length() > 0)
			productWords.add(parsedResult.getProductName());
		if (parsedResult.getFunctions() != null)
			productWords.addAll(parsedResult.getFunctions());
		if (parsedResult.getQualities() != null)
			productWords.addAll(parsedResult.getQualities());
		if (parsedResult.getOtherAttributes() != null
				&& parsedResult.getOtherAttributes().size() > 0)
			productWords.addAll(parsedResult.getOtherAttributes());

		/*
		 * //add parsedResult's scope if some synonym is matched to the scope
		 * terms in DB List<String>
		 * matchedScopes=matchSynonymToScope(productWords); for (int i=0;
		 * i<matchedScopes.size(); i++){ //parsedResult. }
		 */

		List<Product> productsResult = new ArrayList<Product>();
		List<Product> allProducts = repository.select("getProducts", null);

		// get all the products which code includes the matched attribute's code
		for (int i = 0; i < attributeWords.size(); i++) {
			List<Attribute> attributeList = repository.select(
					"getAttributesByTerm", "%" + attributeWords.get(i) + "%");
			for (int j = 0; j < attributeList.size(); j++) {
				for (int k = 0; k < allProducts.size(); k++) {
					// product's code include attribute's code
					if (allProducts.get(k).getCode()
							.indexOf(attributeList.get(j).getCode()) > -1) {
						{
							Product p = allProducts.get(k);
							if (productsResult.indexOf(p) == -1)
								productsResult.add(p);
						}
					}
				}
			}
		}

		// get all the products which product category equals to the matched
		// product category
		for (int i = 0; i < productWords.size(); i++) {
			List<ProductCategory> productCategoryList = repository.select(
					"getProductCategoriesByTerm", "%" + productWords.get(i)
					+ "%");
			for (int j = 0; j < productCategoryList.size(); j++) {
				for (int k = 0; k < allProducts.size(); k++) {
					// product's pc id equals to matched pc id
					if (allProducts.get(k).getProductCategoryId() == productCategoryList
							.get(j).getId()) {
						Product p = allProducts.get(k);
						if (productsResult.indexOf(p) == -1)
							productsResult.add(p);
					}
				}
			}
		}

		// search all the products by nlp'ed terms (product name, functions,
		// qualities, other attributes) stored in productWords
		for (int i = 0; i < productWords.size(); i++) {
			List<Product> productList = repository.select("getProductsByTerm",
					"%" + productWords.get(i) + "%");
			for (int j = 0; j < productList.size(); j++) {
				for (int k = 0; k < allProducts.size(); k++) {
					Product p = allProducts.get(k);
					if (productList.get(j).getId() == p.getId()) {
						if (productsResult.indexOf(p) == -1)
							productsResult.add(p);
					}
				}
			}
		}

		// search in product name/descriptions with attribute words for double
		// check
		for (int i = 0; i < attributeWords.size(); i++) {
			List<Product> productList = repository.select("getProductsByTerm",
					"%" + attributeWords.get(i) + "%");
			for (int j = 0; j < productList.size(); j++) {
				for (int k = 0; k < allProducts.size(); k++) {
					Product p = allProducts.get(k);
					if (productList.get(j).getId() == p.getId()) {
						if (productsResult.indexOf(p) == -1)
							productsResult.add(p);
					}
				}
			}
		}

		// filter out products which scope are not matched to the required scope
		List<Product> finalProductsResult = new ArrayList<Product>();

		if (parsedResult.getScopes()!=null) {
			Set<String> scopes = parsedResult.getScopes();

			for (String scope : scopes) {
				List<Attribute> scopeList = repository.select(
						"getAttributesByTerm", "%" + scope + "%");
				for (int j = 0; j < scopeList.size(); j++) {
					for (int k = 0; k < productsResult.size(); k++) {
						// remove the product which attribute code includes
						// scope code
						// System.out.println("product scope: "+productsResult.get(k).getCode()+productsResult.get(k).getName());
						// System.out.println("nlp scope: "+scopeList.get(j).getCode());
						if (productsResult.get(k).getCode()
								.contains(scopeList.get(j).getCode())) {
							{
								// System.out.println("removed product: "+productsResult.get(k).getName());
								finalProductsResult.add(productsResult.get(k));
							}
						}
					}
				}
			}
		} else {
			for (int i = 0; i < productsResult.size(); i++) {
				finalProductsResult.add(productsResult.get(i));
			}
		}

		for (int i = 0; i < finalProductsResult.size(); i++) {
			finalProductsResult.get(i).initForProductCategory(repository);
			finalProductsResult.get(i).initForProductPrice(repository);
		}

		// set the order init amount from NLP analysis service result
		Order order = new Order();
		//System.out.println("quantity: "+parsedResult.getQuantity());
		if (parsedResult.getQuantity()!=null&&parsedResult.getQuantity().size()>0) {
			List<String> nlpQuantityStr = parsedResult.getQuantity();
			float amount;
			try {
				amount = Float.parseFloat(nlpQuantityStr.get(0));
			} catch (NumberFormatException ex) {
				// System.out.println("Error: Not valid NumberFormat.");
				amount = ChineseNumberConverter.convertToNum(nlpQuantityStr
						.get(0));
			}

			switch (nlpQuantityStr.get(1)) {
			case "升":
			case "L":
			case "l":
				order.setAmount(amount);
				break;
			case "毫升":
			case "ml":
			case "ML":
				order.setAmount(amount / 1000);
				break;
			case "千克":
			case "公斤":
			case "kg":
			case "KG":
				order.setAmount(amount);
				break;
			case "斤":
				order.setAmount(amount/2);
				break;
			case "克":
			case "g":
			case "G":
				order.setAmount(amount / 1000);
				break;
			case "吨":
			case "t":
			case "T":
				order.setAmount(amount * 1000);
				break;
			}
		}
		else order.setAmount(0);



		// set the order delivery date from NLP analysis service result

		Calendar cal = Calendar.getInstance();
		if (parsedResult.getDate()!=null&&parsedResult.getDate().size()>0) {
			List<String> nlpDateStr = parsedResult.getDate();
			int dateNum;

			//three cases: 1. tomorrow; 2. the day after tomorrow; 3. format like "一周后"
			switch (nlpDateStr.get(0)) {
			case "明天":
				cal.add(Calendar.DATE, 1);
				break;
			case "后天":
				cal.add(Calendar.DATE, 2);
				break;
			default:
				try {
					dateNum = (int) Float.parseFloat(nlpDateStr.get(0));
				} catch (NumberFormatException ex) {
					dateNum = (int) ChineseNumberConverter
							.convertToNum(nlpDateStr.get(0));
				}

				switch (nlpDateStr.get(1)) {
				case "周":
				case "星期":
					cal.add(Calendar.DATE, dateNum * 7);
					break;
				case "月":
					cal.add(Calendar.DATE, dateNum * 30);
					break;
				case "个":
					switch (nlpDateStr.get(2)) {
					case "星期":
					case "周":
						cal.add(Calendar.DATE, dateNum * 7);
						break;
					case "月":
						cal.add(Calendar.DATE, dateNum * 30);
						break;
					}
					break;
				}
				break;
			}
			//System.out.println("nlp date: "+cal.getTime());
			order.setDeliveryDate(cal.getTime());
		}
		else order.setDeliveryDate(cal.getTime());

		List<ProductPackageType> pTypes=repository.select("getProductPackageTypes", null);
		ProductPackageType lpType=repository.selectOne("getLabelProductPackageType", null);
		for (int i = 0; i < finalProductsResult.size(); i++) {
			//set init estimated delivery date if order amount is not 0
			if(order.getAmount()>0){	
				int packageAmount=(int)Math.ceil(order.getAmount()*pTypes.get(0).getVolume());
				order.setPackageAmount(packageAmount);

				int pId=finalProductsResult.get(i).getId();
				ProductPrice price=repository.selectOne("getProductPriceByProductId",pId);
				if(price!=null){
					order.setProductCharge(order.getAmount()*price.getPrice());

					order.setPackageCharge(packageAmount*pTypes.get(0).getPrice());

					order.setLabelPackageCharge(lpType.getPrice());

					order.setTotalCharge(order.getProductCharge()+
							order.getPackageCharge()+
							order.getLabelPackageCharge());
				}
			}

			finalProductsResult.get(i).setOrder(order);
		}

		/*if(finalProductsResult.size()==0){
			for(int p=0; p<allProducts.size(); p++){
				allProducts.get(p).setOrder(order);
			}
			return allProducts;
		}*/
		return finalProductsResult;
	}

	/*
	 * Match synonym to scope term
	 */
	/*
	 * public List<String> matchSynonymToScope(List<String> words) throws
	 * RepositoryException{ List<String> matchedScopes=new ArrayList<String>();
	 * List<String> sysDefinedScopes=repository.select("getAttributes", null);
	 * 
	 * return matchedScopes; }
	 */

	/*
	 * for Order Report
	 */
	public List<ReportData> getOrdersGroupbyProductAndMonth()
			throws RepositoryException {
		List<OrderReportGroupByProduct> orderReport = repository.select(
				"getOrdersGroupbyProductAndMonth", null);
		List<Product> products = repository.select("getProducts", null);

		for (int i = 0; i < orderReport.size(); i++) {
			// orderReport.get(i).init(repository);
			for (int j = 0; j < products.size(); j++) {
				if (orderReport.get(i).getProductId() == products.get(j)
						.getId()) {
					orderReport.get(i)
					.setProductName(products.get(j).getName());
					break;
				}
			}
		}

		List<ReportData> reportData = new ArrayList<ReportData>();
		boolean added = false;

		for (int i = 0; i < orderReport.size(); i++) {
			List<Object> valueData = new ArrayList<Object>();
			ReportData oneReportData = new ReportData();
			added = false;
			valueData.add(orderReport.get(i).getMonth());
			valueData.add(orderReport.get(i).getAmount());

			for (int j = 0; j < reportData.size(); j++) {
				// already has the product in the report, just add the value
				if (reportData.get(j).getKey() == orderReport.get(i)
						.getProductName()) {
					reportData.get(j).getValues().add(valueData);
					added = true;
					break;
				}
			}
			// new product, so add both product name and value in the report
			// data
			if (!(added)) {
				oneReportData.setKey(orderReport.get(i).getProductName());
				oneReportData.getValues().add(valueData);
				reportData.add(oneReportData);
			}

		}
		/*
		 * System.out.println("final report data size: "+reportData.size()); for
		 * (int k = 0; k < reportData.size(); k++){
		 * System.out.println("products in final report data: "
		 * +reportData.get(k).getKey()); }
		 */
		return reportData;
	}

}
