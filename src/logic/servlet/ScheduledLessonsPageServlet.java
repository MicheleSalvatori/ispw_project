package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.ScheduledController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/ScheduledLessonsPageServlet")
public class ScheduledLessonsPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userLogged = (UserBean) session.getAttribute("loggedUser");
		
		if (userLogged== null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		List<LessonBean> lessons = null;
		List<CourseBean> courses = null;
		
		ScheduledController controller = new ScheduledController();
		
		try {
			lessons = controller.getLessons(userLogged);
		
		} catch (SQLException e) {
			alert(request, response);
			return;
		
		} catch (RecordNotFoundException e) {
			lessons = new ArrayList<>();
		}
		
		try {
			// Get user courses
			courses = controller.getCourses(userLogged);
			
		} catch (RecordNotFoundException e) {
			courses = new ArrayList<>();
			
		} catch (SQLException e) {
			alert(request, response);
			return;
		}
		
		request.setAttribute("listOfLesson", lessons);
		request.setAttribute("listOfCourse", courses);
		request.getRequestDispatcher("/WEB-INF/ScheduledLessonsPage.jsp").forward(request, response);
	}
	
	private void alert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("alertMsg", "An error as occured. Try later.");
		req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(req, resp);
	}
}
