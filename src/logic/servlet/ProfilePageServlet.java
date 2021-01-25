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
import logic.bean.ProfessorBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.JoinCourseController;
import logic.controller.SignupController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/ProfilePageServlet")
public class ProfilePageServlet extends HttpServlet {

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
		
		JoinCourseController controller = new JoinCourseController();
		
		List<CourseBean> courses = null;
		List<CourseBean> requests = null;
		List<CourseBean> available = null;
		List<ProfessorBean> professors = new ArrayList<>();
		
		try {
			courses = controller.getCourses((UserBean) session.getAttribute("loggedUser"));
			for (CourseBean course : courses) {
				professors.add(controller.getCourseProfessors(course).get(0));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			//
		}
		
		try {
			requests = controller.getRequestedCourses((UserBean) session.getAttribute("loggedUser"));
			for (CourseBean courseRequest : requests) {
				professors.add(controller.getCourseProfessors(courseRequest).get(0));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		try {
			available = controller.getAvailableCourses((UserBean) session.getAttribute("loggedUser"));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		request.setAttribute("listOfCourse", courses);
		request.setAttribute("listOfRequest", requests);
		request.setAttribute("listOfProfessor", professors);
		request.setAttribute("listOfAvailable", available);
		request.getRequestDispatcher("/WEB-INF/ProfilePage.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JoinCourseController controller = new JoinCourseController();
		
		if (request.getParameter("submitAdd") != null) {
			
			String course = request.getParameter("course-select");
			
			StudentBean studentBean = new StudentBean();
			studentBean.setEmail(((UserBean) session.getAttribute("loggedUser")).getEmail());
			studentBean.setName(((UserBean) session.getAttribute("loggedUser")).getName());
			studentBean.setPassword(((UserBean) session.getAttribute("loggedUser")).getPassword());
			studentBean.setSurname(((UserBean) session.getAttribute("loggedUser")).getSurname());
			studentBean.setUsername(((UserBean) session.getAttribute("loggedUser")).getUsername());
			
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course);
			
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent(studentBean);
			requestBean.setCourse(courseBean);

			controller.sendRequest(requestBean);
		}
		
		else if (request.getParameter("submitRemove") != null) {
			
			String course = request.getParameter("course-select");
			
			StudentBean studentBean = new StudentBean();
			studentBean.setEmail(((UserBean) session.getAttribute("loggedUser")).getEmail());
			studentBean.setName(((UserBean) session.getAttribute("loggedUser")).getName());
			studentBean.setPassword(((UserBean) session.getAttribute("loggedUser")).getPassword());
			studentBean.setSurname(((UserBean) session.getAttribute("loggedUser")).getSurname());
			studentBean.setUsername(((UserBean) session.getAttribute("loggedUser")).getUsername());
			
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course);
			
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent(studentBean);
			requestBean.setCourse(courseBean);

			controller.removeCourse(requestBean);
		}
		
		else if (request.getParameter("deleteRequest") != null) {
			
			String course = request.getParameter("course");
			
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course);
			
			StudentBean studentBean = new StudentBean();
			studentBean.setUsername(((UserBean) session.getAttribute("loggedUser")).getUsername());
		
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent(studentBean);
			requestBean.setCourse(courseBean);
			
			controller.deleteRequest(requestBean);
		}
		
		else if (request.getParameter("changePassword") != null) {
			
			String password = request.getParameter("password");
			if (!password.isEmpty()) {

				if (password.compareTo(((UserBean) session.getAttribute("loggedUser")).getPassword()) == 0) {
					session.setAttribute("error", "You inserted your current password.");
				}
				
				else {
					
					UserBean userBean = (UserBean) session.getAttribute("loggedUser");
					userBean.setPassword(password);
					
					SignupController signupController = new SignupController();
					try {
						signupController.changePassword(userBean);
						session.invalidate();
						session.setAttribute("alertMsg", "You will be redirected to Login page.");
						response.sendRedirect("/ispw_project/LoginServlet");
						return;
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (RecordNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
		}
		
		response.sendRedirect("/ispw_project/ProfilePageServlet");
	}
}
