package com.ddebbie.service;





import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ddebbie.exception.BusinessException;
import com.ddebbie.service.common.ServiceResponse;
/*import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;*/
/**
 * Base Service for all the RESTFUL Services
 * @author Ram
 * 13-Sep-2017
 */
public class BaseService {

	public static final String SESSION_TOKEN_NAME = "ddebbieSessionToken";
	
	//@CookieParam(SESSION_TOKEN_NAME)
	String ddebbieSessionToken;
	

	/*
	 * Get the session id for the user session
	 */

    public BaseService() {
    }

    public String getSessionId() {
		return ddebbieSessionToken;
	}

    
    /**
     * 
     * @param ex
     * All the exceptions that occur in the application will trigger the below method
     */
	@ExceptionHandler(BusinessException.class)
	public ServiceResponse handleCustomException(BusinessException ex) {
		return ServiceResponse.getFailureResponse(ex);
 
	}
	
	public String getIpAddress(HttpServletRequest request)
	{
		String  ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
