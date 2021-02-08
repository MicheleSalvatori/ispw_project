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

import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.bean.UserBean;
import logic.controller.BookSeatController;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/LessonPageServlet")
public class LessonPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (req.getSession().getAttribute("loggedUser") == null) {
	        resp.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		String course = req.getParameter("lessonCourse");
		String date = req.getParameter("lessonDate");
		String time = req.getParameter("lessonTime");
		
		CourseBean c = new CourseBean();
		c.setAbbreviation(course);
		
		LessonBean l = new LessonBean();
		l.setCourse(c);
		l.setDate(Date.valueOf(date));
		l.setTime(Time.valueOf(time));
		
		BookSeatController controller = new BookSeatController();
		UserBean user = (UserBean) req.getSession().getAttribute("loggedUser");
		
		try {
			LessonBean lesson = controller.getLesson(l);
			List<SeatBean> occupiedSeats = controller.getOccupateSeatOf(lesson);
			System.out.println(occupiedSeats.get(0).isFree());
			SeatBean mySeat = controller.getMySeat(lesson, user);
			
			req.setAttribute("lesson", lesson);
			req.setAttribute("occupiedSeats", occupiedSeats);
			req.setAttribute("mySeat", mySeat);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (RecordNotFoundException e1) {
			// TODO
		}
		
		req.getRequestDispatcher("/WEB-INF/LessonPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String course = req.getParameter("lessonCourse");
		String date = req.getParameter("lessonDate");
		String time = req.getParameter("lessonTime");
		
		CourseBean c = new CourseBean();
		c.setAbbreviation(course);
		
		LessonBean l = new LessonBean();
		l.setCourse(c);
		l.setDate(Date.valueOf(date));
		l.setTime(Time.valueOf(time));
		
		BookSeatController controller = new BookSeatController();
		UserBean user = (UserBean) req.getSession().getAttribute("loggedUser");
		
		if (req.getParameter("bookSeat")!=null) {
			controller = new BookSeatController();
			try {
				LessonBean lesson = controller.getLesson(l);
				SeatBean seat = new SeatBean(Integer.valueOf(req.getParameter("bookSeat")));
				controller.occupateSeat(seat, lesson, user);
				
				// TODO POSTO GIA OCCUPATO EXCEPTION
				
			} catch (SQLException | DuplicatedRecordException e) {
				e.printStackTrace();
				
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (req.getParameter("yourSeat")!=null) {
			try {
				LessonBean lesson = controller.getLesson(l);
				SeatBean seat = new SeatBean(Integer.valueOf(req.getParameter("yourSeat")));
				controller.freeSeat(seat, lesson, user);
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		resp.sendRedirect("/ispw_project/LessonPageServlet?viewLesson=&lessonCourse=" + course + "&lessonDate=" + date + "&lessonTime=" + time);
	}
}
