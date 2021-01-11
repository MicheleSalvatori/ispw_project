package logic.model;

import java.util.List;

public class Classroom {
	
	private String name;
	private int seatRow;
	private int seatColumn;
	private List<Seat> seats;

	public Classroom(String name) {
		this.name = name;
	}

	public Classroom(String name, int seatRow, int seatColumn) {
		this.name = name;
		this.seatColumn = seatColumn;
		this.seatRow = seatRow;
	}
	
	public Classroom(String name, int seatRow, int seatColumn, List<Seat> seats) {
		this.name = name;
		this.seatColumn = seatColumn;
		this.seatRow = seatRow;
		this.seats = seats;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getSeatColumn() {
		return seatColumn;
	}

	public void setSeatColumn(int seatColumn) {
		this.seatColumn = seatColumn;
	}

}
