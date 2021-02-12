package logic.bean;

public class RequestBean {

	private UserBean rbStudent;
	private CourseBean rbCourse;
	
	public UserBean getStudent() {
		return rbStudent;
	}
	
	public void setStudent(UserBean student) {
		this.rbStudent = student;
	}
	
	public CourseBean getCourse() {
		return rbCourse;
	}
	
	public void setCourse(CourseBean course) {
		this.rbCourse = course;
	}
}
