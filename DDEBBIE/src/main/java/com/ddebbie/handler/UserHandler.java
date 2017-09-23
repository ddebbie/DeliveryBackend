package com.ddebbie.handler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ddebbie.cache.CacheManager;
import com.ddebbie.cache.CacheRegionType;
import com.ddebbie.dao.RoleDAO;
import com.ddebbie.dao.UserDAO;
import com.ddebbie.exception.BusinessException;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.ExceptionMessages;
import com.ddebbie.model.CookieToken;
import com.ddebbie.model.File;
import com.ddebbie.model.ObjectTypes;
import com.ddebbie.model.Role;
import com.ddebbie.model.User;
import com.ddebbie.model.UserAuthentication;
import com.ddebbie.model.input.ChangeUserPassword;
import com.ddebbie.model.output.LoginType;
import com.ddebbie.pagination.Paginator;
import com.ddebbie.service.EmailService;
import com.ddebbie.service.common.GenericResponse;
import com.ddebbie.service.descriptors.ResetPassword;
import com.ddebbie.utils.Constants;
import com.ddebbie.utils.Utils;

/**
 * @author Ram
 *
 */
@Service("userHandler")
public class UserHandler implements UserDetailsService {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	FileHandler fileHandler;

	@Autowired
	Utils utils;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private EmailService emailService;
	
	private static final int MAX_VERIFICATION_CODE = 100000;
	 private static final int MIN_VERIFICATION_CODE = 999999;
	
	
	/**
	 * @param user
	 * @return
	 * @throws BusinessException
	 */
	public User registerNewUserAccount(User user,String confirmToken,String verificationCode) throws BusinessException {
		User obj =null;
		if(validateEmailUser(user))
		{
			user.setPassword(encodeUserPassword(user.getPassword()));
			user.setCts(new Timestamp(new Date().getTime()));
			user.setMts(new Timestamp(new Date().getTime()));
			user.setActive(false);
			user.setCreatorId(1248);
			user.setModifierId(1248);
			user.setConfirmationToken(confirmToken);
			user.setVerificationCode(verificationCode);
			obj = (User) userDAO.persist(user);
		}
		return obj;
	}

	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User userByName = userDAO.getUserByName(userName);
		if (userByName == null) {
			userByName = userDAO.getUserByEmail(userName);
		}

