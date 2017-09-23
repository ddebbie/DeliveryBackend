package com.ddebbie.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddebbie.exception.BusinessException;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.ExceptionMessages;
import com.ddebbie.handler.UserHandler;
import com.ddebbie.model.CookieToken;
import com.ddebbie.model.Role;
import com.ddebbie.model.User;
import com.ddebbie.model.input.ChangeUserPassword;
import com.ddebbie.service.common.GenericResponse;
import com.ddebbie.utils.Utils;

/**
 * 
 * @author Ram
 *
 */
@RestController
@RequestMapping("user")
public class UserService extends BaseService {
	@Autowired
	UserHandler userHandler;
	@Autowired
	Utils utils;

	// Should return cookie token
	@Transactional
	@RequestMapping(value = "login", method = RequestMethod.POST,consumes = {"application/x-www-form-urlencoded"})
	public CookieToken userLogin( User user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CookieToken cookieToken = null;
		System.out.println("In User Login :: " + user.getEmail() + " passowrd ::" + user.getPassword());
		cookieToken = userHandler.authenticate(user, request, response);

		return cookieToken;
	}

	@Transactional
	@RequestMapping(value = "changeUserPassword", method = RequestMethod.POST)
	public boolean changeUserPassword(@RequestBody ChangeUserPassword changeUserPassword) throws BusinessException {
		return userHandler.changeUserPassword(changeUserPassword);
	}
	
	@Transactional
	@RequestMapping(value = "resetPasswordRequest", method = RequestMethod.POST)
	public String resetPasswordRequest(@RequestParam("email") String email) throws BusinessException {
		return userHandler.resetPasswordRequest(email);

	}
	
	@Transactional
	@RequestMapping(value = "resetVerifyCode", method = RequestMethod.POST)
	public GenericResponse resetVerifyCode(@RequestParam("email") String email,@RequestParam("token") String token) throws BusinessException {
		boolean tokenFlag=userHandler.findByPasswordResetCode(email,token);
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
	@RequestMapping(value = "getRole", method = RequestMethod.POST)
	public Role getRole(@RequestParam(value = "id") long roleId) throws BusinessException {
		return userHandler.getRole(roleId);
	}

	@Transactional
	@RequestMapping(value = "getRoleByAdminType", method = RequestMethod.POST)
	public Role getRoleByadminType(@RequestParam(value = "id") long roleId,
			@RequestParam(value = "adminType") int adminType) throws BusinessException {
		return userHandler.getRoleByAdminType(roleId, adminType);
	}

	@Transactional
	@RequestMapping(value = "isDuplicateUserName", method = RequestMethod.POST)
	public boolean isDuplicateUserName(@RequestParam("userName") String userName) throws BusinessException {
		return userHandler.isDuplicateUserName(userName);

	}

	@Transactional
	@RequestMapping(value = "isDuplicateUserEmail", method = RequestMethod.POST)
	public boolean isDuplicateUserEmail(@RequestParam("email") String email) throws BusinessException {
		return userHandler.isDuplicateUserEmail(email);

	}

	@Transactional
	@RequestMapping(value = "getUserInfoForHeader", method = RequestMethod.POST)
	public User getUserInfoForHeader(@RequestBody CookieToken cookieToken) throws BusinessException {
		return userHandler.getUserInfoForHeader(cookieToken);
	}

}
