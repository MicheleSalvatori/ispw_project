package logic.bean;

import logic.utilities.Role;

public class UserBean {

	private static UserBean userInstance = null;
	private Role ubRole;

	private String ubUsername;
	private String ubPassword;
	private String ubName;
	private String ubSurname;
	private String ubEmail;

	public static UserBean getInstance() {
		return UserBean.userInstance;
	}

	public static void setInstance(UserBean userBean) {
		UserBean.userInstance = userBean;
	}

	public Role getRole() {
		return ubRole;
	}

	public void setRole(Role role) {
		this.ubRole = role;
	}

	public String getUsername() {
		return ubUsername;
	}

	public void setUsername(String username) {
		this.ubUsername = username;
	}

	public String getPassword() {
		return ubPassword;
	}

	public void setPassword(String password) {
		this.ubPassword = password;
	}

	public String getName() {
		return ubName;
	}

	public void setName(String name) {
		this.ubName = name;
	}

	public String getSurname() {
		return ubSurname;
	}

	public void setSurname(String surname) {
		this.ubSurname = surname;
	}

	public String getEmail() {
		return ubEmail;
	}

	public void setEmail(String email) {
		this.ubEmail = email;
	}

}
