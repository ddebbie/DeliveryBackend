package com.ddebbie.dao;

import java.util.Collection;
import java.util.List;

import com.ddebbie.model.User;
import com.ddebbie.pagination.Paginator;

/**
 * @author Ram
 * 
 */

public interface UserDAO extends BaseDAO {

	
	/**
	 * @Remark This will return User information  for the required email
	 */
	public User getUserByEmail(String email);

	

	
	/**
	 * @Remark This will return User information for the required User Name
	 */
	public User getUserByUserName(String userName);

	/**
	 * @Remark This will return User for information the required type(user/speaker)
	 */
	public Paginator<User> getUsersByType(int type, int pageSize,
			int pageNumber);

	User getUserByName(String userName);
	
	List<User> getUsers(Collection<Long> userIds);

	boolean isDuplicateUserName(User user);

	boolean isDuplicateEmail(User user);

	public Paginator<User> matchUsers(String searchString,
			int pageNumber, int pageSize);


	public List<User> getUsersByEmail(Collection<String> speakerEmails);

	public boolean isDuplicateUserName(String userName);
	
	boolean isDuplicateEmail(String email);


	
	public User signInEmailUser(String username, String password);

	public List<User> userList();

	public  List<String> getUsersEmail(Collection<Long> userIdList);
	
	

	
	
	public int getTotalUser();
	
	public Paginator<User> getPaginatedUser(int pageNumber,int pageSize,long fromId,long toId);




	public boolean findByConfirmationToken(String confirmationToken);
	
	public boolean findByVerificationCode(String email,String verificationCode);
	
	
	
	
}