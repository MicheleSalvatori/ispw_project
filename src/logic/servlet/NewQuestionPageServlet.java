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
import logic.controller.InsertQuestionController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/NewQuestionPageServlet")
public class NewQuestionPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		InsertQuestionController controller = new InsertQuestionController();
		try {
			List<CourseBean> courses = controller.getCoursesOfStudent((UserBean) session.getAttribute("loggedUser"));
			req.setAttribute("listOfCourses", courses);

		} catch (RecordNotFoundException e) {
			// TODO se non ci sono corsi significa che non sei iscritto in nessun corso e
			// quindi già nella forum page non hai accesso al tasto new question (nell'app
			// desktop è già implmentato)
		}

		req.getRequestDispatcher("/WEB-INF/NewQuestionPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("courses"));
		System.out.println(req.getParameter("question-subject"));
		System.out.println(req.getParameter("question-text"));
		InsertQuestionController controller = new InsertQuestionController();
		QuestionBean newQuestion = new QuestionBean();
//		CourseBean course = new CourseBean();
		StudentBean student = new StudentBean();
		student.setUsername(((UserBean) req.getSession().getAttribute("loggedUser")).getUsername());
//		course.setAbbreviation(req.getParameter("courses"));
		newQuestion.setCourse(req.getParameter("courses"));
		newQuestion.setStudent(student);
		newQuestion.setText(req.getParameter("question-text"));
		newQuestion.setTitle(req.getParameter("question-subject"));
		try {
			controller.save(newQuestion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(student.getUsername());
		resp.sendRedirect("/ispw_project/ForumPageServlet");
	}
}
