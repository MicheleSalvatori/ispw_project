package logic.utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import com.mysql.jdbc.PreparedStatement;

public class Queries {
	
	// User queries
	public static ResultSet selectUserByEmail(Statement stmt, String email) throws SQLException {
		String query = "SELECT username, password, email FROM role NATURAL JOIN admin WHERE email = '" + email + "' "
				+ "UNION SELECT username, password, email FROM role NATURAL JOIN professor WHERE email = '" + email + "' "
				+ "UNION SELECT username, password, email FROM role NATURAL JOIN student WHERE email = '" + email + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectRole(Statement stmt, String username) throws SQLException {
		String query = "SELECT * FROM role WHERE username = '" +  username +  "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}

	public static int insertRole(Statement stmt, String username) throws SQLException {
		String query = "INSERT INTO role (username, type) VALUES ('" + username + "', 'student');";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	// Admin queries
	public static ResultSet selectAdmin(Statement stmt, String username, String password) throws SQLException {
		String query = "SELECT * FROM admin WHERE username = '" +  username + "' AND password = '" + password + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
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

	public static ResultSet getQuestionsCourse(Statement stmt, String username) throws SQLException {
		String query = "SELECT * FROM question where course in (SELECT course FROM follow WHERE student = '"+username+"') ORDER BY id DESC;";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet getQuestionsProfessorCourse(Statement stmt, String username) throws SQLException{
		String query = "SELECT * FROM question where course in (SELECT course FROM teach WHERE professor = '"+username+"') ORDER BY id DESC;";
		System.out.println(query);
		return stmt.executeQuery(query);
	}

	public static ResultSet findCourseOfStudent(Statement stmt, String username) throws SQLException {
		String sql = "SELECT course from follow where student ='"+username+"';";
		System.out.println(sql);
		return stmt.executeQuery(sql);
	}

	public static ResultSet findCourseByAbbr(Statement stmt, String abbrevation) throws SQLException {
		String sql = "SELECT * from course where abbrevation ='"+abbrevation+"';";
		return stmt.executeQuery(sql);
	}

	public static ResultSet findStudent(Statement stmt, String username) throws SQLException {
		String sql = "SELECT * from student where username = '"+username+"';";
		return stmt.executeQuery(sql);
	}
	
	public static int updateStudentPassword(Statement stmt, String username, String password) throws SQLException {
		String query = "UPDATE student SET password = '" + password + "' WHERE username = '" + username + "';";
		System.out.println(query);
		return stmt.executeUpdate(query);
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
		
	public static ResultSet getQuestionByUsername(Statement stmt, String username) throws SQLException {
		String sql = "SELECT * from question where username = '"+username+"';";
		return stmt.executeQuery(sql);
	}

	public static void saveQuestion(Connection conn, String subject, String text, String username, String course, Date date, boolean solved) throws SQLException {
		String sql = "INSERT INTO question VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setNull(1, java.sql.Types.INTEGER);
		stmt.setString(2, subject);
		stmt.setString(3, text);
		stmt.setString(4, username);
		stmt.setString(5, course);
		stmt.setBoolean(6, solved);
		stmt.setDate(7, date);
		
		stmt.executeUpdate();	
		if (stmt != null) {
			stmt.close();
		}
	}

	
	// Answer queries
	public static ResultSet findAnswerByIDQuestion(Statement stmt, int id) throws SQLException {
		String sql = "SELECT * from answer where id = '"+id+"';";
		System.out.println(sql);
		return stmt.executeQuery(sql);
	}
	
	public static void saveAnswer(Connection conn, int id, String username, String text, Date date) throws SQLException {
		String sql = "INSERT INTO answer VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.setString(2, username);
		stmt.setString(3, text);
		stmt.setDate(4, date);
		stmt.executeUpdate();	
		if (stmt != null) {
			stmt.close();
		}
	}

	public static ResultSet countCourses(Statement stmt, String username) throws SQLException {
		String sql  = "SELECT count(distinct course) from follow where student ='"+username+"';"; 
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet countCoursesProf(Statement stmt, String username) throws SQLException {
		String sql  = "SELECT count(distinct course) from teach where professor ='"+username+"';"; 
		return stmt.executeQuery(sql);
	}

	// Lesson queries
	public static ResultSet selectLesson(Statement stmt, Date date, Time time) throws SQLException {
		String query = "SELECT * FROM lesson WHERE date = '" + date + "' AND time = '" + time + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectNextLessonsByProfessor(Statement stmt, Date date, String professor) throws SQLException {
		String query = "SELECT date, time, course, classroom, topic, professor FROM lesson WHERE date >= '" + date + "' AND professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectNextLessonsByStudent(Statement stmt, Date date, String student) throws SQLException {
		String query = "SELECT date, time, lesson.course, classroom, topic, professor FROM lesson JOIN follow ON lesson.course = follow.course WHERE date >= '" + date + "' AND student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectTodayNextLessonsByProfessor(Statement stmt, Date date, Time time, String professor) throws SQLException {
		String query = "SELECT date, time, course, classroom, topic, professor FROM lesson WHERE date >= '" + date + "' AND time >= '" + time + "' AND professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectTodayNextLessonsByStudent(Statement stmt, Date date, Time time, String student) throws SQLException {
		String query = "SELECT date, time, lesson.course, classroom, topic, professor FROM lesson JOIN follow ON lesson.course = follow.course WHERE date >= '" + date + "' AND time >= '" + time + "' AND student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectNextLessonByCourse(Statement stmt, Date date, Time time, String course) throws SQLException {
		String query = "SELECT * FROM lesson WHERE date >= '" + date + "' AND course = '" + course + "' LIMIT 1;";
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
	
	public static ResultSet selectNextExamsByStudent(Statement stmt, Date date, String student) throws SQLException {
		String query = "SELECT date, time, exam.course, classroom, note FROM exam JOIN follow ON exam.course = follow.course WHERE exam.date >= '" + date + "' AND follow.student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query); 
	}
	
	public static ResultSet selectNextExamsByProfessor(Statement stmt, Date date, String professor) throws SQLException {
		String query = "SELECT date, time, exam.course, classroom, note FROM exam JOIN teach ON exam.course = teach.course WHERE exam.date >= '" + date + "' AND teach.professor = '" + professor + "';";
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
		String query = "SELECT course.abbrevation, course.name, year, credits, semester, prerequisites, goal, reception FROM teach JOIN course ON teach.course = course.abbrevation WHERE teach.professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectCoursesByStudent(Statement stmt, String student) throws SQLException {
		String query = "SELECT course.abbrevation, course.name, year, credits, semester, prerequisites, goal, reception FROM follow JOIN course ON follow.course = course.abbrevation WHERE follow.student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectCoursesRequestedByStudent(Statement stmt, String student) throws SQLException {
		String query = "SELECT course.abbrevation, course.name, year, credits, semester, prerequisites, goal, reception FROM request JOIN course ON request.course = course.abbrevation WHERE request.student = '" + student + "';";
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
	
	
	// Verbalized queries
	public static ResultSet selectVerbalizedExamsByStudent(Statement stmt, String student) throws SQLException {
		String query = "SELECT * FROM verbalized WHERE student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}


	public static int setQuestionSolved(Statement stmt, int questionID) throws SQLException {
		String sql = String.format("UPDATE question SET solved = true WHERE id = '%d';", questionID);
		System.out.println(sql);
		return stmt.executeUpdate(sql);
	}


	
	// Assignment queries
	public static ResultSet selectAssignmentsByProfessor(Statement stmt, String professor) throws SQLException {
		String query = "SELECT id, assignment.course, title, text, date FROM assignment JOIN teach ON assignment.course = teach.course WHERE professor = '" + professor + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}
	
	public static ResultSet selectAssignmentsByStudent(Statement stmt, String student) throws SQLException {
		String query = "SELECT id, assignment.course, title, text, date FROM assignment JOIN follow ON assignment.course = follow.course WHERE student = '" + student + "';";
		System.out.println(query);
		return stmt.executeQuery(query);
	}

}