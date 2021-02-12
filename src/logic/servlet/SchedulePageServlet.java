package logic.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.ScheduleController;
import logic.controller.ScheduleExamController;
import logic.controller.ScheduleLessonController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/SchedulePageServlet")
public class SchedulePageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userLogged = (UserBean) session.getAttribute("loggedUser");
	
		if (userLogged == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		List<ClassroomBean> classrooms = null;
		List<CourseBean> courses = null;
		
		ScheduleController controller = new ScheduleController();
		
		try {
			classrooms = controller.getClassrooms();
			courses = controller.getCourses(userLogged);
			
		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;
			
		} catch (RecordNotFoundException e) {
			courses = new ArrayList<>();
		}
		
		request.setAttribute("listOfClass", classrooms);
		request.setAttribute("listOfCourse", courses);
		request.getRequestDispatcher("/WEB-INF/SchedulePage.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getParameter("btnAddLesson") != null) {
			String topic = request.getParameter("topic");
			String course = request.getParameter("selCourseLesson");
			String classroom = request.getParameter("selClassLesson");
			String date = request.getParameter("dateLesson");
			String time = request.getParameter("timeLesson");
			
			ScheduleLessonController controller = new ScheduleLessonController();
			
			LessonBean lessonBean = new LessonBean();
			Date d = Date.valueOf(date);
			Time t = Time.valueOf(time + ":00");
			
			ClassroomBean cl = new ClassroomBean();
			cl.setName(classroom);
			
			UserBean p = new UserBean();
			p.setUsername(((UserBean) session.getAttribute("loggedUser")).getUsername());
			
			lessonBean.setTopic(topic);
			lessonBean.setDate(d);		
			lessonBean.setTime(t);	
			lessonBean.setCourse(course);	
			lessonBean.setClassroom(cl);
			lessonBean.setProfessor(p);
			
			controller.scheduleLesson(lessonBean);
			session.setAttribute("alert", "Lesson added correctly.");
		}
		
		else if (request.getParameter("btnAddExam") != null) {
			String note = request.getParameter("note");
			String course = request.getParameter("selCourseExam");
			String classroom = request.getParameter("selClassExam");
			String date = request.getParameter("dateExam");
			String time = request.getParameter("timeExam");
			
			ScheduleExamController controller = new ScheduleExamController();
			
			Date d = Date.valueOf(date);
			Time t = Time.valueOf(time + ":00");
			
			ExamBean examBean = new ExamBean();
			examBean.setNote(note);	
			examBean.setDate(d);	
			examBean.setTime(t);	
			examBean.setCourse(course);
			examBean.setClassroom(classroom);
			controller.scheduleExam(examBean);
			
			session.setAttribute("alert", "Exam added correctly.");
		}
		
		response.sendRedirect("/ispw_project/SchedulePageServlet");
	}
}
