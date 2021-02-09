package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.AskAQuestionController;

@WebServlet("/NewQuestionPageServlet")
public class NewQuestionPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if (session.getAttribute("loggedUser") == null) {
	        resp.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		AskAQuestionController controller = new AskAQuestionController();
		try {
			List<CourseBean> courses = controller.getCoursesOfStudent((UserBean) session.getAttribute("loggedUser"));
			req.setAttribute("listOfCourses", courses);
			
		} catch (SQLException e) {
			req.setAttribute("alertMsg", "An error as occured. Try later.");
			req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(req, resp);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/NewQuestionPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AskAQuestionController controller = new AskAQuestionController();
		QuestionBean newQuestion = new QuestionBean();
		StudentBean student = new StudentBean();
		student.setUsername(((UserBean) req.getSession().getAttribute("loggedUser")).getUsername());
		newQuestion.setCourse(req.getParameter("courses"));
		newQuestion.setStudent(student);
		newQuestion.setText(req.getParameter("question-text"));
		newQuestion.setTitle(req.getParameter("question-subject"));
		
		try {
			controller.save(newQuestion);
			
		} catch (SQLException e) {
			req.setAttribute("alertMsg", "An error as occured. Try later.");
			req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(req, resp);
			return;
		}
		
		resp.sendRedirect("/ispw_project/ForumPageServlet");
	}
}
