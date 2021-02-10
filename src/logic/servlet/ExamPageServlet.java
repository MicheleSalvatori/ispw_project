package logic.servlet;

import java.io.IOException;
import java.sql.Date;
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
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.bean.VerbalizedBean;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/ExamPageServlet")
public class ExamPageServlet extends HttpServlet {
	
	private static String loggedAttribute = "loggedUser";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute(loggedAttribute) == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		ViewVerbalizedExamsController controller = new ViewVerbalizedExamsController();
		
		List<VerbalizedBean> exams = null;
		List<CourseBean> courses = null;
		double gpa = 0;
		double wpa = 0;
		
		try {
			exams = controller.getVerbalizedExams((UserBean) session.getAttribute(loggedAttribute));
			gpa = controller.gpa(exams);
			wpa = controller.wpa(exams);
			
		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;
			
		} catch (RecordNotFoundException e) {
			exams = new ArrayList<>();
		}
		
		try {
			courses = controller.getCourses((UserBean) session.getAttribute(loggedAttribute));
			
		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;
			
		} catch (RecordNotFoundException e) {
			courses = new ArrayList<>();
		}
		
		request.setAttribute("gpa", gpa);
		request.setAttribute("wpa", wpa);
		request.setAttribute("listOfExam", exams);
		request.setAttribute("listOfCourse", courses);
		request.getRequestDispatcher("/WEB-INF/ExamPage.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getParameter("submitAdd") != null) {
			ViewVerbalizedExamsController controller = new ViewVerbalizedExamsController();
			
			String course = request.getParameter("course-select");
			String date = request.getParameter("date");
			String grade = request.getParameter("grade-select");
			
			StudentBean studentBean = new StudentBean();
			UserBean loggedUser = (UserBean) session.getAttribute(loggedAttribute);
			studentBean.setEmail(loggedUser.getEmail());
			studentBean.setName(loggedUser.getName());
			studentBean.setPassword(loggedUser.getPassword());
			studentBean.setSurname(loggedUser.getSurname());
			studentBean.setUsername(loggedUser.getUsername());
		
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course);
			
			VerbalizedBean verb = new VerbalizedBean();
			verb.setCourse(courseBean);
			verb.setDate(Date.valueOf(date));
			verb.setGrade(Integer.valueOf(grade));
			verb.setStudent(studentBean);
			
			controller.saveVerbalizedExam(verb);
		}
		
		else if (request.getParameter("deleteExam") != null){
			ViewVerbalizedExamsController controller = new ViewVerbalizedExamsController();
			String course = request.getParameter("course");
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course);
			
			StudentBean studentBean = new StudentBean();
			studentBean.setUsername(((UserBean) session.getAttribute(loggedAttribute)).getUsername());
			
			VerbalizedBean verb = new VerbalizedBean();
			verb.setCourse(courseBean);
			verb.setStudent(studentBean);
			
			controller.deleteVerbalizedExam(verb);
		}
		
		response.sendRedirect("/ispw_project/ExamPageServlet");
	}
}
