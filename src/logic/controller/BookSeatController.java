package logic.controller;

import java.sql.SQLException;

import logic.bean.SeatBean;
import logic.model.dao.SeatDAO;

public class BookSeatController {

	public void occupateSeat(SeatBean seat) throws SQLException {
		SeatDAO.occupateSeat(seat.getClassroomName(), seat.getId());
	}
	
	public void freeSeat(SeatBean seat) throws SQLException {
		SeatDAO.freeSeat(seat.getClassroomName(), seat.getId());
	}
}
