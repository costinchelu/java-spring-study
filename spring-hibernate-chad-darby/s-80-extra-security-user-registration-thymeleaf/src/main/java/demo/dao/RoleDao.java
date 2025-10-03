package demo.dao;

import demo.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
