package logic.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CommunicationBean;
import logic.controller.PostCommunicationController;

@WebServlet("/PostCommunicationServlet")
public class PostCommunicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loggedUser") == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		request.getRequestDispatcher("/WEB-INF/PostCommunicationPage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PostCommunicationController controller = new PostCommunicationController();
		CommunicationBean communication = new CommunicationBean();
		communication.setText(req.getParameter("communication-text"));
		communication.setTitle(req.getParameter("communication-title"));
		communication.setDate(new Date(System.currentTimeMillis()));
		
		try {
			controller.postCommunication(communication);
			
		} catch (SQLException e) {
			req.setAttribute("alertMsg", "An error as occured. Try later.");
			req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(req, resp);
			return;
		}
		
		resp.sendRedirect("/ispw_project/AdministrationPageServlet");
	}
}
