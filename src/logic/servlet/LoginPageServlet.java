package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.InvalidInputException;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Email;
import logic.utilities.Role;

@WebServlet("/LoginServlet")
public class LoginPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("login") != null) {
			
			UserBean userBean = new UserBean();
	    	userBean.setUsername(req.getParameter("username"));
	        userBean.setPassword(req.getParameter("password"));
	        LoginController controller = new LoginController();
	        
			try {
				userBean = controller.login(userBean);
				
			} catch (SQLException e) {
				req.setAttribute("alertMsg", "An error as occured. Try later.");
				req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").include(req, resp);
				return;
				
			} catch (RecordNotFoundException e) {
	            req.setAttribute("alertMsg", "Not valid login credentials.");
				req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").include(req, resp);
				return;
			}

	        HttpSession session = req.getSession();
	        session.setAttribute("loggedUser", userBean);
	        
	        if (userBean.getRole() == Role.ADMIN) {
				resp.sendRedirect(req.getContextPath() + "/AdministrationPageServlet");
			}
	        else {
				resp.sendRedirect(req.getContextPath() + "/HomePageServlet");
			}
		}
		
		else if (req.getParameter("forgotPassword") != null) {
			
			LoginController controller = new LoginController();
			
			String email = req.getParameter("email");
			
			try {
				checkInput(email);
				
				UserBean userBean = new UserBean();
				userBean.setEmail(email);
				
				String password = controller.getUserByEmail(userBean).getPassword();
				Email.password(email, password);

			} catch (SQLException e) {
				req.setAttribute("alertMsg", "Connection failed!");
				req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").include(req, resp);
				return;

			} catch (RecordNotFoundException | InvalidInputException e) {
				req.setAttribute("alertMsg", e.getMessage());
				req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").include(req, resp);
				return;
			}

			resp.sendRedirect(req.getContextPath() + "/LoginServlet");
		}
    }
	
	private void checkInput(String email) throws InvalidInputException {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			
		} catch (AddressException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
}
