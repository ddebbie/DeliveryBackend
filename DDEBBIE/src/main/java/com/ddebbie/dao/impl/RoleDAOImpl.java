package com.ddebbie.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ddebbie.dao.AbstractDao;
import com.ddebbie.dao.RoleDAO;
import com.ddebbie.model.Role;
import com.ddebbie.pagination.Paginator;

/**
 * @author Ram
 * 13-Sep-2017
 */
@Repository("RoleDAO")
@Transactional
public class RoleDAOImpl extends AbstractDao implements RoleDAO{

	@Override
	public Paginator<Role> getUsersRoleList(long userId, int pageNumber, int pageSize) {
		int skipCount = (pageNumber - 1) * pageSize;
		Criteria criteria = createCriteria(Role.class);
		criteria.add(Restrictions.and(Restrictions.eq(
				Role.LABEL_USER_ID, userId), Restrictions.eq(
						Role.LABEL_IS_DELETED,
						Role.IS_DELETED_FALSE)));
		
		criteria.setProjection(Projections.rowCount());
        long total = (Long) criteria.uniqueResult();
        criteria.setProjection(null);  
		criteria.setFirstResult(skipCount).setMaxResults(pageSize);
		return new Paginator<Role>((List<Role>) criteria.list(), total);
	}

	

	
	
	
	/*@Override
	public boolean isRoleExist(Role role) {
		Criteria criteria = createCustomCriteria(Role.class);
		criteria.add(Restrictions.eq(Role.LABEL_USER_ID, role.getUserId()));
		criteria.add(Restrictions.eq(Role.LABEL_ADMIN_TYPE, role.getAdminType()));
		if(role.getReferenceId() > 0)
			criteria.add(Restrictions.eq(Role.LABEL_REFERENCE_ID, role.getReferenceId()));
		if(role.getId() > 0){
			criteria.add(Restrictions.ne(Role.LABEL_ID, role.getId()));
		}
		long count= getTotalCount(criteria);
		if(count==0)
		{
			return false;
		}

		return true;
	}*/

	@Override
	public List<Role> getLoggedInUserRole(long loggedInUserId) {
		Criteria criteria = createCustomCriteria(Role.class);
		criteria.add(Restrictions.eq(Role.LABEL_USER_ID, loggedInUserId));
		return criteria.list();
	}
	@Override
	public Role  getUserRole(long userId) {
		Criteria criteria = createCustomCriteria(Role.class);
		criteria.add(Restrictions.eq(Role.LABEL_USER_ID, userId));
		List list=criteria.list();
		Role role=null;
		if(list!=null){
			role=(Role) list.get(0);
		}
		return role;
	}
	@Override
	public Role getUserRoleByAdminType(long userId, int adminType) {
		Criteria criteria = createCustomCriteria(Role.class);
		criteria.add(Restrictions.eq(Role.LABEL_USER_ID, userId));
		criteria.add(Restrictions.eq(Role.LABEL_ADMIN_TYPE, adminType));
		List list=criteria.list();
		Role role=null;
		if(list!=null){
			role=(Role) list.get(0);
		}
		return role;
	}






	
}
