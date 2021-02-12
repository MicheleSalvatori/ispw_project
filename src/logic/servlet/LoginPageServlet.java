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

	private static String alertString = "An error as occured. Try later.";
	private static String alertAttribute = "alertMsg";
	private static String loginPageUrl = "/WEB-INF/LoginPage.jsp";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(loginPageUrl).forward(request, response);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
		
			if (req.getParameter("login") != null) {
				
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				
				if (username.isEmpty() || password.isEmpty()) {
					req.setAttribute(alertAttribute, "One or more fields are empty.");
					req.getRequestDispatcher(loginPageUrl).include(req, resp);
					return;
				}
				
				UserBean userBean = new UserBean();
		    	userBean.setUsername(username);
		        userBean.setPassword(password);
		        LoginController controller = new LoginController();
	
				userBean = controller.login(userBean);
	
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
	
				checkInput(email);
					
				UserBean userBean = new UserBean();
				userBean.setEmail(email);
				
				String password = controller.getUserByEmail(userBean).getPassword();
				Email.password(email, password);
	
				req.setAttribute(alertAttribute, "Password send by email.");
				req.getRequestDispatcher(loginPageUrl).include(req, resp);
			}
			
		} catch (SQLException e) {
			req.setAttribute(alertAttribute, alertString);
			req.getRequestDispatcher(loginPageUrl).include(req, resp);

		} catch (RecordNotFoundException | InvalidInputException e) {
			req.setAttribute("alertMsg", e.getMessage());
			req.getRequestDispatcher(loginPageUrl).include(req, resp);
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
