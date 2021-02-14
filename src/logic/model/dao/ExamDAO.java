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
import logic.model.Exam;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class ExamDAO {
	
	private static String noExam = "No exam found";
	private static String rsCourse = "course";
	private static String rsClassroom = "classroom";

	private ExamDAO() {

	}

	public static Exam getExamByDateAndTime(Date date, Time time) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		Exam exam;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectExam(stmt, date, time);

			if (!rs.first()) {
				throw new RecordNotFoundException(noExam);

			} else {
				rs.first();
				Date d = rs.getDate("date");
				Time t = rs.getTime("time");
				Course c = CourseDAO.getCourseByAbbrevation(rs.getString(rsCourse));
				Classroom cl = ClassroomDAO.getClassroomByName(rs.getString(rsClassroom));
				String note = rs.getString("note");
				exam = new Exam(d, t, c, cl, note);
			}
			rs.close();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return exam;
	}

	public static List<Exam> getExamsByCourse(Date date, Time time, String course)
			throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectExamsByCourse(stmt, date, time, course);
			exams = getExams(rs);

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return exams;
	}

	public static List<Exam> getNextExams(Date date, Time time) throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextExams(stmt, date, time);
			exams = getExams(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return exams;
	}

	public static List<Exam> getNextExamsStudent(Date date, String student)
			throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextExamsByStudent(stmt, date, student);
			exams = getExams(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return exams;
	}

	public static List<Exam> getNextExamsProfessor(Date date, String professor)
			throws SQLException, RecordNotFoundException {

		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextExamsByProfessor(stmt, date, professor);
			exams = getExams(rs);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return exams;
	}
	
	private static List<Exam> getExams(ResultSet rs) throws SQLException, RecordNotFoundException {
		List<Exam> exams;
		
		if (!rs.first()) {
			throw new RecordNotFoundException(noExam);

		} else {
			exams = new ArrayList<>();
			rs.first();
			do {
				Date d = rs.getDate("date");
				Time t = rs.getTime("time");
				Course c = CourseDAO.getCourseByAbbrevation(rs.getString(rsCourse));
				Classroom cl = ClassroomDAO.getClassroomByName(rs.getString(rsClassroom));
				String n = rs.getString("note");
				Exam exam = new Exam(d, t, c, cl, n);
				exams.add(exam);
			} while (rs.next());
		}
		rs.close();
		
		return exams;
	}

	public static boolean insertExam(Exam exam) {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			Date date = exam.getDate();
			Time time = exam.getTime();
			Course course = exam.getCourse();
			Classroom classroom = exam.getClassroom();
			String note = exam.getNote();

			Queries.insertExam(stmt, date, time, course.getAbbreviation(), classroom.getName(), note);

		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public static boolean deleteExam(Exam exam){
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			Queries.delteExam(stmt, exam.getDate(), exam.getTime(), exam.getCourse().getAbbreviation());
		}catch(SQLException e) {
			return false;
		}
		
		return true;
	}
	
}
