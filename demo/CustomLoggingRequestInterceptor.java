/*
 * SAP Innovative Business Solutions
 *
 * Copyright (c) 2021 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.example.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;



/**
 * The Class CustomLoggingRequestInterceptor to intercept and print request json if debug enabled.
 *
 */
public class CustomLoggingRequestInterceptor implements ClientHttpRequestInterceptor
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomLoggingRequestInterceptor.class);

	/**
	 * Intercept.
	 *
	 * @param request the request
	 * @param body the body
	 * @param execution the execution
	 * @return the client http response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
	{
		 
			traceRequest(request, body);
		 
		return execution.execute(request, body);
	}

	/**
	 * Trace request.
	 *
	 * @param request the request
	 * @param body the body
	 */
	private void traceRequest(HttpRequest request, byte[] body)
	{
		LOGGER.info("===========================request begin================================================");
		LOGGER.info("URI         : {}", request.getURI());
		LOGGER.info("Method      : {}", request.getMethod());
		LOGGER.info("Headers     : {}", request.getHeaders());
		if(LOGGER.isInfoEnabled()) {
		//LOGGER.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
		}

		System.out.println(body);
		

		LOGGER.info("==========================request end================================================");

	}


}
