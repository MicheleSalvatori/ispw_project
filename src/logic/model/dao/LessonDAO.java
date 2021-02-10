package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class LessonDAO {
	private static String noLesson = "No lesson found";
	private LessonDAO() {
		
	}
	
	public static Lesson getLessonByDateAndTime(Date date, Time time) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		Lesson lesson;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectLesson(stmt, date, time);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				rs.first();
				lesson = getLesson(rs);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return lesson;
	}
	
	public static List<Lesson> getLessonsByCourse(Date date, Time time, String course) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectLessonsByCourse(stmt, date, time, course);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Lesson lesson = getLesson(rs);
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
	
	public static Lesson getNextLessonByCourse(Date date, Time time, String course) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		Lesson lesson;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextLessonByCourse(stmt, date, time, course);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				rs.first();
				lesson = getLesson(rs);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return lesson;
	}
	
	public static List<Lesson> getNextLessonsProfessor(Date date, String professor) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextLessonsByProfessor(stmt, date, professor);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Lesson lesson = getLesson(rs);
					lessons.add(lesson);
				} while (rs.next());
			}
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return lessons;
	}
	
	public static List<Lesson> getTodayNextLessonsProfessor(Date date, Time time, String professor) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectTodayNextLessonsByProfessor(stmt, date, time, professor);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Lesson lesson = getLesson(rs);
					lessons.add(lesson);
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return lessons;
	}
	
	public static List<Lesson> getNextLessonsStudent(Date date, String student) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextLessonsByStudent(stmt, date, student);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Lesson lesson = getLesson(rs);
					lessons.add(lesson);
				} while (rs.next());
			}
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return lessons;
	}
	
	public static List<Lesson> getTodayNextLessonsStudent(Date date, Time time, String student) throws SQLException, RecordNotFoundException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectTodayNextLessonsByStudent(stmt, date, time, student);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Lesson lesson = getLesson(rs);
					lessons.add(lesson);
				} while (rs.next());
			}
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return lessons;
	}
	
	public static boolean insertLesson(Lesson lesson) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Date date = lesson.getDate();
			Time time = lesson.getTime();
			Course course = lesson.getCourse();
			Classroom classroom = lesson.getClassroom();
			String topic = lesson.getTopic();
			Professor professor = lesson.getProfessor();
			
			Queries.insertLesson(stmt, date, time, course.getAbbreviation(), classroom.getName(), topic, professor.getUsername());
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static Lesson getLesson(Date date, Time time, String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		Lesson lesson;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectLesson(stmt, date, time, course);

			if (!rs.first()) {
				throw new RecordNotFoundException(noLesson);
				
			} else {
				rs.first();
				lesson = getLesson(rs);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return lesson;
	}
	
	private static Lesson getLesson(ResultSet rs) throws SQLException, RecordNotFoundException {
		Date d = rs.getDate("date");
		Time t = rs.getTime("time");
		Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
		Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
		String to = rs.getString("topic");
		Professor p = ProfessorDAO.findProfessor(rs.getString("professor"));
		return new Lesson(d, t, c, cl, to, p);
	}
}