		UserAuthentication userAuthentication;
		boolean userStatus = true;
		if (userByName != null) {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			// TODO: Get User roles and assign roles to SimpleGrantedAuthority
			int pageNumber = 1;
			int pageSize = 10;
			Paginator<Role> roles = getRoles(userByName.getId(), pageNumber, pageSize);

			if (roles.getCount() > 0) {
				List<Role> listOfRoles = roles.getList();
				for (Role role : listOfRoles) {
					authorities.add(new SimpleGrantedAuthority(role.getRoleValue()));
				}

			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			}
		/*	if (userByName.getStatus() != User.STATUS_ACTIVE) {
				userStatus = false;
			}*/
			

			userAuthentication = new UserAuthentication(userByName, authorities);
		} else {
			userAuthentication = new UserAuthentication();
		}
		return userAuthentication;
	}

	public Map<Long, User> getUserMap(Collection<Long> userIds) {
		Map<Long, User> userMap = new HashMap<Long, User>();
		Map<Long, File> profilePicMap = new HashMap<Long, File>();
		if (CollectionUtils.isNotEmpty(userIds)) {
			List<User> userList = userDAO.getUsers(userIds);
			ArrayList<Long> profilePicIds = new ArrayList<Long>();
			if (CollectionUtils.isNotEmpty(userList)) {
				for (User user : userList) {
					if (user.getProfileImageFileId() > 0)
						profilePicIds.add(user.getProfileImageFileId());
				}
				if (CollectionUtils.isNotEmpty(profilePicIds)) {
					profilePicMap = fileHandler.getIdFileMap(profilePicIds);
				}
				/*for (User user : userList) {
					if (profilePicMap.containsKey(user.getProfileImageFileId()))
						user.setProfilePicture(profilePicMap.get(user.getProfileImageFileId()));
					userMap.put(user.getId(), user);
				}*/
			}
		}
		return userMap;
	}

	public User save(User user) {
		return (User) userDAO.persist(user);
	}

	public CookieToken resetPassword(ResetPassword resetPassword) throws BusinessException {
		LoginType loginType = new LoginType();
		if (resetPassword.getNewPassword() == null || resetPassword.getNewPassword() == "") {
			throw new BusinessException(ExceptionCodes.PASSWORD_NEWPASSWORD_MANDATORY,
					ExceptionMessages.PASSWORD_NEWPASSWORD_MANDATORY);
		}
		if (resetPassword.getRetypePassword() == null || resetPassword.getRetypePassword() == "") {
			throw new BusinessException(ExceptionCodes.PASSWORD_RETYPEPASSWORD_MANDATORY,
					ExceptionMessages.PASSWORD_RETYPEPASSWORD_MANDATORY);
		}
		if (!(resetPassword.getNewPassword()).equals(resetPassword.getRetypePassword())) {
			throw new BusinessException(ExceptionCodes.RETYPE_PASSWORD_NOT_MATCHING,
					ExceptionMessages.RETYPE_PASSWORD_NOT_MATCHING);
		}
		
		//Verify if token and email match
		
		boolean tokenFlag=this.findByPasswordResetCode(resetPassword.getEmail(),resetPassword.getToken());
		if(tokenFlag)
		{
			User user=userDAO.getUserByEmail(resetPassword.getEmail());
			UserAuthentication ud = (UserAuthentication) loadUserByUsername(user.getEmail());
			user.setPassword(encodeUserPassword(resetPassword.getNewPassword()));
			boolean status = userDAO.update(user) == null ? false : true;
			if (status) {
				// Raju Nune Need to look into this changes -
				// MemCachedSessionSecurityContextRespository : saveContext//
				UUID token = UUID.randomUUID();
				cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).put(token.toString(), ud);
				SecurityContextHolder.getContext().setAuthentication(ud);
				// memCachedSessionSecurityContextRespository.saveContext(SecurityContextHolder.getContext(),
				// request, response);
				// response.addCookie(new
				// Cookie(Constants.OMICS_SESSION, token.toString()));
				cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).put(token.toString(), ud);
				if (token != null)
					return new CookieToken(token.toString(), utils.getLoggedInUserId());

			}
			
		}
		else
		{
			throw  new BusinessException(ExceptionCodes.VERIFICATION_CODE_INVALID, ExceptionMessages.VERIFICATION_CODE_INVALID);	
		}
		
		throw  new BusinessException(ExceptionCodes.UPDATE_FAILED, ExceptionMessages.UPDATE_FAILED);

	}

	public boolean isDuplicateUserName(User user) {
		return userDAO.isDuplicateUserName(user);
	}

	public boolean isDuplicateEmail(User user) {
		return userDAO.isDuplicateEmail(user);
	}

	public User update(User user) {
		User obj = (User) userDAO.update(user);
		// userSolrHandler.addOrUpdateUserToSolr(user);
		return obj;
	}

	public Role addRole(Role role) throws BusinessException {
		Role obj = (Role) roleDAO.persist(role);

		return obj;
	}

	
	
	
	/**
	 * @param currentPassword
	 * @param newPassword
	 * @param userName
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean changeUserPassword(ChangeUserPassword changeUserPassword) throws BusinessException {

		if (StringUtils.isEmpty(changeUserPassword.getCurrentPassword())) {
			throw new BusinessException(ExceptionCodes.PASSWORD_CURRENTPASSWORD_MANDATORY,
					ExceptionMessages.PASSWORD_CURRENTPASSWORD_MANDATORY);
		}
		if (StringUtils.isEmpty(changeUserPassword.getNewPassword())) {
			throw new BusinessException(ExceptionCodes.PASSWORD_NEWPASSWORD_MANDATORY,
					ExceptionMessages.PASSWORD_NEWPASSWORD_MANDATORY);
		}
		if (StringUtils.isEmpty(changeUserPassword.getRetypePassword())) {
			throw new BusinessException(ExceptionCodes.PASSWORD_RETYPEPASSWORD_MANDATORY,
					ExceptionMessages.PASSWORD_RETYPEPASSWORD_MANDATORY);
		}
		User userAuthentication=null;
		if(changeUserPassword.getCookieToken()!=null && utils.isUserAuthenticated(changeUserPassword.getCookieToken().getToken()))
		{
			userAuthentication = (User) userDAO.getObjectById(changeUserPassword.getCookieToken().getUserId(), ObjectTypes.USER);
		}

		if (userAuthentication == null) {
			throw new BusinessException(ExceptionCodes.USER_NOT_LOGGED_IN, ExceptionMessages.USER_NOT_LOGGED_IN);
		}

		if (userAuthentication != null
				&& !userAuthentication.getPassword().equals(changeUserPassword.getCurrentPassword())) {
			throw new BusinessException(ExceptionCodes.USER_PASSWORD_NOT_MATCHING,
					ExceptionMessages.USER_PASSWORD_NOT_MATCHING);
		}

		if (!(changeUserPassword.getNewPassword()).equals(changeUserPassword.getRetypePassword())) {
			throw new BusinessException(ExceptionCodes.RETYPE_PASSWORD_NOT_MATCHING,
					ExceptionMessages.RETYPE_PASSWORD_NOT_MATCHING);
		}

		User user = userDAO.getUserByEmail(userAuthentication.getEmail().toString());
		user.setPassword(changeUserPassword.getNewPassword());
		boolean status = userDAO.update(user) == null ? false : true;
		if (status) {
			// Raju Nune Need to look into this changes -
			// MemCachedSessionSecurityContextRespository : saveContext//
			userAuthentication.setPassword(user.getPassword());

		}
		return status;
	}

	public Paginator<User> matchUsers(String searchString, int pageNumber, int pageSize) {

		return userDAO.matchUsers(searchString, pageNumber, pageSize);
	}

	public Role getRole(long roleId) throws BusinessException {
		Role role = new Role();
		try {
			if (roleId > 0)
				role = (Role) roleDAO.getObjectById(roleId, ObjectTypes.ROLE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(ExceptionCodes.ROLE_NOT_FOUND, ExceptionMessages.ROLE_NOT_FOUND);
		}
		return role;
	}

	public Paginator<Role> getRoles(long userId, int pageNumber, int pageSize) {
		if (userId > 0)
			return roleDAO.getUsersRoleList(userId, pageNumber, pageSize);
		else
			return null;
	}

	private boolean validate(User user) throws BusinessException {
		if (StringUtils.isBlank(user.getName())) {
			throw new BusinessException(ExceptionCodes.USER_NAME_MANDATORY, ExceptionMessages.USER_NAME_MANDATORY);
		}

		if (StringUtils.isBlank(user.getName())) {
			throw new BusinessException(ExceptionCodes.USER_USERNAME_MANDATORY,
					ExceptionMessages.USER_USERNAME_MANDATORY);
		}

		if (isDuplicateUserName(user)) {
			throw new BusinessException(ExceptionCodes.USER_USERNAME_EXISTS, ExceptionMessages.USER_USERNAME_EXISTS);
		}

		return validateEmailUser(user);

	}

	public boolean validateEmailUser(User user) throws BusinessException {
		
		if (StringUtils.isBlank(user.getName())) {
			throw new BusinessException(ExceptionCodes.USER_NAME_MANDATORY, ExceptionMessages.USER_NAME_MANDATORY);
		}
		
		if (StringUtils.isBlank(user.getEmail())) {
			throw new BusinessException(ExceptionCodes.USER_EMAIL_MANDATORY, ExceptionMessages.USER_EMAIL_MANDATORY);
		}

		if (isDuplicateEmail(user)) {
			throw new BusinessException(ExceptionCodes.USER_EMAIL_EXISTS, ExceptionMessages.USER_EMAIL_EXISTS);
		}

		if (user.getId() == 0) {
			if (StringUtils.isBlank(user.getPassword())) {
				throw new BusinessException(ExceptionCodes.USER_PASSWORD_MANDATORY,
						ExceptionMessages.USER_PASSWORD_MANDATORY);
			}
		}
		return true;
	}

	public boolean isUser(long userId) throws BusinessException {
		/*
		 * try { getUser(userId); } catch (ObjectNotFoundException onf) { return
		 * false; }
		 */
		return true;
	}

	public List<User> getSpeakersByEmail(Collection<String> speakerEmails) {
		List<User> usersByEmail = null;
		if (CollectionUtils.isNotEmpty(speakerEmails))
			usersByEmail = userDAO.getUsersByEmail(speakerEmails);
		return usersByEmail;
	}

	public boolean isDuplicateUserName(String userName) throws BusinessException {
		if (userName == null) {
			throw new BusinessException(ExceptionCodes.USER_USERNAME_MANDATORY,
					ExceptionMessages.USER_USERNAME_MANDATORY);
		} else {
			return userDAO.isDuplicateUserName(userName);
		}

	}

	public boolean isDuplicateUserEmail(String email) throws BusinessException {
		if (email == null) {
			throw new BusinessException(ExceptionCodes.USER_EMAIL_MANDATORY, ExceptionMessages.USER_EMAIL_MANDATORY);
		} else {
			return userDAO.isDuplicateEmail(email);
		}
	}

	public User getUserInfoForHeader(CookieToken cookieToken) throws BusinessException {
		boolean isUserAuthenticated=utils.isUserAuthenticated(cookieToken.getToken());
		if(isUserAuthenticated)
		{
			
		
		long loggedInUserID = cookieToken.getUserId();
		if (loggedInUserID > 0) {
			User loggedInUser = (User) userDAO.getObjectById(loggedInUserID, ObjectTypes.USER);
		/*	if (loggedInUser.getProfileImageFileId() > 0)
				loggedInUser.setProfilePicture(fileHandler.getFile(loggedInUser.getProfileImageFileId()));
		*/	
			return loggedInUser;
		} else {
			throw new BusinessException(ExceptionCodes.USER_NOT_FOUND, ExceptionMessages.USER_NOT_FOUND);
		}
		}
		else
		{
			throw new BusinessException(ExceptionCodes.USER_NOT_LOGGED_IN, ExceptionMessages.USER_NOT_LOGGED_IN);
		}
	}

	public Map<Long, User> userMap() {
		Map<Long, User> userMap = new HashMap<Long, User>();
		List<User> userList = userDAO.userList();
		if (CollectionUtils.isNotEmpty(userList)) {
			for (User user : userList) {
				userMap.put(user.getId(), user);
			}
		}
		return userMap;
	}

	
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}
	
	public boolean findByConfirmationToken(String confirmationToken) {
		return userDAO.findByConfirmationToken(confirmationToken);
	}
	
	public boolean findByVerificationCode(String email,String verificationCode) {
		return userDAO.findByVerificationCode(email,verificationCode);
	}
	
	public boolean findByPasswordResetCode(String email,String verificationCode) {
		return userDAO.findByPasswordResetCode(email,verificationCode);
	}

	public String[] getUsersEmails(Collection<Long> userIdList) {

		List<String> emailList = userDAO.getUsersEmail(userIdList);
		String[] emailArray = new String[emailList.size()];
		if (emailList.size() > 0 || emailList != null) {
			for (int i = 0; i < emailList.size(); i++) {
				emailArray[i] = emailList.get(i);
			}
		}
		return emailArray;
	}

	public boolean sendForgotPasswordLink(String email, HttpServletRequest request, HttpServletResponse response)
			throws BusinessException, MessagingException {
		// TODO Auto-generated method stub

		if (isDuplicateUserEmail(email)) {
			/*
			 * if(enableEmail){ User user=getUserByEmail(email); final long
			 * userIdForMail=user.getId(); final String
			 * usernameForMail=user.getName(); final String
			 * emailForMail=user.getEmail(); new Thread(new Runnable() {
			 * 
			 * @Override public void run() { try {
			 * triggerNotification.sendResetLink(userIdForMail,emailForMail,
			 * usernameForMail); } catch (BusinessException | MessagingException
			 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
			 * }).start(); return true;
			 * 
			 * }else throw new
			 * BusinessException(ExceptionCodes.EMAIL_SERVICE_DISABLED,
			 * ExceptionMessages.EMAIL_SERVICE_DISABLED);
			 */

		} else
			throw new BusinessException(ExceptionCodes.USER_EMAIL_NOT_REGISTERED,
					ExceptionMessages.USER_EMAIL_NOT_REGISTERED);

		return true;
	}

	public void addAdmin(String email, int roleType) throws BusinessException {
		if (isDuplicateUserEmail(email)) {
			User user = getUserByEmail(email);
			Role role = new Role();
			role.setUserId(user.getId());
			role.setType(roleType);

			addRole(role);
			/*if (user != null) {
				user.setRole(roleType);
			}*/
		} else {
			throw new BusinessException(ExceptionCodes.INVALID_EMAIL, ExceptionMessages.INVALID_EMAIL);
		}
	}

	public CookieToken authenticate(User user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UUID token = null;
		
		System.out.println("authentication first step ::"+user.getPassword()+"--"+user.getEmail());
		
		

		// Raju Nune Need to cross check user is already logged in and available
		// in memcache

		UserAuthentication ud = (UserAuthentication) loadUserByUsername(user.getEmail());
		if (ud.getStatus() == User.STATUS_INACTIVE) {
			throw new BusinessException(ExceptionCodes.ACCOUNT_INACTIVE, ExceptionMessages.ACCOUNT_INACTIVE);
		}
		// else if(ud.getStatus() == User.STATUS_NOT_VERIFIED){
		// throw new
		// BusinessException(ExceptionCodes.ACCOUNT_NOT_VERIFIED,ExceptionMessages.ACCOUNT_NOT_VERIFIED);
		// }
		if (ud.getUserId() == 0) {
			throw new BusinessException(ExceptionCodes.USERNAME_OR_EMAIL_IS_INVALID,
					ExceptionMessages.USERNAME_OR_EMAIL_IS_INVALID);
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 

			System.out.println("Password in DataBase ::"+ud.getCredentials().toString() +" encoded password submitted ::"+encodeUserPassword(user.getPassword()));
			if (ud.getCredentials() != null
					&& !encoder.matches( user.getPassword(),ud.getCredentials().toString())) {
				throw new BusinessException(ExceptionCodes.PASSWORD_IS_INVALID, ExceptionMessages.PASSWORD_IS_INVALID);
			} else {
				if (!ud.getAuthorities().isEmpty()
						&& !ud.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ROLE_ANONYMOUS))) {
					token = UUID.randomUUID();
					if (ud != null
							&& !ud.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ROLE_ANONYMOUS))) {
						// Raju Nune - need to call
						// MemCachedSessionSecurityContextRespository,
						// saveContext
						// method here instead of directly saving
						cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).put(token.toString(), ud);
						SecurityContextHolder.getContext().setAuthentication(ud);
						// memCachedSessionSecurityContextRespository.saveContext(SecurityContextHolder.getContext(),
						// request, response);
						// response.addCookie(new
						// Cookie(Constants.OMICS_SESSION, token.toString()));
						cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).put(token.toString(), ud);

					}
				}
			}
		}

		if (token != null)
			return new CookieToken(token.toString(), utils.getLoggedInUserId());
		else
			return new CookieToken();
	}

	public boolean isUserLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		// // TODO Auto-generated method stub
		//
		//
		// if(utils.getLoggedInUserId() > 0)
		// return true;
		//
		// return true;

		// TODO Auto-generated method stub
		boolean flag = false;
		Cookie cookie[] = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				Cookie cookiee = cookie[i];
				if (cookiee.getName().equalsIgnoreCase(Constants.DDEBBIE_SESSION)) {
					Cookie requestCookie = cookie[i];
					if (cacheManager.getCache(com.ddebbie.cache.CacheRegionType.USER_SESSION_CACHE)
							.getValue(requestCookie.getValue()) != null) {
						flag = true;

					} else {
						flag = false;
					}
				} else {
					/*
					 * flag= false;
					 */}
			}

		} else {
			flag = false;
		}

		return flag;

	}
	
	public boolean logout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		Cookie cookie[] = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				Cookie cookiee = cookie[i];
				if (cookiee.getName().equalsIgnoreCase(Constants.DDEBBIE_SESSION)) {
					Cookie requestCookie = cookie[i];
					if (cacheManager.getCache(com.ddebbie.cache.CacheRegionType.USER_SESSION_CACHE)
							.getValue(requestCookie.getValue()) != null) {
						cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).remove(requestCookie.getValue());

					} else {
					}
				} else {
				}
			}

		} else {
		}

		SecurityContextHolder.clearContext();
		return true;

	}

	

	public Role getRoleByAdminType(long userId, int adminType) throws BusinessException {
		Role role = new Role();
		try {
			if (userId > 0)
				role = (Role) roleDAO.getUserRoleByAdminType(userId, adminType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessException(ExceptionCodes.ROLE_NOT_FOUND, ExceptionMessages.ROLE_NOT_FOUND);
		}
		return role;
	}
	
	public String encodeUserPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


	public String resetPasswordRequest(String email) throws BusinessException {
		String verificationCode="";
		if (email == null) {
			throw new BusinessException(ExceptionCodes.USER_EMAIL_MANDATORY, ExceptionMessages.USER_EMAIL_MANDATORY);
		} else {
			User user = userDAO.getUserByEmail(email);
			if(user!=null)
			{
				Random rand = new Random();
		        Integer code = rand.nextInt(MIN_VERIFICATION_CODE
		                - MAX_VERIFICATION_CODE + 1) + MAX_VERIFICATION_CODE;
		         verificationCode=code.toString();
		         user.setResetToken(verificationCode);
		         save(user);
		         

					SimpleMailMessage resetPasswordEmail = new SimpleMailMessage();
					resetPasswordEmail.setTo("arvapallishiva@gmail.com");
					resetPasswordEmail.setCc("ddebbiedelivery@gmail.com");
					
					resetPasswordEmail.setSubject("Password Reset Request - Verification Code");
					//String appUrl = request.getScheme() + "://" + request.getServerName()+":8080/DDEBBIECourityAPI-0.0.1-SNAPSHOT";

					resetPasswordEmail.setText("To reset the password, please enter the verification code:\n"
							+ verificationCode);
					resetPasswordEmail.setFrom("ddebbiedelivery@gmail.com");
					System.out.println("verificationCode:"+verificationCode);
					emailService.sendEmail(resetPasswordEmail);
			}
			else
			{
				throw new BusinessException(ExceptionCodes.USER_NOT_FOUND, ExceptionMessages.USER_NOT_FOUND);
			}
		}
		return verificationCode;
	}



	
}