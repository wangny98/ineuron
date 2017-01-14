package com.ineuron.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ineuron.api.INeuronResponse;
import com.ineuron.common.exception.INeuronException;
import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.domain.user.entity.User;
import com.ineuron.domain.user.service.SecurityService;
import com.ineuron.domain.user.service.UserService;
import com.ineuron.domain.user.valueobject.Role;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8") 
public class UserResource {

	@Inject
	private UserService userService;
	
	@Inject
	private SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

	public UserResource() {
		super();
	}
	
	@Path("/validateloginstatus")
	@GET
	@Timed
	public Response validateLoginStatus(@Context HttpHeaders httpHeader) {	
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			if(response.getApiToken() != null){
				LOGGER.info("user/validateloginstatus newApiToken=" + response.getApiToken());
				return Response.ok(response).build();
			}else{
				return Response.status(Status.UNAUTHORIZED).build();			
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@Path("/authenticate")
	@POST
	@Timed
	public Response login(final User user, @Context final UriInfo uriInfo) {
		System.out.println("LOGGER.isDebugEnabled() = " + LOGGER.isDebugEnabled());
		INeuronResponse response = new INeuronResponse();
		try {
			User foundUser=userService.doAuthenticate(user);
			if(foundUser!=null){
				String apiToken = securityService.createApiToken(user.getUsername());
				LOGGER.info("user/authenticate newApiToken=" + apiToken);
				response.setValue(foundUser);
				response.setApiToken(apiToken);
				System.out.println("return authenticate");
				return Response.ok(response).build();
			}else{
				return Response.status(Status.UNAUTHORIZED).build();
			}
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@Path("/register")
	@POST
	@Timed
	public Response signup(final User user, @Context final UriInfo uriInfo) {
		try {
			userService.doRegister(user);
			return Response.ok().build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@Path("/update")
	@POST
	@Timed
	public Response update(final User user, @Context final UriInfo uriInfo, @Context HttpHeaders httpHeader) {
		
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			LOGGER.info("user/update newApiToken=" + response.getApiToken());
			userService.updateUser(user);			
			response.setValue(user);
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
	public Response delete(final User user, @Context final UriInfo uriInfo, @Context HttpHeaders httpHeader) {
		
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			LOGGER.info("user/delete newApiToken=" + response.getApiToken());
			userService.deleteUser(user);			
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}


	@Path("/list")
	@GET
	@Timed
	public Response getUserList(@Context HttpHeaders httpHeader, @QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug); 
			LOGGER.info("user/list newApiToken=" + response.getApiToken());
			List<User> users = userService.getUserList();
			response.setValue(users);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	

	@Path("/user")
	@GET
	@Timed
	public Response getUserByUsername(@QueryParam("username") String username) {
		
		LOGGER.info("in userResource: getUserByUsername. username:" + username);
		try {
			INeuronResponse response = new INeuronResponse(); 
			User user=userService.getUserByUsername(username);			
			response.setValue(user);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} 
	}

	
	@Path("/createrole")
	@POST
	@Timed
	public Response createRole(final Role role, @Context final UriInfo uriInfo, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			LOGGER.info("user/createrole newApiToken=" + response.getApiToken());
			userService.createRole(role);
			response.setValue(role);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}
	
	@Path("/updaterole")
	@POST
	@Timed
	public Response updateRole(final Role role, @Context final UriInfo uriInfo,  @Context HttpHeaders httpHeader) {
		
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			LOGGER.info("user/updaterole newApiToken=" + response.getApiToken());
			userService.updateRole(role);			
			response.setValue(role);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}
	
	@Path("/deleterole")
	@POST
	@Timed
	public Response deleteRole(final Role role, @Context final UriInfo uriInfo, @Context HttpHeaders httpHeader) {
		
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			userService.deleteRole(role);			
			response.setValue(role);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		} 

	}
	
	@Path("/rolelist")
	@GET
	@Timed
	public Response getRoleList(@Context HttpHeaders httpHeader) {
		
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false); 
			List<Role> roles = userService.getRoleList();
			response.setValue(roles);
			return Response.ok(response).build();	
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (INeuronException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

}
