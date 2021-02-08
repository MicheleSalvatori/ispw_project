package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.WeeklyLesson;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class WeeklyLessonDAO {

	private WeeklyLessonDAO() {
		
	}

	public static List<WeeklyLesson> getCourseWeeklyLessons(String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<WeeklyLesson> lessons;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectWeeklyLessonsByCourse(stmt, course);

			if (!rs.first()) {
				throw new RecordNotFoundException("No weekly lesson found");
				
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					String d = rs.getString("day");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					WeeklyLesson lesson = new WeeklyLesson(d, t, cl, c);
					
					lessons.add(lesson);
				} while (rs.next());
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return lessons;
	}
}
