
package logic.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import logic.model.Answer;
import logic.model.Course;
import logic.model.Student;

public class QuestionBean {
	
	private int id;
	private String title;
	private String text;
	private Course course;
	private Student student;
	private boolean solved;
	private Date date;
	private List<Answer> answers;	
	
	public QuestionBean() {
		
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswers(Answer answer) {
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
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setCourseByAbbr(String course) {
		Course c = new Course();
		c.setAbbrevation(course);
		this.course = c;
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
			+"\nCourse: "+getCourse().getAbbrevation();
		return buildString;
	}
}