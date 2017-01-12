package com.ineuron.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ineuron.api.INeuronResponse;
import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.domain.product.valueobject.Material;
import com.ineuron.domain.product.service.ProductService;
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

@Path("/material")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class MaterialResource {

	@Inject
	private ProductService productService;

	@Inject
	private SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MaterialResource.class);

	public MaterialResource() {
		super();
	}

	// Material

	@Path("/list")
	@GET
	@Timed
	public Response materialList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<Material> materials = productService.getMaterials();
			response.setValue(materials);
			return Response.ok(response).cookie(new NewCookie("name", "Hello, world!")).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/materialbyname")
	@GET
	@Timed
	public Response materialByName(@QueryParam("name") String name, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			Material material = productService.getMaterialByName(name);
			response.setValue(material);
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
	public Response createMaterial(final Material material, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.createMaterial(material);
			response.setValue(material);
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
	public Response updateMaterial(final Material material, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.updateMaterial(material);
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
	public Response deleteMaterial(final Material material, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.deleteMaterial(material);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

}
