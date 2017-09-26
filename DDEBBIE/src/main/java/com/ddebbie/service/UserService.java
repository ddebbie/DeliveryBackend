package com.ddebbie.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ddebbie.exception.BusinessException;
import com.ddebbie.exception.ExceptionCodes;
import com.ddebbie.exception.ExceptionMessages;
import com.ddebbie.handler.UserHandler;
import com.ddebbie.model.CookieToken;
import com.ddebbie.model.Role;
import com.ddebbie.model.User;
import com.ddebbie.model.input.ChangeUserPassword;
import com.ddebbie.service.common.GenericResponse;
import com.ddebbie.service.descriptors.ResetPassword;
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
	
	 @Autowired
	 private Environment environment;

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
	@RequestMapping(value = "reset-password-change", method = RequestMethod.POST)
	public CookieToken resetPassword(@RequestBody ResetPassword resetPassword) throws BusinessException {
		return userHandler.resetPassword(resetPassword);
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
	
	
	@RequestMapping(value = "profileupload", method = RequestMethod.POST)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String fileName = formatter.format(Calendar.getInstance().getTime()) + "_thumbnail.jpg";
      
        if (!file.isEmpty()) {
            try {
            	String existingBucketName = "ddebbie";
            	  String keyName = "mypic.JPG";
            	  
            	 
            	  String amazonFileUploadLocationOriginal=existingBucketName+"/";
            	  

            	  AmazonS3 s3Client = new AmazonS3Client(new PropertiesCredentials(UserService.class.getResourceAsStream("AwsCredentials.properties")));
            	  
            	  //FileInputStream stream = new FileInputStream(filePath);
            	  byte[] bytes = file.getBytes();

                  ByteArrayInputStream imageInputStream = new ByteArrayInputStream(bytes);

            	  ObjectMetadata objectMetadata = new ObjectMetadata();
            	  PutObjectRequest putObjectRequest = new PutObjectRequest(amazonFileUploadLocationOriginal, keyName, imageInputStream, objectMetadata);
            	  PutObjectResult result = s3Client.putObject(putObjectRequest);
            	  System.out.println("Etag:" + result.getETag() + "-->" + result);

                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

	@RequestMapping(value="/user/profile-picture", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] profilePicture() throws IOException {
        User u; //= userService.getLoggedInUser();
        String profilePicture=""; //= userRoot + File.separator + u.getId() + File.separator + u.getProfilePicture();
        if(new File(profilePicture).exists()) {
            return IOUtils.toByteArray(new FileInputStream(profilePicture));
        } else {
            return null;
        }
    }
}
