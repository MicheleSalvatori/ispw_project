package logic.bean;

import java.sql.Date;

public class AnswerBean {
	
	private int abID;
	private String abText;
	private UserBean abUser;
	private Date abDate;

	public int getId() {
		return abID;
	}

	public void setId(int id) {
		this.abID = id;
	}

	public String getText() {
		return abText;
	}

	public void setText(String text) {
		this.abText = text;
	}

	public UserBean getUser() {
		return abUser;
	}

	public void setUser(UserBean user) {
		this.abUser = user;
	}

	public Date getDate() {
		return abDate;
	}

	public void setDate(Date date) {
		this.abDate = date;
	}
}
