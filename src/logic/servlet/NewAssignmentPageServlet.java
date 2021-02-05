package logic.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.AssignmentBean;
import logic.bean.CourseBean;
import logic.bean.UserBean;
import logic.controller.AddAssignmentController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/NewAssignmentPageServlet")
public class NewAssignmentPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if (session.getAttribute("loggedUser") == null) {
	        resp.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		AddAssignmentController controller = new AddAssignmentController();
		List<CourseBean> courses = null;
		try {
			courses = controller.getCoursesOfProfessor((UserBean) session.getAttribute("loggedUser"));
		
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		req.setAttribute("listOfCourses", courses);
		req.getRequestDispatcher("/WEB-INF/NewAssignmentPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AddAssignmentController controller = new AddAssignmentController();
		AssignmentBean assignmentBean = new AssignmentBean();
		assignmentBean.setCourse(req.getParameter("assignment-course"));
		assignmentBean.setDate(Date.valueOf(req.getParameter("assignment-date")));
		assignmentBean.setText(req.getParameter("assignment-text"));
		assignmentBean.setTitle(req.getParameter("assignment-title"));
		
		try {
			controller.saveAssignment(assignmentBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.sendRedirect("/ispw_project/ForumPageServlet");
	}
}
