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

import logic.bean.CourseBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.bean.VerbalizedBean;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/ExamPageServlet")
public class ExamPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getSession().getAttribute("loggedUser") == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		ViewVerbalizedExamsController controller = new ViewVerbalizedExamsController();
		
		List<VerbalizedBean> exams = null;
		List<CourseBean> courses = null;
		double GPA = 0;
		double WPA = 0;
		
		try {
			exams = controller.getVerbalizedExams((UserBean) session.getAttribute("loggedUser"));
			GPA = controller.GPA(exams);
			WPA = controller.WPA(exams);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			exams = null;
		}
		
		try {
			courses = controller.getCourses((UserBean) session.getAttribute("loggedUser"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// niente
		}
		
		request.setAttribute("gpa", GPA);
		request.setAttribute("wpa", WPA);
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
			studentBean.setEmail(((UserBean) session.getAttribute("loggedUser")).getEmail());
			studentBean.setName(((UserBean) session.getAttribute("loggedUser")).getName());
			studentBean.setPassword(((UserBean) session.getAttribute("loggedUser")).getPassword());
			studentBean.setSurname(((UserBean) session.getAttribute("loggedUser")).getSurname());
			studentBean.setUsername(((UserBean) session.getAttribute("loggedUser")).getUsername());
		
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
			studentBean.setUsername(((UserBean) session.getAttribute("loggedUser")).getUsername());
			
			VerbalizedBean verb = new VerbalizedBean();
			verb.setCourse(courseBean);
			verb.setStudent(studentBean);
			
			controller.deleteVerbalizedExam(verb);
		}
		
		response.sendRedirect("/ispw_project/ExamPageServlet");
	}
}
