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

import logic.bean.CommunicationBean;
import logic.controller.PostCommunicationController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/AdministrationPageServlet")
public class AdministrationPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("loggedUser") == null) {
			response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
			return;
		}

		PostCommunicationController controller = new PostCommunicationController();
		List<CommunicationBean> communications = null;
		try {
			communications = controller.getCommunications();

		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;

		} catch (RecordNotFoundException e) {
			communications = new ArrayList<>();
		}

		request.setAttribute("listOfCommunications", communications);
		request.getRequestDispatcher("/WEB-INF/AdministrationPage.jsp").forward(request, response);
	}

}
