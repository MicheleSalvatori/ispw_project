package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.Session;
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
	
	public void setLessonPage(String course) {
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		vboxScroll.getChildren().clear();
		insertFilters(course);
	
		try {
			
			List<Lesson> lessons;
			if (Session.getSession().getType() == Role.STUDENT) {
				lessons = LessonDAO.getNextLessonsStudent(date, time, Session.getSession().getUserLogged().getUsername());
			}
			else if (Session.getSession().getType() == Role.PROFESSOR) {
				lessons = LessonDAO.getNextLessonsProfessor(date, time, Session.getSession().getUserLogged().getUsername());
			}
			else {
				return;
			}

			for (Lesson lesson : lessons) {
				LessonCard lessonCard = new LessonCard(SQLConverter.date(lesson.getDate()), lesson.getClassroom().getName(), lesson.getCourse().getAbbrevation(), SQLConverter.time(lesson.getTime()));
				vboxScroll.getChildren().add(lessonCard);
			}
			
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("No lesson found"));
			return;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setExamPage(String course) {
		labelPage.setText("Exams");
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		vboxScroll.getChildren().clear();
		insertFilters(course);
	
		try {
			List<Exam> exams = ExamDAO.getExamsByCourse(date, time, course);
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
	
	private void insertFilters(String c) {
		try {
			vboxCourse.getChildren().clear();
			List<Course> courses;
			if (Session.getSession().getType() == Role.STUDENT) {
				courses = CourseDAO.getStudentCourses(Session.getSession().getUserLogged().getUsername());
			}
			else if (Session.getSession().getType() == Role.PROFESSOR) {
				courses = CourseDAO.getProfessorCourses(Session.getSession().getUserLogged().getUsername());
			}
			else {
				return;
			}

			for (Course course : courses) {
				CourseFilterCard courseFilterCard = new CourseFilterCard(course.getAbbrevation());
				vboxCourse.getChildren().add(courseFilterCard);
				System.out.println(course.getAbbrevation() + "  :  " + c);
				if (course.getAbbrevation().compareTo(c) == 0) {
					courseFilterCard.getController().getButton().setSelected(true);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void setPage() {
		
		if (PageLoader.getPage() == Page.SCHEDULED_LESSONS) {
			
			Date date = new Date(System.currentTimeMillis());
			Time time = new Time(System.currentTimeMillis());
		
			try {
				
				List<Lesson> lessons;
				if (Session.getSession().getType() == Session.Role.STUDENT) {
					lessons = LessonDAO.getNextLessonsStudent(date, time, Session.getSession().getUserLogged().getUsername());
				}
				else if (Session.getSession().getType() == Session.Role.PROFESSOR) {
					lessons = LessonDAO.getNextLessonsProfessor(date, time, Session.getSession().getUserLogged().getUsername());
				}
				else {
					return;
				}
				
				for (Lesson lesson : lessons) {
					LessonCard lessonCard = new LessonCard(SQLConverter.date(lesson.getDate()), lesson.getClassroom().getName(), lesson.getCourse().getAbbrevation(), SQLConverter.time(lesson.getTime()));
					vboxScroll.getChildren().add(lessonCard);
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			labelPage.setText("Exams");
			
			for (int i=0; i<10; i++) {
				try {
					ScheduledExamCard scheduledExamCard = new ScheduledExamCard("03/12/2020", "ISPW", "z555", "37:96");
					vboxScroll.getChildren().add(scheduledExamCard);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		try {
			List<Course> courses = CourseDAO.getProfessorCourses(Session.getSession().getUserLogged().getUsername());
			for (Course course : courses) {
				CourseFilterCard courseFilterCard = new CourseFilterCard(course.getAbbrevation());
				vboxCourse.getChildren().add(courseFilterCard);

			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
