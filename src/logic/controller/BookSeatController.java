package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.model.Seat;
import logic.model.dao.SeatDAO;

public class BookSeatController {

	public void occupateSeat(SeatBean seat) throws SQLException {
		SeatDAO.occupateSeat(seat.getClassroomName(), seat.getId());
	}

	public void freeSeat(SeatBean seat) throws SQLException {
		SeatDAO.freeSeat(seat.getClassroomName(), seat.getId());
	}

	public List<SeatBean> getOccupateSeatOf(LessonBean lessonBean) throws SQLException {
		List<Seat> seats = SeatDAO.getOccupiedSeat(lessonBean);
		List<SeatBean> seatsBean = new ArrayList<>();

		if (seats == null) {		//TODO gestire con eccezzione
			return null;
		}

		for (Seat s : seats) {
			SeatBean sBean = new SeatBean();
			sBean.setId(s.getIndex());
			sBean.setFree(s.getState());
			seatsBean.add(sBean);
		}

		return seatsBean;
	}
}
