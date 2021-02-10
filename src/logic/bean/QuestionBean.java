package logic.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class QuestionBean {
	
	private int qbID;
	private String qbTitle;
	private String qbText;
	private String qbCourse;
	private StudentBean qbStudent;
	private boolean qbSolved;
	private Date qbDate;
	private List<AnswerBean> qbAnswers;

	public List<AnswerBean> getAnswers() {
		return qbAnswers;
	}

	public void setAnswers(List<AnswerBean> answers) {
		this.qbAnswers = answers;
	}

	public void addAnswers(AnswerBean answer) {
		if (qbAnswers == null) {
			this.qbAnswers = new ArrayList<>();
		}
		this.qbAnswers.add(answer);
	}
  
	public Date getDate() {
		return qbDate;
	}

	public void setDate(Date date) {
		this.qbDate = date;
	}

	public boolean isSolved() {
		return qbSolved;
	}

	public void setSolved(boolean solved) {
		this.qbSolved = solved;
	}

	public int getId() {
		return qbID;
	}

	public void setId(int id) {
		this.qbID = id;
	}

	public String getTitle() {
		return qbTitle;
	}

	public void setTitle(String title) {
		this.qbTitle = title;
	}

	public String getText() {
		return qbText;
	}

	public void setText(String text) {
		this.qbText = text;
	}
	
	public String getCourse() {
		return qbCourse;
	}

	public void setCourse(String course) {
		this.qbCourse = course;
	}

	public StudentBean getStudent() {
		return qbStudent;
	}

	public void setStudent(StudentBean student) {
		this.qbStudent = student;
	}

	@Override
	public String toString() {
		return "ID: "+getId()
			+"\nTitle: "+ getTitle()
			+"\nText: "+ getText()
			+"\nAuthor: "+getStudent().getName() + " " + getStudent().getSurname()
			+"\nCourse: "+getCourse();
	}
}