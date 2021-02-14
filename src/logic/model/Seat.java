package logic.model;

public class Seat {
	private int id;
	private boolean free;
	
	public Seat(int index, boolean status) {
		this.id = index;
		this.free = status;
	}
	
	public Seat(int id) {
		this.id = id;
	}

	public int getIndex() {
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
