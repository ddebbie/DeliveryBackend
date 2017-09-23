package com.ddebbie.dao.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ddebbie.dao.AbstractDao;
import com.ddebbie.dao.UserDAO;
import com.ddebbie.model.AbstractObject;
import com.ddebbie.model.User;
import com.ddebbie.pagination.Paginator;

/**
 * @author Ram
 * 
 */
@SuppressWarnings("unchecked")
@Repository("userDAO")
@Transactional
public class UserDAOImpl extends AbstractDao implements UserDAO {

	public User getUserByEmail(String email) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.eq(User.LABEL_EMAIL, email));
		return (User) criteria.uniqueResult();

	}

	public User getUserByUserName(String userName) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.eq(User.LABEL_NAME, userName));
		return (User) criteria.uniqueResult();

	}

	public Paginator<User> getUsersByType(int type, int pageSize, int pageNumber) {
		int skipCount = (pageNumber - 1) * pageSize;

		Criteria criteria = createCustomCriteria(User.class);
		// criteria.add(Restrictions.eq(User.LABEL_TYPE, type));
		criteria.setProjection(Projections.rowCount());
		long total = (Long) criteria.uniqueResult();
		criteria.setProjection(null);
		criteria.setFirstResult(skipCount).setMaxResults(pageSize);
		return new Paginator<User>((List<User>) criteria.list(), total);
	}

	@Override
	public User getUserByName(String userName) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.eq(User.LABEL_NAME, userName));
		List list = criteria.list();
		if (CollectionUtils.isEmpty(list))
			return null;
		return (User) list.get(0);
	}

	@Override
	public boolean isDuplicateUserName(User user) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_NAME, user.getName()),
				Restrictions.isNotNull(User.LABEL_PASSWORD)));
		if (user.getId() != 0)
			criteria.add(Restrictions.ne(User.LABEL_ID, user.getId()));
		criteria.setProjection(Projections.rowCount());
		long total = (Long) criteria.uniqueResult();
		return total > 0;
	}

	@Override
	public boolean isDuplicateEmail(User user) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_EMAIL, user.getEmail()),
				Restrictions.isNotNull(User.LABEL_PASSWORD)));
		if (user.getId() != 0)
			criteria.add(Restrictions.ne(User.LABEL_ID, user.getId()));
		criteria.setProjection(Projections.rowCount());
		long total = (Long) criteria.uniqueResult();
		return total > 0;
	}

	@Override
	public Paginator<User> matchUsers(String searchString, int pageNumber,
			int pageSize) {
		int skipCount = (pageNumber - 1) * pageSize;
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.and(Restrictions.ilike(User.LABEL_EMAIL,
				searchString, MatchMode.ANYWHERE), Restrictions.eq(
				User.LABEL_IS_DELETED, User.IS_DELETED_FALSE)));
		criteria.addOrder(Order.asc(User.LABEL_EMAIL));
		criteria.setProjection(Projections.rowCount());
		long total = (Long) criteria.uniqueResult();
		criteria.setProjection(null);
		criteria.setFirstResult(skipCount).setMaxResults(pageSize);
		return new Paginator<User>((List<User>) criteria.list(), total);
	}

	@Override
	public List<User> getUsersByEmail(Collection<String> speakerEmails) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.in(User.LABEL_EMAIL, speakerEmails));
		return criteria.list();
	}

	@Override
	public boolean isDuplicateUserName(String userName) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.eq(User.LABEL_NAME, userName));
		criteria.setProjection(Projections.rowCount());
		long total = (Long) criteria.uniqueResult();
		return total > 0;

	}

	@Override
	public boolean isDuplicateEmail(String email) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.eq(User.LABEL_EMAIL, email));
		criteria.setProjection(Projections.rowCount());
		long total = (Long) criteria.uniqueResult();
		return total > 0;
	}

	@Override
	public List<User> userList() {
		Criteria criteria = createCustomCriteria(User.class);
		return criteria.list();
	}

	@Override
	public List<String> getUsersEmail(Collection<Long> userIdList) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.in(User.LABEL_ID, userIdList));
		criteria.setProjection(Projections.property(User.LABEL_EMAIL));
		return criteria.list();
	}

	@Override
	public int getTotalUser() {
		// TODO Auto-generated method stub
		Criteria criteria = createCustomCriteria(User.class);
		return (int) getTotalCount(criteria);
	}

	@Override
	public Paginator<User> getPaginatedUser(int pageNumber, int pageSize,
			long fromId, long toId) {

		int skipCount = (pageNumber - 1) * pageSize;
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.ge(AbstractObject.LABEL_ID, fromId));
		criteria.add(Restrictions.le(AbstractObject.LABEL_ID, toId));
		long total = (Long) getTotalCount(criteria);
		criteria.setFirstResult(skipCount).setMaxResults(pageSize);
		return new Paginator<User>((List<User>) criteria.list(), total);

	}

	@Override
	public List<User> getUsers(Collection<Long> userIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User signInEmailUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findByConfirmationToken(String confirmationToken) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_SIGNUPTOKEN, confirmationToken)));
		//criteria.setProjection(Projections.*(User.LABEL_SIGNUPTOKEN));
		List list = criteria.list();
		if (CollectionUtils.isEmpty(list))
			return false;
		else
		{
			User usertoken=(User) list.get(0);
			usertoken.setMts(new Timestamp(new Date().getTime()));
			usertoken.setActive(true);
			usertoken.setModifierId(1248);
			usertoken = (User) this.persist(usertoken);
		}
		return true;
		
	}
	
	@Override
	public boolean findByVerificationCode(String email,String verificationCode) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_VERIFICATIONCODE, verificationCode)));
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_EMAIL, email)));
		//criteria.setProjection(Projections.*(User.LABEL_SIGNUPTOKEN));
		List list = criteria.list();
		if (CollectionUtils.isEmpty(list))
			return false;
		else
		{
			User usertoken=(User) list.get(0);
			usertoken.setMts(new Timestamp(new Date().getTime()));
			usertoken.setActive(true);
			usertoken.setModifierId(1248);
			usertoken = (User) this.persist(usertoken);
		}
		return true;
		
	}
	
	@Override
	public boolean findByPasswordResetCode(String email,String verificationCode) {
		Criteria criteria = createCustomCriteria(User.class);
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_RESETTOKEN, verificationCode)));
		criteria.add(Restrictions.and(
				Restrictions.eq(User.LABEL_EMAIL, email)));
		//criteria.setProjection(Projections.*(User.LABEL_SIGNUPTOKEN));
		List list = criteria.list();
		if (CollectionUtils.isEmpty(list))
			return false;
		else
		{
//			User usertoken=(User) list.get(0);
//			usertoken.setMts(new Timestamp(new Date().getTime()));
//			usertoken.setActive(true);
//			usertoken.setModifierId(1248);
//			usertoken = (User) this.persist(usertoken);
		}
		return true;
		
	}

}
