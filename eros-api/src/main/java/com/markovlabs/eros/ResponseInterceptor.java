package com.markovlabs.eros;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseInterceptor implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
		resp.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
		resp.getHeaders().putSingle("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		resp.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
	}

}
