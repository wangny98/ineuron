package com.ineuron.domain.order.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.INeuronRepository;
import com.ineuron.dataaccess.db.DataTablePageParameters;
import com.ineuron.dataaccess.db.ReportData;
import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.order.valueobject.OrderReportGroupByProduct;
import com.ineuron.domain.order.valueobject.OrderResponse;
import com.ineuron.domain.order.valueobject.OrderStatus;
import com.ineuron.domain.product.entity.Product;

public class OrderService {

	@Inject
	INeuronRepository repository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderService.class);


	public Order createOrder(Order order) throws RepositoryException {
		
		String prefix="SO";
		
		Date today=new Date();
		order.setOrderDateTime(today);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH,0);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		order.setOrderDate(today);		
		Order o = repository.selectOne("getLatestOrderByDate", format.format(Calendar.getInstance().getTime()));
		
		int newOrderSN = 0;
		if (o != null)
			newOrderSN = o.getSerialNumber() + 1;
		else
			newOrderSN = 1;
		order.setSerialNumber(newOrderSN);
		
		//Order number format: SO-[yyyymmdd]-xxxx. currently set the max number per day is 9999
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String todayDateforNumber = dateFormat.format(today);
	    order.setOrderNumber(prefix+"-"+todayDateforNumber+"-"+String.format("%04d", newOrderSN));	
		
	    //set the status to init (id=1)
	    order.setStatusId(1);
	    
	    //valid order is 1; deleted order is -1;
	    order.setValidFlag(1);
	    
	    //deprecated
	    //TO-DO: calculate the production period based on productionTicket's total period of this productId
	    //1. calculate the total production period of that productId in productionTicket table 
	    //2. compare this order's amount to specific device (type='production')'s volume; 
	    //if btw min and max, then use the total production period (add orders together
	    //if below min, use the total; if beyond max, use the mod
	    //3. set the productionperiod value of Order object
	    
		order.addOrder(repository);
		return order;
	}

	/*public Integer getOrderProductionPeriod(Integer productId) throws RepositoryException{
				
	}*/
	
	
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
		//System.out.println("get order by name in service: success");
		if(order!=null) order.init(repository);
		return order;
	}
	
	/*
	 * for data table paging
	 */
	public OrderResponse getOrdersByPage(DataTablePageParameters dtPageParameters) throws RepositoryException {
		OrderResponse orderResponse=new OrderResponse();
		
		Integer total=repository.selectOne("getTotalNumberOfOrders", null);
		orderResponse.setTotalRecords(total);
		//System.out.println("total: "+orderResponse.getTotalRecords());
		
		Integer startP=(dtPageParameters.getCurrentPage()-1)*dtPageParameters.getItemsPerPage();
		dtPageParameters.setStartPosition(startP);
		//System.out.println("startP: "+dtPageParameters.getStartPosition());
		List<Order> orders;
		if(startP==0) {
			orders=repository.select("getOrdersOfFirstPage", dtPageParameters); 
		}
		else{
			orders=repository.select("getOrdersByPage", dtPageParameters);
		}
		
		for (int i = 0; i < orders.size(); i++) {
				orders.get(i).init(repository);
		}
		orderResponse.setOrders(orders);  
	
		return orderResponse;
	}
	
	
	public List<OrderStatus> getOrderStatusList() throws RepositoryException {
		List<OrderStatus> orderStatusList = repository.select("getOrderStatus", null);
		
		return orderStatusList;
	}

	
	/*
	 * for Order Report
	 */
	public List<ReportData> getOrdersGroupbyProductAndMonth() throws RepositoryException {
		List<OrderReportGroupByProduct> orderReport = repository.select("getOrdersGroupbyProductAndMonth", null);
		List<Product> products=repository.select("getProducts", null);
		
		for (int i = 0; i < orderReport.size(); i++) {
			//orderReport.get(i).init(repository);
			for(int j=0; j<products.size(); j++){
				if(orderReport.get(i).getProductId()==products.get(j).getId()){
					orderReport.get(i).setProductName(products.get(j).getName());
					break;
				}
			}
		}
		
		List<ReportData> reportData=new ArrayList<ReportData>();
		boolean added=false;
		
		for (int i = 0; i < orderReport.size(); i++){
			List<Object> valueData=new ArrayList<Object>();
			ReportData oneReportData=new ReportData();
			added=false;
			valueData.add(orderReport.get(i).getMonth());
			valueData.add(orderReport.get(i).getAmount());
			

			/*System.out.println("products in order report: "+orderReport.get(i).getProductName());
			System.out.println("products in order report: "+orderReport.get(i).getProductId());
			System.out.println("valueData month to be added: "+valueData.get(0));
			System.out.println("valueData amount to be added: "+valueData.get(1));
			
			System.out.println("orderReport["+i+"]");
			for (int m = 0; m < reportData.size(); m++){
				System.out.println("before each for(orderReport) reportData value: "+"report["+m+"]:"+reportData.get(m).getKey()+" "+reportData.get(m).getValues());
			}*/
			
			for (int j = 0; j < reportData.size(); j++){
				//already has the product in the report, just add the value
				if(reportData.get(j).getKey()==orderReport.get(i).getProductName()){
					reportData.get(j).getValues().add(valueData);
					added=true;
					break;
				}
			}
			//new product, so add both product name and value in the report data
			if(!(added)){
				oneReportData.setKey(orderReport.get(i).getProductName());
				oneReportData.getValues().add(valueData);
				reportData.add(oneReportData);
				//System.out.println("add new product value: "+oneReportData.getKey()+oneReportData.getValues());
				//System.out.println("now in reportData: "+reportData.get(reportData.size()-1).getValues());
			}
			/*
			for (int n = 0; n < reportData.size(); n++){
				System.out.println("data in reportData after each for(orderReport): "+"reportData["+n+"]"+reportData.get(n).getKey()+" "+reportData.get(n).getValues());
			}*/
		}
		/*
		System.out.println("final report data size: "+reportData.size());
		for (int k = 0; k < reportData.size(); k++){
			System.out.println("products in final report data: "+reportData.get(k).getKey());
		}*/
		return reportData;
	}

}
