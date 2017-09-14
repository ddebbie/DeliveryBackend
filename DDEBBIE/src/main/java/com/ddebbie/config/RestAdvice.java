package com.ddebbie.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.ddebbie.model.output.XmlUrlSet;
import com.ddebbie.service.common.ServiceResponse;

@ControllerAdvice
public class RestAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
			MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> converter,
			ServerHttpRequest request, ServerHttpResponse response) {

               if(body instanceof XmlUrlSet){
   return body;
  }else{
   return body instanceof String ? body
     : (body instanceof ServiceResponse ? body : ServiceResponse
       .getSuccessResponse(body));
  }

	}

	@Override
	public boolean supports(MethodParameter methodParameter,
			Class<? extends HttpMessageConverter<?>> converter) {
		return true;
	}

}
