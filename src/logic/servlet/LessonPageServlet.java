package logic.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.bean.UserBean;
import logic.controller.BookASeatController;
import logic.controller.CheckWeatherController;
import logic.controller.ViewNextLessonController;
import logic.exceptions.SeatAlreadyBookedException;

@WebServlet("/LessonPageServlet")
public class LessonPageServlet extends HttpServlet {

	private static String alertString = "An error as occured. Try later.";
	private static String alertAttribute = "alertMsg";
	private static String loggedAttribute = "loggedUser";
	private static String loginPageUrl = "/WEB-INF/LoginPage.jsp";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (req.getSession().getAttribute(loggedAttribute) == null) {
	        resp.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		String course = req.getParameter("lessonCourse");
		String date = req.getParameter("lessonDate");
		String time = req.getParameter("lessonTime");
		
		LessonBean l = new LessonBean();
		l.setCourse(course);
		l.setDate(Date.valueOf(date));
		l.setTime(Time.valueOf(time));
		
		ViewNextLessonController lessonController = new ViewNextLessonController();
		BookASeatController seatController = new BookASeatController();
		UserBean user = (UserBean) req.getSession().getAttribute(loggedAttribute);
		
		try {
			LessonBean lesson = lessonController.getLesson(l);
			List<SeatBean> occupiedSeats = seatController.getOccupateSeatOf(lesson);
			SeatBean mySeat = seatController.getMySeat(lesson, user);
			
			CheckWeatherController controllerWeather = new CheckWeatherController();
			List<String> weather = controllerWeather.getWeather(Integer.parseInt(lesson.getTime().toString().substring(0, 2)));
			
			req.setAttribute("lesson", lesson);
			req.setAttribute("occupiedSeats", occupiedSeats);
			req.setAttribute("mySeat", mySeat);
			req.setAttribute("weather", weather);
			
		} catch (SQLException e) {
			req.setAttribute(alertAttribute, alertString);
			req.getRequestDispatcher(loginPageUrl).forward(req, resp);
			return;	
		}
		
		req.getRequestDispatcher("/WEB-INF/LessonPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String coursePost = req.getParameter("lessonCourse");
		String datePost = req.getParameter("lessonDate");
		String timePost = req.getParameter("lessonTime");
		
		LessonBean lessonBean = new LessonBean();
		lessonBean.setCourse(coursePost);
		lessonBean.setDate(Date.valueOf(datePost));
		lessonBean.setTime(Time.valueOf(timePost));
		
		ViewNextLessonController lessonController = new ViewNextLessonController();
		BookASeatController seatController = new BookASeatController();
		UserBean user = (UserBean) req.getSession().getAttribute(loggedAttribute);
		
		if (req.getParameter("bookSeat")!=null) {
			seatController = new BookASeatController();
			try {
				LessonBean lesson = lessonController.getLesson(lessonBean);
				SeatBean seat = new SeatBean(Integer.valueOf(req.getParameter("bookSeat")));
				seatController.occupateSeat(seat, lesson, user);
				
			} catch (SQLException e) {
				req.setAttribute(alertAttribute, alertString);
				req.getRequestDispatcher(loginPageUrl).forward(req, resp);
				return;
				
			} catch (SeatAlreadyBookedException e) {
				req.setAttribute(alertAttribute, alertString);
				req.getRequestDispatcher("/WEB-INF/LessonPage.jsp").include(req, resp);
				return;
			}
		}
		
		if (req.getParameter("yourSeat")!=null) {
			try {
				LessonBean lesson = lessonController.getLesson(lessonBean);
				SeatBean seat = new SeatBean(Integer.parseInt(req.getParameter("yourSeat")));
				seatController.freeSeat(seat, lesson, user);
				
			} catch (SQLException e) {
				req.setAttribute(alertAttribute, alertString);
				req.getRequestDispatcher(loginPageUrl).forward(req, resp);
				return;
			}
		}
		
		resp.sendRedirect("/ispw_project/LessonPageServlet?viewLesson=&lessonCourse=" + coursePost + "&lessonDate=" + datePost + "&lessonTime=" + timePost);
	}
}
