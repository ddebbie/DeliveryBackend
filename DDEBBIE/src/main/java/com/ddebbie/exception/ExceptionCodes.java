package com.ddebbie.exception;

public interface ExceptionCodes {

	/**
	 * Login exception codes
	 */
	public static final int USERNAME_OR_EMAIL_IS_INVALID = 1;
	public static final int PASSWORD_IS_INVALID = 2;
	public static final int ACCOUNT_INACTIVE = 3;
	public static final int ACCOUNT_NOT_VERIFIED = 4;

	public static final int CACHE_REGION_NOT_FOUND = 2000000;
	public static final int CACHE_NAME_NOT_PROVIDED = 2000001;
	public static final int ENCRYPTION_FAILED = 2000002;

	// General Exceptions
	public static final int NO_RESULTS_FOUND = 100;
	public static final int UPDATE_FAILED = 101;
	public static final int INSERTION_FALIED = 102;
	public static final int DELETE_FAILED = 103;
	public static final int OBJECT_NOT_FOUND = 104;
	public static final int SAVE_FAILED = 105;
	public static final int GET_DETAILS_FAILED = 106;
	public static final int USER_UNAUTHORIZED_FOR_ACTION = 107;
	public static final int VALIDATION_FAILED = 108;
	// unkown
	public static final int INPUT_STRING_DOES_NOT_MATCH = 112;
	public static final int PASSWORD_CURRENTPASSWORD_MANDATORY = 113;
	public static final int PASSWORD_NEWPASSWORD_MANDATORY = 114;
	public static final int PASSWORD_RETYPEPASSWORD_MANDATORY = 115;
	public static final int RETYPE_PASSWORD_NOT_MATCHING = 116;
	public static final int EMAIL_VALIDATION = 118;
	public static final int USER_NOT_LOGGED_IN = 117;
	public static final int INVALID_REQUEST = 119;

	// Conference Banner related Exception starts with 300
	public static final int INVALID_IMAGE_ID = 301;

	// public static final int MANDATORY_FIELD = 3002;// nO refs
	public static final int EXTERNAL_REGISTRATION_LINK = 304;
	public static final int TERMS_AND_CONDITION = 305;
	public static final int EMAIL_MESSAGE_CONTENT = 306;
	public static final int INVALID_EMAIL = 307;
	// Unkown-END

	// File Related Exception - 3000
	public static final int FILE_TYPE_NOT_ALLOWED = 3000;
	public static final int FILE_DOES_NOT_EXIST = 3001;
	public static final int FILE_UPLOAD_FAILED = 3002;
	public static final int FILE_SIZE_EXCEEDED_MAXIMUM = 3003;
	public static final int FILE_DOWNLOAD_FAILED = 3004;
	public static final int FILE_MANDATORY = 3005;
	// All Banner Exceptions-6000
	
	//Registration Verification Code
	
	public static final int VERIFICATION_CODE_INVALID=400;


	/**
	 * All General Exceptions-1000
	 */
	//

	// Mandatory Exceptions-1100

	// Failed Exceptions-1200

	// Invalid Input Exceptions-1300

	// Mapping Exceptions-1400

	// NotFound Exceptions

	// Failed Exceptions-15200

	// Invalid Input Exceptions-15300

	// Mapping Exceptions-15400

	// NotFound Exceptions

	// All Venue Exceptions-5000

	// Mandatory Exceptions-5100

	// Failed Exceptions-5200

	// Invalid Input Exceptions-5300

	;

	// All User Exceptions-4000
	public static final int USER_EXISTS = 4000;
	public static final int ROLE_EXISTS = 4001;
	public static final int VERIFICATION_LINK_EXISTS = 4002;
	public static final int USER_USERNAME_EXISTS = 4003;
	public static final int DISCOUNT_CODE_EXISTS = 4004;
	public static final int ROLE_NOT_EXISTS = 4005;
	public static final int USER_EMAIL_EXISTS = 4006;
	public static final int SPEAKER_CANNOT_BE_DEMOTED_TO_USER = 4007;
	public static final int USER_REMINDER_ALREADY_EXISTED = 4008;

	// Not found Exceptions-4100
	public static final int USER_NOT_FOUND = 4100;
	public static final int ROLE_NOT_FOUND = 4101;

	// User Mandatory Exceptions-4200
	public static final int USER_ID_MANDATORY = 4200;

	public static final int USER_DOES_NOT_EXIST = 4302;

	// all email related exceptions
	public static final int EMAIL_TEMPLATE_NOT_FOUND = 14500;

	public static final int INVALID_CONTENT_PAGE = 10138;
	public static final int USER_USERNAME_MANDATORY = 0;
	public static final int USER_NAME_MANDATORY = 0;
	public static final int USER_PASSWORD_NOT_MATCHING = 0;
	public static final int USER_PASSWORD_MANDATORY = 0;
	public static final int USER_EMAIL_MANDATORY = 0;
	public static final int EMAIL_SERVICE_DISABLED = 0;
	public static final int USER_EMAIL_NOT_REGISTERED = 0;

}
