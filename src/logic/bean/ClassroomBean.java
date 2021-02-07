package logic.bean;

import java.util.List;

public class ClassroomBean {
	
	private String name;
	private int seatRow;
	private int seatColumn;
	List<SeatBean> seat;

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
	
	public void setSeat(List<SeatBean> seat) {
        this.seat = seat;
    }
	
	public List<SeatBean> getSeat() {
		return seat;
	}
}
