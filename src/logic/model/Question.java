package logic.model;

import java.sql.Date;
import java.util.List;

public class Question {
	private int id;
	private String title;
	private String text;
	private Course course;
	private Student student;
	private boolean solved;
	private Date date;
	private List<Answer> answers;
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswers(Answer answer) {
		this.answers.add(answer);
	}

	public Question() {
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public boolean isSolved() {
		return solved;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
//		String buildString = "ID: "+getId()
//			+"\nTitle: "+ getTitle()
//			+"\nText: "+ getText()
//			+"\nAuthor: "+getStudent().getName() + " " + getStudent().getSurname();
		return getText();
	}

}
