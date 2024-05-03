package com.askme.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EM_ROLE")
public class RoleDTO extends BaseDTO {

	public static final int ADMIN = 1;
	public static final int STUDENT = 2;
	public static final int COLLEGE = 3;

	@Column(name = "ROLE_NAME", length = 50)
	private String roleName;

	@Column(name = "ROLE_DESCRIPTION", length = 70)
	private String roleDescription;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getKey() {
		return String.valueOf(id);
	}

	public String getValue() {
		return roleName;
	}

}
