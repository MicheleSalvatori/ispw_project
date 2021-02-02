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
import logic.controller.BookSeatController;

@WebServlet("/LessonPageServlet")
public class LessonPageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LessonBean lesson = (LessonBean) req.getSession().getAttribute("lesson");
//		req.getSession().setAttribute("lesson", null);
		req.setAttribute("lesson", lesson);
		BookSeatController controller = new BookSeatController();
		try {
//			controller.getMySeat(lesson);
			List<SeatBean> occupiedSeats = controller.getOccupateSeatOf(lesson);
			System.out.println("ID: " +occupiedSeats.get(0).getId() + " "+occupiedSeats.get(0).isFree());
			System.out.println("ID: " +occupiedSeats.get(1).getId() + " "+occupiedSeats.get(1).isFree());
			System.out.println("ID: " +occupiedSeats.get(10).getId() + " "+occupiedSeats.get(1).isFree());
			req.setAttribute("occupiedSeats", occupiedSeats);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/LessonPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
