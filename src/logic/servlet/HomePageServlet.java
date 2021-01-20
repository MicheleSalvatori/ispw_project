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

import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.ViewNextLessonController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/NextLessonsServlet")
public class HomePageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*if (request.getSession().getAttribute("loggedUser") == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}*/
		
		HttpSession session = request.getSession(false);

		ViewNextLessonController viewNextLessonController = new ViewNextLessonController();
		try {
			List<LessonBean> lessons = viewNextLessonController.getTodayLessons((UserBean) session.getAttribute("loggedUser"));
			LessonBean lesson = lessons.get(0);
			lessons.remove(0);
			request.setAttribute("lesson", lesson);
			request.setAttribute("listOfLesson", lessons);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();					// TODO Not found show
			//return;
		}
		
		request.getRequestDispatcher("/WEB-INF/HomePage.jsp").forward(request, response);
	}
}
