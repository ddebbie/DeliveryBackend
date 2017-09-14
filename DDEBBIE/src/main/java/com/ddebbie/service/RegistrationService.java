/**
 * sarvapal
 */
package com.ddebbie.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ddebbie.exception.BusinessException;
import com.ddebbie.handler.UserHandler;
import com.ddebbie.model.User;
import com.ddebbie.service.common.GenericResponse;
import com.ddebbie.utils.Utils;

/**
 * @author sarvapal
 *
 */
@RestController
@RequestMapping("registration")
public class RegistrationService extends BaseService {

	@Autowired
	UserHandler userHandler;
	@Autowired
	Utils utils;
	
	@Transactional
	@RequestMapping(value="", method=RequestMethod.GET)
	public User sayHi()throws BusinessException{
		 User obj =new User();
		 return obj;
		
	}
	
	@Transactional
	@RequestMapping(value="signupUser", method=RequestMethod.POST)
	public GenericResponse registerUserAccount(@RequestBody User user)throws BusinessException{
		System.out.println("inregisterUserAccount" );
		 final User registered = userHandler.registerNewUserAccount(user);
		 return new GenericResponse("success");
		
	}
}
