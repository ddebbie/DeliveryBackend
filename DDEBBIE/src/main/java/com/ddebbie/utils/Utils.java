package com.ddebbie.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ddebbie.cache.CacheManager;
import com.ddebbie.cache.CacheRegionType;
import com.ddebbie.dao.UserDAO;
import com.ddebbie.model.User;
 
 /**
 * @author Ram
 * 13-Sep-2017
 */
@Component
public final class Utils {
	
	@Autowired
	UserDAO userDAO;
	

	@Autowired
	CacheManager cacheManager;
	
	@Value("${runningEnvironment}") 
	private String runningEnvironment;
	
    

	//@Autowired
	private static SimpleDateFormat dateFormatGmt= new SimpleDateFormat();
	
	private static long userId;

	public static Timestamp getGMTCurrentTime() {

		dateFormatGmt.applyPattern(Constants.DATE_FORMAT);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
		return Timestamp.valueOf(dateFormatGmt.format(System
				.currentTimeMillis()));
		// TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		// //;
		// return new
		// Timestamp(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis());

	}
	
	
	// If the current date is equals to the conferenceEndDate the value of
	// the ckeckdate will be 0
	// If Current date is smaller than conferenceEndDate the value of
	// checkdate will be -1
	// If Current date is bigger than conferenceEndDate the value of
	// checkdate will be 1
	public static int compareWithCurrentDate(Date date) {
		return date.compareTo(new Date());
	}
	
	public static String getYearFromDate( Date date) {
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		return formatYear.format(date);
	}
	
	 public static void assignNull(Object... objects) {
        for (Object object : objects) {
            object = null;
        }
    }
	 
	public static Timestamp convertToGivenTimezone(Timestamp givenTimestamp,
			String timezoneDiffFromGMT) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(givenTimestamp);
		String time = timezoneDiffFromGMT;
		String hrs = null;
		String min = null;
		String[] hrsnmin = time.split("\\.");
		if (hrsnmin.length > 0) {
			if (hrsnmin.length == 2) {
				hrs = hrsnmin[0];
				min = hrsnmin[1];
			} else
				hrs = hrsnmin[0];
		}
		cal.add(Calendar.HOUR, Integer.valueOf(hrs));
		cal.add(Calendar.MINUTE, Integer.valueOf(min));
		Date date = cal.getTime();
		return new Timestamp(date.getTime());
	}
	
	public static void setUserId(long id) {
		userId = id;
	}
	
	public long getUserId() {
		return userId;
	}

    public  boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
 
    public long getLoggedInUserId() {
        long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     
        if (null != authentication && !(authentication instanceof AnonymousAuthenticationToken)) {
        	 User user= userDAO.getUserByEmail((String) authentication.getDetails());
        	 if(user==null)
        	 {
        		 user=userDAO.getUserByUserName((String) authentication.getPrincipal());
        	 }
        	 if(user!=null)
        	 {
        		 userId = user.getId();
        	 }
        }
        return userId;
    }

    public User getLoggedInUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();        
        if (null != authentication && !(authentication instanceof AnonymousAuthenticationToken)) {
        	user = userDAO.getUserByEmail((String) authentication.getDetails());
        }
        return user;
    }
    
	public boolean isUserAuthenticated(String token)
	{
		System.out.println("checking for token authentication::"+token);
		if(cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).getValue(token)!=null)
		{
			Authentication userAuthentication = (Authentication) (cacheManager.getCache(CacheRegionType.USER_SESSION_CACHE).getValue(token));
			if(userAuthentication!=null)
			{
				System.out.println("User received  :: "+userAuthentication.getPrincipal().toString()+" :: "+userAuthentication.getName().toString());
			}
			return true;
		}
			
		return false;
	}
    
    public String getMd5(String inputString)
    {
    return DigestUtils.md5Hex(inputString);
    }
    
    public int getCurrentYear(){
    	return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    public String constructValidKeyword(String name){
    	String trimmedName = name.toLowerCase().trim();
    	String removeSpecialChar = trimmedName.replaceAll("[\t\r\n()?:!.,;{}#%^&*@=+']+", " ");
    	String afterRemovingSpaces = removeSpecialChar.replaceAll("[ ]+", "-");
    	return afterRemovingSpaces;
    }
    
    public String constructValidUniversityKeyword(String name){
    	String trimmedName = name.toLowerCase().trim();
    	String removeSpecialChar = trimmedName.replaceAll("[()?:!.,;{}#%^&*@=+']+", " ");
    	String afterRemovingBR=removeSpecialChar.replaceAll("(?i)<BR>", "-").replaceAll("(?i)</BR>", "-");
    	String afterRemovingSpaces = afterRemovingBR.replaceAll("[ ]+", "-");    	
    	return afterRemovingSpaces;
    }
    
    public int getYearFromTimestamp(Timestamp timestamp){
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timestamp.getTime());
    	return cal.get(Calendar.YEAR);
    }
    
   
   
    public String getCurrentMonth(){
    	String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

            Calendar cal = Calendar.getInstance();
         return monthName[cal.get(Calendar.MONTH)];
    }
    
    public String getBaseUrlWithoutSlash(String baseUrl){
    	String baseUrlWithoutSlash = baseUrl.substring(0, baseUrl.length()-1);
    	return baseUrlWithoutSlash;
    }
    public String removeTag(String text){
		if(text!=null){
			return text.replaceAll("\\<.*?\\>", "");
		}
		return text;
	}
    
    
    public boolean isValidUrl(String url) {

        String[] schemes = {"http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (urlValidator.isValid(url)) {
//            System.out.println("url is valid");
            return true;
        } else {
//            System.out.println("url is invalid");
            return false;
        }
       
    }
    
    
    //Current timestamp without hours and minutes
    
    public Timestamp getCurrentTimeStamp(){
    	Calendar last = Calendar.getInstance();   // this takes current date
		// Set time fields to zero
		last.set(Calendar.HOUR_OF_DAY, 0);
		last.set(Calendar.MINUTE, 0);
		last.set(Calendar.SECOND, 0);
		last.set(Calendar.MILLISECOND, 0);
	    
	return new Timestamp(last.getTime().getTime()); 
    }
}
