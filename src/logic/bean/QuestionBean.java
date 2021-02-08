package logic.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class QuestionBean {
	
	private int id;
	private String title;
	private String text;
	private String course;
	private StudentBean student;
	private boolean solved;
	private Date date;
	private List<AnswerBean> answers;

	public List<AnswerBean> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerBean> answers) {
		this.answers = answers;
	}

	public void addAnswers(AnswerBean answer) {
		if (answers == null) {
			this.answers = new ArrayList<>();
		}
		this.answers.add(answer);
	}
  
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

//	public void setCourseByAbbr(String course) {
//		CourseBean c = new CourseBean();
//		c.setAbbreviation(course);
//		this.course = c;
//	}

	public StudentBean getStudent() {
		return student;
	}

	public void setStudent(StudentBean student) {
		this.student = student;
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