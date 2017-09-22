/**
 * sarvapal
 */
package com.ddebbie.service;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddebbie.exception.BusinessException;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.ExceptionMessages;
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
	@Autowired
	private EmailService emailService;
	
	 private static final int MAX_VERIFICATION_CODE = 100000;
	 private static final int MIN_VERIFICATION_CODE = 999999;


	@Transactional
	@RequestMapping(value = "", method = RequestMethod.GET)
	public User sayHi() throws BusinessException {
		User obj = new User();
		return obj;

	}
	//Currently unused as we are using verification code mechanism
	@Transactional
	@RequestMapping(value = "signupUserConfirm", method = RequestMethod.GET)
	public GenericResponse confirmUserAccountUUID(@RequestParam("token") String token) throws BusinessException {
		boolean tokenFlag=userHandler.findByConfirmationToken(token);
		if(tokenFlag)
		{
			return new GenericResponse("success");
		}
		else
		{
			throw  new BusinessException(ExceptionCodes.VERIFICATION_CODE_INVALID, ExceptionMessages.VERIFICATION_CODE_INVALID);
		}
	}
	
	@Transactional
	@RequestMapping(value = "signupVerifyCode", method = RequestMethod.GET)
	public GenericResponse confirmUserAccount(@RequestParam("email") String email,@RequestParam("token") String token) throws BusinessException {
		boolean tokenFlag=userHandler.findByVerificationCode(email,token);
		if(tokenFlag)
		{
			return new GenericResponse("success");
		}
		else
		{
			throw  new BusinessException(ExceptionCodes.VERIFICATION_CODE_INVALID, ExceptionMessages.VERIFICATION_CODE_INVALID);	
		}
	}

	@Transactional
	@RequestMapping(value = "signupUser", method = RequestMethod.POST)
	public GenericResponse registerUserAccount(@RequestBody User user,HttpServletRequest request) throws BusinessException {
		System.out.println("inregisterUserAccount");
		String confirmToken = UUID.randomUUID().toString();
		Random rand = new Random();
        Integer code = rand.nextInt(MIN_VERIFICATION_CODE
                - MAX_VERIFICATION_CODE + 1) + MAX_VERIFICATION_CODE;
        String verificationCode=code.toString();
		final User registered = userHandler.registerNewUserAccount(user,confirmToken,verificationCode);
		if (registered != null) {
			
			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo("arvapallishiva@gmail.com");
			registrationEmail.setCc("ddebbiedelivery@gmail.com");
			
			registrationEmail.setSubject("Registration Confirmation - Verification Code");
			String appUrl = request.getScheme() + "://" + request.getServerName()+":8080/DDEBBIECourityAPI-0.0.1-SNAPSHOT";

			registrationEmail.setText("To confirm your e-mail address, please enter the verification code:\n"
					+ verificationCode);
			registrationEmail.setFrom("ddebbiedelivery@gmail.com");
			System.out.println("verificationCode:"+verificationCode);
			emailService.sendEmail(registrationEmail);
		}
		return new GenericResponse("success");

	}
	

	

}
