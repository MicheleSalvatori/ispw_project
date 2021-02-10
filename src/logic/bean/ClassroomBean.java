package logic.bean;

import java.util.List;

public class ClassroomBean {
	
	private String cbName;
	private int cbSeatRow;
	private int cbSeatColumn;
	List<SeatBean> cbSeats;

	public String getName() {
		return cbName;
	}

	public void setName(String name) {
		this.cbName = name;
	}

	public int getSeatRow() {
		return cbSeatRow;
	}

	public void setSeatRow(int seatRow) {
		this.cbSeatRow = seatRow;
	}

	public int getSeatColumn() {
		return cbSeatColumn;
	}

	public void setSeatColumn(int seatColumn) {
		this.cbSeatColumn = seatColumn;
	}
	
	public void setSeat(List<SeatBean> seat) {
        this.cbSeats = seat;
    }
	
	public List<SeatBean> getSeat() {
		return cbSeats;
	}
}
