package com.ineuron;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.ineuron.common.exception.RepositoryException;
import com.ineuron.domain.user.util.DesUtil;
import com.ineuron.domain.user.valueobject.RolesCache;

/**
 * I-Neuron Entry Point!
 * 
 */
public class INeuronApplication extends Application<INeuronConfiguration> {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(INeuronApplication.class);
	
	public static void main(String[] args) throws Exception {
		new INeuronApplication().run(args);
	}

	@Override
	public String getName() {
		return "I-Neuron";
	}

	@Override
	public void initialize(Bootstrap<INeuronConfiguration> bootstrap) {
		bootstrap.addBundle(new AssetsBundle("/assets/", "/ineuron/"));
		
		
		//Guice是Google开发的一个轻量级，基于Java5（主要运用泛型与注释特性）的依赖注入框架(IOC)。
		GuiceBundle<INeuronConfiguration> guiceBundle = GuiceBundle.<INeuronConfiguration>newBuilder()
				.addModule(new INeuronModule())
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(INeuronConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
	}

	@Override
	public void run(INeuronConfiguration configuration, Environment environment) {
		try {
			environment.jersey().register(MultiPartFeature.class);
			RolesCache.init();
			LOGGER.info("RolesCache is initiallized...");
			
			DesUtil.encrypt("init............");
			LOGGER.info("DesUtil is initiallized...");
			
			
		} catch (RepositoryException e) {
			LOGGER.error("Failed to initiallize the RolesCache", e);
		} catch (Exception e) {
			LOGGER.error("Failed to initiallize DesUtil", e);
		}
	}

}
