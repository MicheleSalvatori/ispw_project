package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.model.Course;
import logic.model.Exam;
import logic.model.Lesson;
import logic.model.dao.CourseDAO;
import logic.model.dao.ExamDAO;
import logic.model.dao.LessonDAO;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
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
			
			if (PageLoader.getPage() == Page.SCHEDULED_LESSONS) {
				labelPage.setText("Lessons");
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
			}
			
			else if (PageLoader.getPage() == Page.SCHEDULED_EXAMS) {
				labelPage.setText("Exams");
				// Get user exams and courses
				if (Session.getSession().getType() == Role.STUDENT) {
					exams = ExamDAO.getNextExamsStudent(date, Session.getSession().getUsername());
					courses = CourseDAO.getStudentCourses(Session.getSession().getUsername());
				}
				else if (Session.getSession().getType() == Role.PROFESSOR) {
					exams = ExamDAO.getNextExamsProfessor(date, Session.getSession().getUsername());
					courses = CourseDAO.getProfessorCourses(Session.getSession().getUsername());
				}
				else {
					return;
				}
			}

		} catch (NullPointerException e) {
			vboxCourse.getChildren().add(new Label("No course found"));
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setLessonPage(Object obj) throws IOException {
		CourseBean course = (CourseBean) obj;
		setFilters(course);
		filterLessons(course);
	}
	
	public void setExamPage(Object obj) throws IOException {
		CourseBean course = (CourseBean) obj;
		setFilters(course);
		filterExams(course);
	}
	
	public void setFilters(CourseBean course) throws IOException {
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
	}
	
	public void filterLessons(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbrevation())) {
			filteredCourses.remove(course.getAbbrevation());
		}
		else {
			filteredCourses.add(course.getAbbrevation());
		}
		
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
	
	
	public void filterExams(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbrevation())) {
			filteredCourses.remove(course.getAbbrevation());
		}
		else {
			filteredCourses.add(course.getAbbrevation());
		}
		
		try {
			vboxScroll.getChildren().clear();
			for (Exam exam : exams) {
				if (filteredCourses.contains(exam.getCourse().getAbbrevation()) || filteredCourses.isEmpty()) {
					ExamBean examBean = new ExamBean();
					examBean.setClassroom(exam.getClassroom());
					examBean.setCourse(exam.getCourse());
					examBean.setDate(exam.getDate());
					examBean.setNote(exam.getNote());
					examBean.setTime(exam.getTime());
					
					ScheduledExamCard scheduledExamCard = new ScheduledExamCard(examBean);
					vboxScroll.getChildren().add(scheduledExamCard);
				}
			}
			
			if (vboxScroll.getChildren().isEmpty()) {
				vboxScroll.getChildren().add(new Label("No exam found."));
			}
			
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("No exam found"));
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}