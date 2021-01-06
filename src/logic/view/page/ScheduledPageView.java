package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.model.Course;
import logic.model.Exam;
import logic.model.Lesson;
import logic.model.dao.CourseDAO;
import logic.model.dao.ExamDAO;
import logic.model.dao.LessonDAO;
import logic.utilities.Role;
import logic.utilities.SQLConverter;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ScheduledExamCard;

public class ScheduledPageView implements Initializable {

	@FXML
	private Label labelPage;
	
	@FXML
	private VBox vboxScroll, vboxCourse;
	
	List<Lesson> lessons;
	List<Exam> exams;
	List<Course> courses;
	
	List<String> filteredCourses = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Get current date
		Date date = new Date(System.currentTimeMillis());
		
		try {
			// Get user lessons and courses
			if (Session.getSession().getType() == Role.STUDENT) {
				lessons = LessonDAO.getNextLessonsStudent(date, Session.getSession().getUsername());
				courses = CourseDAO.getStudentCourses(Session.getSession().getUsername());
			}
			else if (Session.getSession().getType() == Role.PROFESSOR) {
				lessons = LessonDAO.getNextLessonsProfessor(date, Session.getSession().getUsername());
				courses = CourseDAO.getProfessorCourses(Session.getSession().getUsername());
			}
			else {
				return;
			}

		} catch (NullPointerException e) {
			vboxCourse.getChildren().add(new Label("No course found"));
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setLessonPage(CourseBean course) throws IOException {
		for (Course c : courses) {
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbrevation(c.getAbbrevation());
			courseBean.setCredits(c.getCredits());
			courseBean.setGoal(c.getGoal());
			courseBean.setName(c.getName());
			courseBean.setPrerequisites(c.getPrerequisites());
			courseBean.setReception(c.getReception());
			courseBean.setSemester(c.getSemester());
			courseBean.setYear(c.getYear());
			
			CourseFilterCard courseFilterCard = new CourseFilterCard(courseBean);
			if (courseFilterCard.getController().getCourse().getAbbrevation().compareTo(course.getAbbrevation()) == 0) {
				courseFilterCard.getController().getButton().setSelected(true);
			}
			vboxCourse.getChildren().add(courseFilterCard);
		}
		
		filterLessons(course);
	}
	
	public void filterLessons(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbrevation())) {
			filteredCourses.remove(course.getAbbrevation());
		}
		else {
			filteredCourses.add(course.getAbbrevation());
		}

		System.out.println(filteredCourses);
		
		try {
			vboxScroll.getChildren().clear();
			for (Lesson lesson : lessons) {
				if (filteredCourses.contains(lesson.getCourse().getAbbrevation()) || filteredCourses.isEmpty()) {
					LessonBean lessonBean = new LessonBean();
					lessonBean.setClassroom(lesson.getClassroom());
					lessonBean.setCourse(lesson.getCourse());
					lessonBean.setDate(lesson.getDate());
					lessonBean.setProfessor(lesson.getProfessor());
					lessonBean.setTime(lesson.getTime());
					lessonBean.setTopic(lesson.getTopic());
					
					LessonCard lessonCard = new LessonCard(lessonBean);
					vboxScroll.getChildren().add(lessonCard);
				}
			}
			
			if (vboxScroll.getChildren().isEmpty()) {
				vboxScroll.getChildren().add(new Label("No lesson found."));
			}
			
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("No lesson found"));
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// TODO finire exam
	public void setExamPage(CourseBean course) {
		labelPage.setText("Exams");
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		vboxScroll.getChildren().clear();
	
		try {
			List<Exam> exams = ExamDAO.getExamsByCourse(date, time, course.getAbbrevation());
			for (Exam exam : exams) {
				ScheduledExamCard examCard = new ScheduledExamCard(SQLConverter.date(exam.getDate()), exam.getCourse().getAbbrevation(), exam.getClassroom().getName(), SQLConverter.time(exam.getTime()));
				vboxScroll.getChildren().add(examCard);
			}
			
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("No exam found"));
			return;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
