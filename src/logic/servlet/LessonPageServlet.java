package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.bean.ClassroomBean;
import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.bean.UserBean;
import logic.controller.BookSeatController;
import logic.exceptions.DuplicatedRecordException;

@WebServlet("/LessonPageServlet")
public class LessonPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LessonBean lesson = (LessonBean) req.getSession().getAttribute("lesson");
//		req.getSession().setAttribute("lesson", null);
		req.setAttribute("lesson", lesson);
		UserBean user = (UserBean) req.getSession().getAttribute("loggedUser");
		BookSeatController controller = new BookSeatController();
		try {
			controller.getMySeat(lesson, user);
			List<SeatBean> occupiedSeats = controller.getOccupateSeatOf(lesson);
			SeatBean mySeat = controller.getMySeat(lesson, user);
			req.setAttribute("occupiedSeats", occupiedSeats);
			req.setAttribute("mySeat", mySeat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/LessonPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("loggedUser");
		LessonBean lesson = (LessonBean) req.getSession().getAttribute("lesson");
		
		if (req.getParameter("bookSeat")!=null) {
			BookSeatController controller = new BookSeatController();
			try {
				SeatBean seat = new SeatBean(Integer.valueOf(req.getParameter("bookSeat")));
				controller.occupateSeat(seat, lesson, user);
			} catch (SQLException | DuplicatedRecordException e) {
				e.printStackTrace();
			}
		}
		
		if (req.getParameter("yourSeat")!=null) {
			BookSeatController controller = new BookSeatController();
			try {
				SeatBean seat = new SeatBean(Integer.valueOf(req.getParameter("yourSeat")));
				controller.freeSeat(seat, lesson, user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		resp.sendRedirect("/ispw_project/LessonPageServlet");
	}

}
