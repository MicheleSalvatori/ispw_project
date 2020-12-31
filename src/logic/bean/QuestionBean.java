package logic.bean;

import logic.model.Course;
import logic.model.Student;

public class QuestionBean {
	private int id;
	private String title;
	private String text;
	private Course questionCourse;
	private Student student;

	public QuestionBean() {
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

	public Course getQuestionCourse() {
		return questionCourse;
	}

	public void setQuestionCourse(Course questionCourse) {
		this.questionCourse = questionCourse;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	@Override
	public String toString() {
		String buildString = "ID: "+getId()
			+"\nTitle: "+ getTitle()
			+"\nText: "+ getText()
			+"\nAuthor: "+getStudent().getName() + " " + getStudent().getSurname()
			+"\nCourse: "+getQuestionCourse().getAbbrevation();
		return buildString;
	}

}
