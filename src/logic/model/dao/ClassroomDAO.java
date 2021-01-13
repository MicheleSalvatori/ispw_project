package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class ClassroomDAO {

	private ClassroomDAO() {
		
	}
	
	public static Classroom getClassroomByName(String name) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		Classroom classroom;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectClassroom(stmt, name);

			if (!rs.first()) {
				throw new RecordNotFoundException("No classroom found.");
				
			} else {
				rs.first();
				String className = rs.getString("name");
				int row = rs.getInt("seatRow");
				int col = rs.getInt("seatColumn");
//				List<Seat> seatsOf = SeatDAO.getSeatOfClassroom(className);
				classroom = new Classroom(className, row, col);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return classroom;
	}
	
	
	public static List<Classroom> getAllClassrooms() throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Classroom> classrooms;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectAllClassrooms(stmt);

			if (!rs.first()) {
				throw new RecordNotFoundException("No classroom found.");
				
			} else {
				classrooms = new ArrayList<>();
				rs.first();
				do {
					String className = rs.getString("name");
					Classroom c = new Classroom(className);
					classrooms.add(c);
				} while (rs.next());
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return classrooms;
	}
}
