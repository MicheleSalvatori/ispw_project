package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.CheckWeatherController;
import logic.controller.ViewNextLessonController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loggedUser") == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");

		ViewNextLessonController viewNextLessonController = new ViewNextLessonController();
		List<LessonBean> lessons = null;
		LessonBean lesson = null;
		
		try {
			lessons = viewNextLessonController.getTodayLessons(loggedUser);
			lesson = lessons.get(0);
			lessons.remove(0);
			
		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;
		
		} catch (RecordNotFoundException e) {
			lesson = null;
			lessons = new ArrayList<>();
		}
		
		int hour = LocalDateTime.now().getHour();
		CheckWeatherController controller = new CheckWeatherController();
		
		List<List<String>> weather = new ArrayList<>();
		for (int i=0; i<5; i++) {
			List<String> info = controller.getWeather(hour+i);
			weather.add(info);
		}
		
		request.setAttribute("lesson", lesson);
		request.setAttribute("listOfLesson", lessons);
		request.setAttribute("listOfWeather", weather);
		request.getRequestDispatcher("/WEB-INF/HomePage.jsp").forward(request, response);
	}
}