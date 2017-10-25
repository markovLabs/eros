package com.markovlabs.eros;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.bundles.version.VersionBundle;
import io.dropwizard.bundles.version.VersionSupplier;
import io.dropwizard.bundles.version.suppliers.MavenVersionSupplier;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ErosWebApp extends Application<ErosWebAppConfig> {
	
	private static final String EROS_ARTIFACT_ID = "eros-web";
	private static final String EROS_GROUP_ID = "com.markovLabs";
	
	@Override
	public void initialize(Bootstrap<ErosWebAppConfig> bootstrap) {
		VersionSupplier versionSupplier = new MavenVersionSupplier(EROS_GROUP_ID, EROS_ARTIFACT_ID);
		bootstrap.addBundle(new VersionBundle(versionSupplier));
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle("/assets", "/"));
	}

	@Override
	public void run(ErosWebAppConfig config, Environment env) throws Exception {
		ObjectMapper jsonMapper = env.getObjectMapper();
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
		jsonMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		jsonMapper.setDateFormat(new SimpleDateFormat("YYYY-MM-DD"));
	}

	public static void main(String[] args) throws Exception {
		new ErosWebApp().run(args);
	}

}
