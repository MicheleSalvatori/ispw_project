package logic;

import logic.model.User;

public class Session {
	
	private static Session instance = null;
	private User userLogged;
	private int type;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
