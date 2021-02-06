package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CommunicationBean;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.controller.PostCommunicationController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

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

		HttpSession session = request.getSession();

		PostCommunicationController controller = new PostCommunicationController();
		List<CommunicationBean> communications = null;
		try {
			communications = controller.getCommunications();

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (RecordNotFoundException e) {
		}

		request.setAttribute("listOfCommunications", communications);
		request.getRequestDispatcher("/WEB-INF/AdministrationPage.jsp").forward(request, response);
	}
	
}
