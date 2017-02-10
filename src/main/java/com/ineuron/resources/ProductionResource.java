package com.ineuron.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ineuron.api.INeuronResponse;
import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.domain.production.entity.Production;
import com.ineuron.domain.production.valueobject.ProductionPeriod;
import com.ineuron.domain.production.service.ProductionService;
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

@Path("/production")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class ProductionResource {

	@Inject
	private ProductionService productionService;

	@Inject
	private SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductionResource.class);

	public ProductionResource() {
		super();
	}

	// Production

	/*@Path("/list")
	@GET
	@Timed
	public Response productionList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService,
					httpHeader, false);
			List<Production> productions = productionService.getProductions();
			response.setValue(productions);
			return Response.ok(response)
					.cookie(new NewCookie("name", "Hello, world!")).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}*/

	@Path("/periodsbyproductid")
	@GET
	@Timed
	public Response productionPeriodsByProductId(@QueryParam("productId") Integer productId,
			@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService,
					httpHeader, false);
			List<ProductionPeriod> productionPeriods = productionService.getProductionPeriodsByProductId(productId);
			response.setValue(productionPeriods);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	/*@Path("/create")
	@POST
	@Timed
	public Response createProduction(final Production production,
			@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService,
					httpHeader, false);
			productionService.createProduction(production);
			response.setValue(production);
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
	public Response updateProduction(final Production production,
			@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService,
					httpHeader, false);
			productService.updateProduction(production);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@Path("/delete")
	@POST
	@Timed
	public Response deleteProduction(final Production production,
			@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService,
					httpHeader, false);
			productService.deleteProduction(production);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}*/

}
