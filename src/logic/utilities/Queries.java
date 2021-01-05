package logic.utilities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class Queries {
	
	// Role queries
	public static int insertRole(Statement stmt, String username) throws SQLException {
		String query = "INSERT INTO role (username, type) VALUES ('" + username + "', 'student');";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	
	// Porfessor queries
	public static ResultSet selectProfessor(Statement stmt, String username, String password) throws SQLException {
		String query = "SELECT * FROM professor WHERE username = '" +  username + "' AND password = '" + password + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectProfessorByUsername(Statement stmt, String username) throws SQLException {
		String query = "SELECT * FROM professor WHERE username = '" +  username + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectProfessorsByCourse(Statement stmt, String course) throws SQLException {
		String query = "SELECT username, name, surname, email FROM teach JOIN professor on teach.professor = professor.username WHERE teach.course = '" + course + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static int updateProfessorPassword(Statement stmt, String username, String password) throws SQLException {
		String query = "UPDATE professor SET password = '" + password + "' WHERE username = '" + username + "';";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	
	// Student queries
	public static ResultSet selectStudent(Statement stmt, String username, String password) throws SQLException {
		String query = "SELECT * FROM student WHERE username = '" +  username + "' AND password = '" + password + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectStudentByUsername(Statement stmt, String username) throws SQLException {
		String query = "SELECT * FROM student WHERE username = '" +  username + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static int insertStudent(Statement stmt, String username, String password, String name, String surname, String email) throws SQLException {
		String query = "INSERT INTO student (username, password, name, surname, email) VALUES ('" + username + "', '" + password + "', '" + name + "', '" + surname + "', '" + email + "');";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	public static int updateStudentPassword(Statement stmt, String username, String password) throws SQLException {
		String query = "UPDATE student SET password = '" + password + "' WHERE username = '" + username + "';";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	
	// Lesson queries
	public static ResultSet selectLesson(Statement stmt, Date date, Time time) throws SQLException {
		String query = "SELECT * FROM lesson WHERE date = '" + date + "' AND time = '" + time + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectNextLessonsByProfessor(Statement stmt, Date date, Time time, String professor) throws SQLException {
		String query = "SELECT date, time, course, classroom, topic FROM lesson WHERE date = '" + date + "' AND time >= '" + time + "' AND professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectNextLessonsByStudent(Statement stmt, Date date, Time time, String student) throws SQLException {
		String query = "SELECT date, time, lesson.course, classroom, topic FROM lesson JOIN follow ON lesson.course = follow.course WHERE date = '" + date + "' AND time >= '" + time + "' AND student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectNextLesson(Statement stmt, Date date, Time time) throws SQLException {
		String query = "SELECT * FROM lesson WHERE date >= '" + date + "' AND time >= '" + time + "' LIMIT 1;";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectLessonsByCourse(Statement stmt, Date date, Time time, String course) throws SQLException {
		String query = "SELECT * FROM lesson WHERE date >= '" + date + "' AND time >= '" + time + "' AND course = '" + course + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static int insertLesson(Statement stmt, Date date, Time time, String course, String classroom, String topic, String professor) throws SQLException {
		String query = String.format("INSERT INTO lesson (date, time, course, classroom, topic, professor) VALUES('%s', '%s', '%s', '%s', '%s', '%s')", date, time, course, classroom, topic, professor);
		System.out.println(query);
		return stmt.executeUpdate(query); 
	}
	
	
	// Exam queries
	public static ResultSet selectExam(Statement stmt, Date date, Time time) throws SQLException {
		String query = "SELECT * FROM exam WHERE date = '" + date + "' AND time = '" + time + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectNextExams(Statement stmt, Date date, Time time) throws SQLException {
		String query = "SELECT * FROM exam WHERE date = '" + date + "' AND time >= '" + time + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectExamsByCourse(Statement stmt, Date date, Time time, String course) throws SQLException {
		String query = "SELECT * FROM exam WHERE date >= '" + date + "' AND time >= '" + time + "' AND course = '" + course + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static int insertExam(Statement stmt, Date date, Time time, String course, String classroom, String note) throws SQLException {
		String query = String.format("INSERT INTO exam (date, time, course, classroom, note) VALUES('%s', '%s', '%s', '%s', '%s')", date, time, course, classroom, note);
		System.out.println(query);
		return stmt.executeUpdate(query); 
	}
	
	
	// Course queries
	public static ResultSet selectCourse(Statement stmt, String abbr) throws SQLException {
		String query = "SELECT * FROM course WHERE abbrevation = '" + abbr + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectCoursesByProfessor(Statement stmt, String professor) throws SQLException {
		String query = "SELECT course.abbrevation, course.name FROM teach JOIN course ON teach.course = course.abbrevation WHERE teach.professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectCoursesByStudent(Statement stmt, String student) throws SQLException {
		String query = "SELECT course.abbrevation, course.name FROM follow JOIN course ON follow.course = course.abbrevation WHERE follow.student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectCoursesRequestedByStudent(Statement stmt, String student) throws SQLException {
		String query = "SELECT course.abbrevation, course.name FROM request JOIN course ON request.course = course.abbrevation WHERE request.student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectAvailableCourses(Statement stmt, String student) throws SQLException {
		String query = "SELECT * FROM course WHERE (abbrevation, name) NOT IN "
						+ "(SELECT course.abbrevation, course.name FROM follow JOIN course ON follow.course = course.abbrevation WHERE follow.student = '" + student + "') "
						+ "AND (abbrevation, name) NOT IN (SELECT course.abbrevation, course.name FROM request JOIN course ON request.course = course.abbrevation WHERE request.student = '" + student + "');";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectAllCourses(Statement stmt) throws SQLException {
		String query = "SELECT * FROM course;";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	
	// Classroom queries
	public static ResultSet selectClassroom(Statement stmt, String name) throws SQLException {
		String query = "SELECT * FROM classroom WHERE name = '" + name + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectAllClassrooms(Statement stmt) throws SQLException {
		String query = "SELECT * FROM classroom;";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	
	// Question queries
	public static ResultSet selectQuestionsOfCourse(Statement stmt, String abbrevation) throws SQLException {
		String query = "SELECT * FROM question WHERE course = '" + abbrevation + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectQuestionsByUsername(Statement stmt, String username) throws SQLException {
		String query = "SELECT * FROM question WHERE username = '" + username + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static int insertQuestion(Statement stmt, String title, String text, String username, String course) throws SQLException {
		String query = String.format("INSERT INTO question (title, text, username, course) VALUES('%s', '%s', '%s', '%s')", title, text, username, course);
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	
	// Request queries
	public static ResultSet selectRequest(Statement stmt, String student, String course) throws SQLException {
		String query = "SELECT * FROM request WHERE student = '" + student + "' AND course = '" + course + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectRequestsByProfessor(Statement stmt, String professor) throws SQLException {
		String query = "SELECT student, request.course FROM request JOIN teach ON request.course = teach.course WHERE professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectRequestsByProfessorAndCourse(Statement stmt, String professor, String course) throws SQLException {
		String query = "SELECT student, request.course FROM request JOIN teach ON request.course = teach.course WHERE professor = '" + professor + "' AND request.course = '" + course + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static int insertRequest(Statement stmt, String student, String course) throws SQLException {
		String query = String.format("INSERT INTO request (student, course) VALUES('%s', '%s')", student, course);
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	public static int insertFollow(Statement stmt, String student, String course) throws SQLException {
		String query = String.format("INSERT INTO follow (course, student) VALUES('%s', '%s')", course, student);
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	public static int deleteRequest(Statement stmt, String student, String course) throws SQLException {
		String query = String.format("DELETE FROM request WHERE student = '%s' AND course = '%s';", student, course);
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	public static int deleteFollow(Statement stmt, String student, String course) throws SQLException {
		String query = String.format("DELETE FROM follow WHERE student = '%s' AND course = '%s';", student, course);
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
}

