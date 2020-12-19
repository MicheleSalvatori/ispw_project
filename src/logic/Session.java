package logic;

import logic.model.User;

public class Session {
	
	private static Session instance = null;
	private User userLogged;
	
	private Session() {}

	public static Session getSession() {
		if (Session.instance == null) {
			Session.instance = new Session();
		}
		return instance;
	}
	
	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}
	
	public User getUserLogged() {
		return userLogged;
	}

}
