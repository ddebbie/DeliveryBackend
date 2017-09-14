package com.ddebbie.service.common;

import java.io.StringWriter;
import java.io.Writer;

import com.ddebbie.exception.BusinessException;
import com.ddebbie.exception.SystemException;
import com.ddebbie.service.descriptors.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Ram
 * 13-Sep-2017
 */
public class ServiceResponse {

	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public static ServiceResponse getSuccessResponse(Object payload) {
		ServiceResponse response = new ServiceResponse();
		response.setPayload(payload);
		response.setStatus(RESPONSE_STATUS_SUCCESS);
		return response;
	}
	
	public static String getFailureResponseString(Throwable ex) {
		ServiceResponse response = getFailureResponse(ex);
		try {
			return new ObjectMapper().writeValueAsString(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String getStackTraceMessage(Throwable ex) {
	     Writer writer = new StringWriter();
	     return writer.toString();
	 }
	
	public static ServiceResponse getFailureResponse(Throwable ex) {
		ServiceError error = new ServiceError();
		if (ex instanceof BusinessException ) {
			BusinessException be = (BusinessException) ex;
			error.setCode(be.getCode());
			error.setMessage(be.getMessage());
			//Logger.getLogger(Constants.BUSINESS_EXCEPTION_LOGGER).log(Level.ERROR, getStackTraceMessage(ex) );
		} else if (ex instanceof SystemException) {
			SystemException se = (SystemException) ex;
			error.setCode(se.getCode());
			error.setMessage(se.getMessage());
			//Logger.getLogger(Constants.SYSTEM_EXCEPTION_LOGGER).log(Level.ERROR, getStackTraceMessage(ex) );
		} else {
			//Logger.getLogger(Constants.UNHANDLED_EXCEPTION_LOGGER).log(Level.ERROR, getStackTraceMessage(ex) );
		}
		
		ServiceResponse response = new ServiceResponse();
		response.setStatus(RESPONSE_STATUS_ERROR);
		response.setPayload(error);
		return response;
	}
	
	public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";
	
	@JsonView(BaseView.class)
	private Object payload;
	
	@JsonView(BaseView.class)
	private String status;

}
