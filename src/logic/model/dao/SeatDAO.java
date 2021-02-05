package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.model.Seat;
import logic.utilities.Queries;
import logic.utilities.SQLConverter;
import logic.utilities.SingletonDB;

public class SeatDAO {
	
	private SeatDAO() {
		
	}

	public static void occupateSeat(String username, LessonBean lesson, int seatID) throws SQLException {
		Statement stmt = null;
		Connection conn = null;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			Queries.occupateSeat(stmt, seatID, lesson.getClassroom().getName(), lesson.getDate().toString(), lesson.getTime().toString(), lesson.getCourse().getAbbreviation(), username);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public static void freeSeat(int seatID, String user, LessonBean lessonBean) throws SQLException {
		Statement stmt = null;
		Connection conn = null;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			Queries.freeSeat(stmt, seatID, lessonBean.getClassroom().getName(), user, lessonBean.getCourse().getAbbreviation(), lessonBean.getDate().toString(), lessonBean.getTime().toString());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public static List<Seat> getSeatOfClassroom(String nameClassroom) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		List<Seat> seats;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.findSeatOfClassroom(stmt, nameClassroom);

			if (!rs.first()) {
				seats = null;
			} else {
				rs.first();
				seats = new ArrayList<>();
				do {
					int id = rs.getInt("id");
					boolean status = rs.getBoolean("free");
					Seat s = new Seat(id, status);
					seats.add(s);
				} while (rs.next());
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return seats;
	}

	public static List<Seat> getOccupiedSeat(LessonBean lesson) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		List<Seat> occupiedSeats;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			String dateLesson = SQLConverter.date(lesson.getDate());		// Non funziona, sul db è yyyy/mm/gg
			String dateLesson = lesson.getDate().toString();
			String timeLesson = SQLConverter.time(lesson.getTime());
			ResultSet rs = Queries.getOccupateSeats(stmt, lesson.getCourse().getAbbreviation(), dateLesson, timeLesson);

			if (!rs.first()) {
				occupiedSeats = null;
			} else {
				rs.first();
				occupiedSeats = new ArrayList<>();
				do {
					int id = rs.getInt("seat");
					occupiedSeats.add(new Seat(id, false));
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return occupiedSeats;
	}

	public static SeatBean getMySeatIn(String username, LessonBean lesson) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		SeatBean mySeat;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Non mettere SQLConverte per date che non funziona, gli serve prima l'anno
			ResultSet rs = Queries.getSeat(stmt, username, lesson.getDate().toString(), lesson.getTime().toString(),
					lesson.getCourse().getAbbreviation());

			if (!rs.first()) {
				mySeat = null;
			} else {
				rs.first();
				mySeat = new SeatBean(rs.getInt("seat"));
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return mySeat;
	}
}
