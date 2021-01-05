package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.bean.LessonBean;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class LessonDAO {

	private LessonDAO() {
		
	}
	
	public static Lesson getLessonByDateAndTime(Date date, Time time) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		Lesson lesson;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectLesson(stmt, date, time);

			if (!rs.first()) {
				lesson = null;
			} else {
				rs.first();
				Date rs_date = rs.getDate("date");
				Time rs_time = rs.getTime("time");
				Course course = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
				Classroom classroom = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
				String topic = rs.getString("topic");
				Professor professor = ProfessorDAO.findProfessor(rs.getString("professor"));
				lesson = new Lesson(rs_date, rs_time, course, classroom, topic, professor);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return lesson;
	}
	
	public static List<Lesson> getLessonsByCourse(Date date, Time time, String course) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectLessonsByCourse(stmt, date, time, course);

			if (!rs.first()) {
				lessons = null;
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					Lesson lesson = new Lesson(d, t, c, cl);
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
	
	public static Lesson getNextLesson(Date date, Time time) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		Lesson lesson;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextLesson(stmt, date, time);

			if (!rs.first()) {
				lesson = null;
			} else {
				rs.first();
				Date rs_date = rs.getDate("date");
				Time rs_time = rs.getTime("time");
				Course course = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
				Classroom classroom = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
				String topic = rs.getString("topic");
				lesson = new Lesson(rs_date, rs_time, course, classroom, topic);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return lesson;
	}
	
	public static List<Lesson> getNextLessonsProfessor(Date date, Time time, String professor) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextLessonsByProfessor(stmt, date, time, professor);

			if (!rs.first()) {
				lessons = null;
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					Lesson lesson = new Lesson(d, t, c, cl);
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
	
	public static List<Lesson> getNextLessonsStudent(Date date, Time time, String student) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		List<Lesson> lessons;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextLessonsByStudent(stmt, date, time, student);

			if (!rs.first()) {
				lessons = null;
			} else {
				lessons = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					Lesson lesson = new Lesson(d, t, c, cl);
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
	
	public static boolean insertLesson(LessonBean lessonBean) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Date date = lessonBean.getDate();
			Time time = lessonBean.getTime();
			Course course = lessonBean.getCourse();
			Classroom classroom = lessonBean.getClassroom();
			String topic = lessonBean.getTopic();
			Professor professor = lessonBean.getProfessor();
			
			Queries.insertLesson(stmt, date, time, course.getAbbrevation(), classroom.getName(), topic, professor.getUsername());
			
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
