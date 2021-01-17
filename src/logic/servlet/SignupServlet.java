package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.bean.UserBean;
import logic.controller.SignupController;
import logic.exceptions.DuplicatedRecordException;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		if (!password.equals(confirmPassword)) {
			alert(req, resp, "Passwords doesn't match", "/SignupPage.jsp");
			return;
		}
		
		String email = req.getParameter("email");
		if (!email.contains("@") || !email.contains(".com") || !email.contains(".it")) {
			alert(req, resp, "Invalid Email", "/SignupPage.jsp");
			return;
		}
		
    	UserBean userBean = new UserBean();
    	userBean.setUsername(req.getParameter("username"));
        userBean.setPassword(req.getParameter("password"));
        userBean.setEmail(req.getParameter("email"));
        userBean.setName(req.getParameter("name"));
        userBean.setSurname(req.getParameter("surname"));
        SignupController controller = new SignupController();
        
		try {
			controller.signup(userBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (DuplicatedRecordException e) {
			alert(req, resp, e.getMessage(), "/SignupPage.jsp");
			return;
		}

		alert(req, resp, "Registration completed!\nYou will'be redirect to the login page.", "/LoginPage.jsp");
    }
	
	private void alert(HttpServletRequest req, HttpServletResponse resp, String msg, String page) throws ServletException, IOException {
		req.setAttribute("alertMsg", msg);
		RequestDispatcher rd = req.getRequestDispatcher(page);
        rd.include(req, resp);
	}
}
