package com.ineuron.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ineuron.api.INeuronResponse;
import com.ineuron.api.NLPSearchResponse;
import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.dataaccess.db.DataTablePageParameters;
import com.ineuron.dataaccess.db.ReportData;
import com.ineuron.domain.order.entity.Order;
import com.ineuron.domain.order.service.OrderService;
import com.ineuron.domain.order.valueobject.OrderStatus;
import com.ineuron.domain.order.valueobject.OrderResponse;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.user.service.SecurityService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class OrderResource {

	@Inject
	private OrderService orderService;

	@Inject
	private SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

	public OrderResource() {
		super();
	}

	@Path("/list")
	@GET
	@Timed
	public Response orderList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<Order> orders = orderService.getOrderList();
			response.setValue(orders);
			return Response.ok(response).cookie(new NewCookie("name", "Hello, world!")).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}		
	}
	
	@Path("/listbyproducts")
	@GET
	@Timed
	public Response getOrdersByProducts(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			//System.out.println("productIds: "+productIds);
			List<Order> orders = orderService.getOrdersByProductIds(1);
			response.setValue(orders);
			return Response.ok(response).cookie(new NewCookie("name", "Hello, world!")).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}		
	}

	

	@Path("/listbypage")
	@POST
	@Timed
	public Response getOrdersByPage(final DataTablePageParameters dtPageParameters, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			//System.out.println("before create order");
			OrderResponse orderRes=orderService.getOrdersByPage(dtPageParameters);
			//System.out.println("after get orders by page");
			response.setValue(orderRes);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	/*
	 * NLP based Search for ordering products
	 */
	@Path("/nlpsearchfororder")
	@GET
	@Timed
	public Response productsByNLPWords(@QueryParam("words") String words, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			NLPSearchResponse nlpSearchResponse = orderService.getProductsAndOrderInfoByNLPWords(words);
			response.setValue(nlpSearchResponse);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	@Path("/ordersbyproductspermonth")
	@GET
	@Timed
	public Response getOrderNumbersByProductsPerMonth(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			//System.out.println("before create order");
			List<ReportData> orders=orderService.getOrdersGroupbyProductAndMonth();
			//System.out.println("after get orders by page");
			response.setValue(orders);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	@Path("/create")
	@POST
	@Timed
	public Response createOrder(final Order order, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			//System.out.println("before create order");
			orderService.createOrder(order);
			//System.out.println("after create order");
			response.setValue(order);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	@Path("/update")
	@POST
	@Timed
	public Response updateOrder(final Order order, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			orderService.updateOrder(order);
			response.setValue(order);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}	
	}
	
	
	@Path("/updateorderforstatus")
	@POST
	@Timed
	public Response updateOrderForStatus(final Order order, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			orderService.updateOrderForStatus(order);
			response.setValue(order);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}	
	}
	
	
	@Path("/orderstatuslist")
	@GET
	@Timed
	public Response orderStatusList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<OrderStatus> orderStatusList = orderService.getOrderStatusList();
			response.setValue(orderStatusList);
			return Response.ok(response).cookie(new NewCookie("name", "Hello, world!")).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}		
	}

}
