package logic.bean;

public class RequestBean {

	private StudentBean rbStudent;
	private CourseBean rbCourse;
	
	public StudentBean getStudent() {
		return rbStudent;
	}
	
	public void setStudent(StudentBean student) {
		this.rbStudent = student;
	}
	
	public CourseBean getCourse() {
		return rbCourse;
	}
	
	public void setCourse(CourseBean course) {
		this.rbCourse = course;
	}
}
