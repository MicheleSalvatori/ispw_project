package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.bean.UserBean;
import logic.exceptions.SeatAlreadyBookedException;
import logic.model.Seat;
import logic.model.dao.SeatDAO;

public class BookASeatController {

	public SeatBean occupateSeat(SeatBean seat, LessonBean lesson, UserBean user) throws SQLException, SeatAlreadyBookedException {
		SeatBean mySeat = null;
		try {
			mySeat = getMySeat(lesson, user);
			SeatDAO.occupateSeat(user.getUsername(), lesson, seat.getId());
			if (mySeat != null) {
				freeSeat(mySeat, lesson, user);
			}
			mySeat = new SeatBean(seat.getId(), lesson.getClassroom().getName());
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new SeatAlreadyBookedException("Looks like someone was faster, choose another seat!");
		} 
		
		return mySeat;
	}

	public void freeSeat(SeatBean seat, LessonBean lesson, UserBean user) throws SQLException {
		SeatDAO.freeSeat(seat.getId(), user.getUsername(), lesson);
	}

	public List<SeatBean> getOccupateSeatOf(LessonBean lessonBean) throws SQLException {
		List<Seat> seats = SeatDAO.getOccupiedSeat(lessonBean);
		List<SeatBean> seatsBean = new ArrayList<>();

		for (int i = 1; i<=lessonBean.getClassroom().getSeatColumn() * lessonBean.getClassroom().getSeatRow(); i++) {		
			SeatBean sBean = new SeatBean(i);
			sBean.setFree(true);
			seatsBean.add(sBean);
		}
			
		if (seats != null) {
			for (Seat s : seats) {
				seatsBean.get(s.getIndex()-1).setFree(s.getState());
			}
		}
			
		return seatsBean;
	}

	public SeatBean getMySeat(LessonBean lesson, UserBean user) throws SQLException {
		String username = user.getUsername();
		Seat seat = SeatDAO.getMySeatIn(username, lesson);
		return new SeatBean(seat.getIndex());
	}
}