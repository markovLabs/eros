package com.markovlabs.eros;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

public class ErosAppConfig extends Configuration {
	
	@NotEmpty private String dbURL;
	@NotEmpty private String dbUser;
	@NotEmpty private String dbPassword;
	@NotEmpty private String imgQueue;

	public String getImgQueue() {
		return imgQueue;
	}

	public void setImgQueue(String imgQueue) {
		this.imgQueue = imgQueue;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDbUser() {
		return dbUser;
	}
	
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}
	
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

}
