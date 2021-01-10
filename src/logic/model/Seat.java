package logic.model;

public class Seat {
	private boolean free;
	private int id;
	
	public Seat(int id, boolean status) {
		this.id = id;
		this.free = status;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void occupateSeat() {
		this.free = false;
	}
	
	public void freeSeat() {
		this.free = true;
	}
	
	public boolean getState() {
		return this.free;
	}
}
