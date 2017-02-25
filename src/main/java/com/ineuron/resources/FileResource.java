package com.ineuron.resources;


import javax.ws.rs.Consumes;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ineuron.api.INeuronResponse;
import com.ineuron.common.exception.InvalidAPITokenException;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.domain.product.valueobject.Material;
import com.ineuron.domain.user.service.SecurityService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {

	private static String fileLocation="/ineuron-nginx/data/images/";
	
	@Inject
	private SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);

	public FileResource() {
		super();
		//fileLocation="/file-inventory/images/";
	}
	
	@Path("/upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
	        @FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {

		String path = System.getProperty("user.dir");
		
		String uploadedFileLocation = path + fileLocation + fileDetail.getFileName();
		LOGGER.info("uploading " + uploadedFileLocation);
	    // save it
	    writeToFile(uploadedInputStream, uploadedFileLocation);
	    String output = "File uploaded to : " + uploadedFileLocation;
	    return Response.ok(output).build();
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
	    int read;
	    final int BUFFER_LENGTH = 1024;
	    final byte[] buffer = new byte[BUFFER_LENGTH];
	    OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	    while ((read = uploadedInputStream.read(buffer)) != -1) {
	        out.write(buffer, 0, read);
	    }
	    out.flush();
	    out.close();
	}

	
	@Path("/download")
	@GET
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response downloadFile(@QueryParam("filename") String filename, @Context HttpHeaders httpHeader) throws IOException {

		try {
			INeuronResponse response = new INeuronResponse(securityService, httpHeader, false);
			String path = System.getProperty("user.dir");
			
			String expectedFileLocation = path + fileLocation + filename;
			LOGGER.info("downloading " + expectedFileLocation);
		    //System.out.println("filelocation: "+expectedFileLocation);
			File file = new File(expectedFileLocation);
			//InputStream fileInputStream = new FileInputStream(file);
			//fileInputStream.read();
			//System.out.println("bytes in stream:" + fileInputStream.available()); 
			//ObjectInputStream input = new ObjectInputStream(fileInputStream);
			//Object object = (Object) input.readObject(); //etc.
			//input.close();
			//response.setValue(fileInputStream);
		    return Response.ok(file, "application/zip").build();
		    
		} catch (InvalidAPITokenException e) {
			LOGGER.error(e.getMessage(), e.getRootCause());
			return Response.status(Status.UNAUTHORIZED).build();
		} 
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		FileResource.fileLocation = fileLocation;
	}
}
