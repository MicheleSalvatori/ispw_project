package logic.bean;

import logic.model.Classroom;

public class SeatBean {
	private boolean free;
	private int id;
//	private Classroom classroom;		//Forse nelle bean possiamo passare anche delle Entity?
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

//	public Classroom getClassroom() {
//		return classroom;
//	}
//
//	public void setClassroom(Classroom classroom) {
//		this.classroom = classroom;
//	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public boolean isFree() {
		return this.free;
	}

}
