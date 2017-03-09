package com.ineuron.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ineuron.api.INeuronResponse;
import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.domain.product.entity.Formula;
import com.ineuron.domain.product.entity.Product;
import com.ineuron.domain.product.valueobject.ProductCategory;
import com.ineuron.domain.product.valueobject.AttributeCategory;
import com.ineuron.domain.product.valueobject.Attribute;
import com.ineuron.domain.product.valueobject.ProductPackageType;
import com.ineuron.domain.product.valueobject.ProductPrice;
import com.ineuron.domain.product.valueobject.ManufacturingProcess;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class ProductResource {

	@Inject
	private ProductService productService;

	@Inject
	private SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

	public ProductResource() {
		super();
	}

	@Path("/createproductcategory")
	@POST
	@Timed
	public Response createProductCategory(final ProductCategory productCategory, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.createProductCategory(productCategory);
			response.setValue(productCategory);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	@Path("/updateproductcategory")
	@POST
	@Timed
	public Response updateProductCategory(final ProductCategory productCategory, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.updateProductCategory(productCategory);
			response.setValue(productCategory);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/getproductcategorybyname")
	@POST
	@Timed
	public Response getProductCategoryByName(final String name, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			ProductCategory productCategory = productService.getProductCategoryByName(name);
			response.setValue(productCategory);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/getproductcategorybyid")
	@POST
	@Timed
	public Response getProductCategoryById(final Integer id, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			ProductCategory productCategory = productService.getProductCategoryById(id);
			response.setValue(productCategory);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/getproductcategorybycode")
	@POST
	@Timed
	public Response getProductCategoryByCode(final String code, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			ProductCategory productCategory = productService.getProductCategoryByCode(code);
			response.setValue(productCategory);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/productcategorylist")
	@GET
	@Timed
	public Response productCategoryList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<ProductCategory> productCategories = productService.getProductCategoryList();
			response.setValue(productCategories);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/deleteproductcategory")
	@POST
	@Timed
	public Response deleteProductCategory(final ProductCategory productCategory, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.deleteProductCategory(productCategory);
			// response.setValue(productCategory);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/createproduct")
	@POST
	@Timed
	public Response createProduct(final Product product, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.createProduct(product);
			response.setValue(product);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/deleteproduct")
	@POST
	@Timed
	public Response deleteProduct(final Product product, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.deleteProduct(product);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/updateproduct")
	@POST
	@Timed
	public Response updateProduct(final Product product, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.updateProduct(product);
			response.setValue(product);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/productlist")
	@GET
	@Timed
	public Response productList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<Product> products = productService.getProductList();
			response.setValue(products);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/productlistbycategory")
	@POST
	@Timed
	public Response productListByCategory(final Integer productCategoryId, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<Product> products = productService.getProductListByCategory(productCategoryId);
			response.setValue(products);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/getproductbyname")
	@POST
	@Timed
	public Response productByName(final String name, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			Product product = productService.getProductByName(name);
			response.setValue(product);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/productbyid")
	@GET
	@Timed
	public Response productById(@QueryParam("id") Integer productId, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			Product product = productService.getProductById(productId);
			response.setValue(product);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/attributecategorylist")
	@GET
	@Timed
	public Response attributeCategoryList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<AttributeCategory> attributeCategories = productService.getAttributeCategoryList();
			response.setValue(attributeCategories);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/attributelist")
	@GET
	@Timed
	public Response attributeList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<Attribute> attributes = productService.getAttributeList();
			response.setValue(attributes);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/attributesbycategoryid")
	@POST
	@Timed
	public Response attributesByCategoryId(final Integer attributeCategoryId, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<Attribute> attributes = productService.getAttributesByCategoryId(attributeCategoryId);
			response.setValue(attributes);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/getattributebyname")
	@POST
	@Timed
	public Response attributeByName(final String name, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			Attribute attribute = productService.getAttributeByName(name);
			response.setValue(attribute);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/createattribute")
	@POST
	@Timed
	public Response createAttribute(final Attribute attribute, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.createAttribute(attribute);
			response.setValue(attribute);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/updateattribute")
	@POST
	@Timed
	public Response updateAttribute(final Attribute attribute, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.updateAttribute(attribute);
			response.setValue(attribute);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}		
	}

	@Path("/deleteattribute")
	@POST
	@Timed
	public Response deleteAttribute(final Attribute attribute, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.deleteAttribute(attribute);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/saveprocesses")
	@POST
	@Timed
	public Response saveProcesses(final List<ManufacturingProcess> processes,
			@QueryParam("hasformula") boolean hasFormula, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			productService.saveProcesses(processes, hasFormula);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/formulas")
	@GET
	@Timed
	public Response formulas(@Context HttpHeaders httpHeader, @QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			List<Formula> formulas = productService.getFormulas();
			response.setValue(formulas);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/formulabyid")
	@GET
	@Timed
	public Response formulaById(@QueryParam("id") String formulaId, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			Formula formula = productService.getFormulaById(formulaId);
			response.setValue(formula);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/createformula")
	@POST
	@Timed
	public Response createFormula(final Formula formula, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			productService.addFormula(formula);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/updateformula")
	@POST
	@Timed
	public Response updateFormula(final Formula formula, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		LOGGER.info("updateformula:" + formula);
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			productService.updateFormula(formula);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@Path("/deleteformula")
	@POST
	@Timed
	public Response deleteFormula(@QueryParam("id") String id, @Context HttpHeaders httpHeader,
			@QueryParam("debug") boolean debug) {
		LOGGER.info("deleteformula:" + id);
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, debug);
			productService.deleteFormula(id);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	@Path("/updateproductprice")
	@POST
	@Timed
	public Response updateProductPrice(final ProductPrice productPrice, @Context HttpHeaders httpHeader) {
		try {
			System.out.println("updateproductprice");
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.updateProductPrice(productPrice);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}		
	}
	
	
	@Path("/productpackagetypelist")
	@GET
	@Timed
	public Response productPackageList(@Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			List<ProductPackageType> productPackageTypes = productService.getProductPackageTypes();
			response.setValue(productPackageTypes);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	@Path("/createproductpackagetype")
	@POST
	@Timed
	public Response createProductPackageType(final ProductPackageType productPackageType, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.createProductPackageType(productPackageType);
			return Response.ok(response).build();
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		}		
	}
	
	@Path("/updateproductpackagetype")
	@POST
	@Timed
	public Response updateProductPackageType(final ProductPackageType productPackageType, @Context HttpHeaders httpHeader) {
		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			productService.updateProductPackageType(productPackageType);
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
