package extra.dao;

import extra.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
