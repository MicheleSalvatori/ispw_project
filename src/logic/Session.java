package logic;

import logic.model.User;

public class Session {
	
	private static Session instance = null;
	private User userLogged;
	private int type;
	
	public static final int STUDENT_ROLE = 1;
	public static final int PROFESSOR_ROLE = 2;
	public static final int ADMIN_ROLE = 3;
	
	
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
