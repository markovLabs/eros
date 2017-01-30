package com.markovlabs.eros;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

public class ErosAppConfig extends Configuration {
	
	@NotEmpty private String dbURL;

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

}
