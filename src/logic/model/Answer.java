package logic.model;

import java.sql.Date;

public class Answer {
	private int id;
	private String text;
	private User user;
	private Date date;

	public Answer(int id, String text, User user, Date date) {
		this.id = id;
		this.text = text;
		this.user = user;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
