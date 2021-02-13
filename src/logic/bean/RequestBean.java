package logic.bean;

public class RequestBean {

	private UserBean rbStudent;
	private String rbCourse;
	
	public UserBean getStudent() {
		return rbStudent;
	}
	
	public void setStudent(UserBean student) {
		this.rbStudent = student;
	}
	
	public String getCourse() {
		return rbCourse;
	}
	
	public void setCourse(String course) {
		this.rbCourse = course;
	}
}
