package logic.bean;

import java.sql.Date;

public class CommunicationBean {
	
	private int cobID;
	private String cobText;
	private String cobTitle;
	private Date cobDate;

	public int getId() {
		return cobID;
	}

	public void setId(int id) {
		this.cobID = id;
	}

	public String getText() {
		return cobText;
	}

	public void setText(String text) {
		this.cobText = text;
	}

	public String getTitle() {
		return cobTitle;
	}

	public void setTitle(String title) {
		this.cobTitle = title;
	}

	public Date getDate() {
		return cobDate;
	}

	public void setDate(Date date) {
		this.cobDate = date;
	}
}
