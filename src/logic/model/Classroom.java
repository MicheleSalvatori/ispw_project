package logic.model;

public class Classroom {
	private int seatRow;
	private int seatColumn;
	private String name;

	public Classroom(String name) {
		this.name = name;
	}

	public Classroom(String name, int seatRow, int seatColumn) {
		this.name = name;
		this.seatColumn = seatColumn;
		this.seatRow = seatRow;
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
