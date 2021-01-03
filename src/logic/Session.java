package logic;

import logic.model.User;
import logic.utilities.Role;

public class Session {
	
	private static Session instance = null;
	private User userLogged;
	private Role type;
	
	private Session() {}

	public static Session getSession() {
		if (Session.instance == null) {
			Session.instance = new Session();
		}
		return instance;
	}
	
	public User getUserLogged() {
		return userLogged;
	}
	
	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}

	public Role getType() {
		return type;
	}

	public void setType(Role type) {
		this.type = type;
	}
}
