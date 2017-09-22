package com.ddebbie.exception;

public interface ExceptionMessages {

	/**
	 * Login exception messages
	 */
	public static final String USERNAME_OR_EMAIL_IS_INVALID = "Entered username or email is invalid.";
	public static final String PASSWORD_IS_INVALID = "Entered password is invalid.";
	public static final String ACCOUNT_INACTIVE = "Your account is inactive. Please contact Omics Team for activating your account.";
	public static final String ACCOUNT_NOT_VERIFIED = "Your account is not verified. Please verify your account.";

	public static final String CACHE_REGION_NOT_FOUND = "Cache region not found";

	public static final String NO_RESULTS_FOUND = "No results found";

	public static final String UPDATE_FAILED = "Unable to update the record";

	public static final String INSERTION_FAILED = "Unable to add the record";

	public static final String EMAIL_VALIDATION = "Please enter a valid email";

	public static final String RECAPTCHA_VALIDATION_REQUIRED = "Captcha validation is required";

	public static final String DELETE_FAILED = "Unable to delete the record";

	public static final String IMAGE_ID_CANNOT_BE_EMPTY = "Invalid image id";

	// unable to save record to db
	public static final String SAVE_FAILED = "unable to save record ";

	public static final String OBJECT_NOT_FOUND = "Data not found";

	public static final String USER_USERNAME_MANDATORY = "Username is mandatory";

	public static final String USER_PASSWORD_MANDATORY = "Password is mandatory";
	public static final String USER_PASSWORD_NOT_MATCHING = "Your current password is incorrect";

	public static final String USER_EMAIL_MANDATORY = "Email is mandatory";

	public static final String USER_USERNAME_EXISTS = "Username already exist";

	public static final String FILE_TYPE_NOT_ALLOWED = "Invalid file type";

	public static final String USER_NOT_FOUND = "User does not exist";

	public static final String FILE_DOES_NOT_EXIST = "File does not exist";
	public static final String FILE_SIZE_EXCEEDED_MAXIMUM = "Uploaded file size has exceeded the limits. Please upload a file of smaller size";

	public static final String MANDATORY_FIELD = "Please fill the Mandatory fields";
	public static final String TEMPLATE_ALREADY_EXISTS = "Template already exists";

	public static final String ROLE_NOT_FOUND = "Invalid user for University Admin";
	public static final String USER_DOES_NOT_EXIST = "User does not exist";

	public static final String ROLE_EXISTS = "User already exists for this role";

	public static final String PASSWORD_CURRENTPASSWORD_MANDATORY = "Current Password is mandatory";

	public static final String PASSWORD_NEWPASSWORD_MANDATORY = "New Password is mandatory";

	public static final String PASSWORD_RETYPEPASSWORD_MANDATORY = "Retype Password is mandatory";

	public static final String USER_NOT_LOGGED_IN = "Please login to continue";

	public static final String CONFERENCE_ABSTRACT_TRACK_ORDER_EXISTS = "Order is already associated with other Abstract Track";

	public static final String CONFERENCE_ABSTRACT_TITLE_MANDATORY = "Title is mandatory";

	public static final String CONFERENCE_ABSTRACT_FIRSTNAME_MANDATORY = "The first name is mandatory";

	public static final String CONFERENCE_ABSTRACT_EMAIL_MANDATORY = "The email is mandatory";

	public static final String CONFERENCE_ABSTRACT_PHONE_MANDATORY = "The phone is mandatory";

	public static final String CONFERENCE_ABSTRACT_CATEGORY_INVALID = "The category is invalid";

	public static final String CONFERENCE_ABSTRACT_POSTALADDRESS_MANDATORY = "The postal address is mandatory";

	public static final String FILE_MANDATORY = "The file is mandatory";

	public static final String CONFERENCE_ABSTRACT_SUBMISSION_DISABLED = "The abstract submission has been disabled";

	public static final String INVALID_EMAIL = "The email is invalid";

	public static final String USER_SPEAKER_MAPPING_EXISTS = "User is already a speaker";

	public static final String CONFERENCE_TEMPLATE_NAME_EXIST = "Conference Template Name already exist";

	public static final String USER_NAME_MANDATORY = "Username mandatory";
	public static final String VALIDATION_FAILED = "Invalid captcha";
	public static final String RETYPE_PASSWORD_NOT_MATCHING = "Retype password not matching";
	public static final String USER_EMAIL_EXISTS = "User email exists";
	public static final String EMAIL_SERVICE_DISABLED = "Email service disabled";
	public static final String USER_EMAIL_NOT_REGISTERED = "User email not registered";
	
	//Registration
	public static final String VERIFICATION_CODE_INVALID="Invalid Code Entered";
	
}
