package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.bean.CommunicationBean;
import logic.controller.PostCommunicationController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/CommunicationsPageServlet")
public class CommunicationsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PostCommunicationController controller = new PostCommunicationController();
		List<CommunicationBean> communications = null;
		try {
			communications = controller.getCommunications();

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (RecordNotFoundException e) {
		}

		request.setAttribute("listOfCommunications", communications);
		request.getRequestDispatcher("/WEB-INF/CommunicationsPage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
