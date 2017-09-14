package com.ddebbie.dao;

import java.util.List;

import com.ddebbie.model.Role;
import com.ddebbie.pagination.Paginator;

/**
 * @author Ram
 * 13-Sep-2017
 */
public interface RoleDAO extends BaseDAO {
	
	//Return paginated list of Roles for an user
	public Paginator<Role> getUsersRoleList(long userId, int pageNumber, int pageSize);

	public List<Role> getLoggedInUserRole(long loggedInUserId);
	public Role  getUserRole(long userId) ;
	public Role  getUserRoleByAdminType(long userId,int adminType) ;
}
