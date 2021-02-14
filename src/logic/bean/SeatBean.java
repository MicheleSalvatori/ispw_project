package logic.bean;

public class SeatBean {
	
	private boolean sebFree;
	private int sebID;
	private String sebClassroomName;
	
	public SeatBean(int id) {
		setId(id);
	}
	
	public SeatBean(int id, String classroom) {
		setId(id);
		setClassroomName(classroom);
	}
	
	public String getClassroomName() {
		return sebClassroomName;
	}

	public void setClassroomName(String classroomName) {
		this.sebClassroomName = classroomName;
	}

	public int getId() {
		return sebID;
	}

	public void setId(int id) {
		this.sebID = id;
	}

	public void setFree(boolean free) {
		this.sebFree = free;
	}

	public boolean isFree() {
		return this.sebFree;
	}

}
