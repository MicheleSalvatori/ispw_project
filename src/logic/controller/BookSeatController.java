package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.model.Seat;
import logic.model.dao.SeatDAO;

public class BookSeatController {

	public SeatBean occupateSeat(SeatBean seat, LessonBean lesson) throws SQLException, DuplicatedRecordException {
		SeatBean mySeat = null;
		try {
			mySeat = getMySeat(lesson);
			SeatDAO.occupateSeat(UserBean.getInstance().getUsername(), lesson, seat.getId());
			if (mySeat != null) {
				freeSeat(mySeat, lesson);
			}
			mySeat = new SeatBean(seat.getId(), lesson.getClassroom().getName());
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DuplicatedRecordException("Looks like someone was faster, choose another seat!");
		} 
		return mySeat;
	}

	public void freeSeat(SeatBean seat, LessonBean lesson) throws SQLException {
		SeatDAO.freeSeat(seat.getId(), UserBean.getInstance().getUsername(), lesson);
	}

	public List<SeatBean> getOccupateSeatOf(LessonBean lessonBean) throws SQLException {
		List<Seat> seats = SeatDAO.getOccupiedSeat(lessonBean);
		List<SeatBean> seatsBean = new ArrayList<>();

		if (seats == null) {
			seatsBean = null;
		} else {

			for (int i = 1; i<=lessonBean.getClassroom().getSeatColumn() * lessonBean.getClassroom().getSeatRow(); i++) {
//				SeatBean sBean = new SeatBean(s.getIndex());
//				sBean.setFree(s.getState());
//				seatsBean.add(sBean);
				
				SeatBean sBean = new SeatBean(i);
				sBean.setFree(true);
				seatsBean.add(sBean);
			}
			
			for (Seat s : seats) {
				System.out.println("S: "+s.getIndex()+s.getState());
				seatsBean.get(s.getIndex()-1).setFree(s.getState());
			}
		}
		return seatsBean;
	}

	public SeatBean getMySeat(LessonBean lesson, UserBean user) throws SQLException {
		String username = user.getUsername();
		SeatBean mySeat = SeatDAO.getMySeatIn(username, lesson);
		return mySeat;
	}
}
