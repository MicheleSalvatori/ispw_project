package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.InvalidInputException;

@WebServlet("/SignupServlet")
public class SignupPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/SignupPage.jsp").forward(request, response);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		
		try {
			checkInput(name, surname, email, password, confirmPassword);
		
		} catch (InvalidInputException e) {
			alert(req, resp, e.getMessage(), "/WEB-INF/SignupPage.jsp");
			return;
		}
		
    	UserBean userBean = new UserBean();
    	userBean.setUsername(req.getParameter("username"));
        userBean.setPassword(req.getParameter("password"));
        userBean.setEmail(req.getParameter("email"));
        userBean.setName(req.getParameter("name"));
        userBean.setSurname(req.getParameter("surname"));
        LoginController controller = new LoginController();
        
		try {
			controller.signup(userBean);
			
		} catch (SQLException e) {
			req.setAttribute("alertMsg", "An error as occured. Try later.");
			req.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(req, resp);
			return;
			
		} catch (DuplicatedRecordException e) {
			alert(req, resp, e.getMessage(), "/WEB-INF/SignupPage.jsp");
			return;
		}

		alert(req, resp, "Registration completed!\nYou will'be redirect to the login page.", "/WEB-INF/LoginPage.jsp");
    }
	
	private void alert(HttpServletRequest req, HttpServletResponse resp, String msg, String page) throws ServletException, IOException {
		req.setAttribute("alertMsg", msg);
		RequestDispatcher rd = req.getRequestDispatcher(page);
        rd.include(req, resp);
	}
	
	private void checkInput(String name, String surname, String email, String password, String confirmPassword) throws InvalidInputException {

		Pattern p = Pattern.compile("[^A-Za-z]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name + surname);

		// Check if email is valid and if passwords are the same
		if (!password.equals(confirmPassword)) {
			throw new InvalidInputException("Please make sure your passwords match.");
		}
		
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			
		} catch (AddressException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		// Check if name and surname are valid
		if (m.find()) {
			throw new InvalidInputException("The name and surname must contain alpha characters only.");
		}
	}
}
