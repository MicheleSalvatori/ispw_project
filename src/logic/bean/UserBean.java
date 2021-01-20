package logic.bean;

import logic.utilities.Role;

public class UserBean {
	
	private static UserBean userInstance = null;
	private Role role;
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	
	public static UserBean getInstance() {
		return UserBean.userInstance;
	}
	
	public static void setInstance(UserBean userBean) {
		UserBean.userInstance = userBean;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
