package logic.bean;

public class SeatBean {
	private boolean free;
	private int id;
	private String classroomName;

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public boolean isFree() {
		return this.free;
	}

}