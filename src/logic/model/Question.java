package logic.model;

public class Question {
	private int id;
	private String title;
	private String text;
	private Course course;
	private Student student;

	public Question() {
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
