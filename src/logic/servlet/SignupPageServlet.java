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
import logic.controller.LoginController;
import logic.exceptions.DuplicatedRecordException;

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
		
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		if (!password.equals(confirmPassword)) {
			alert(req, resp, "Passwords doesn't match", "/WEB-INF/SignupPage.jsp");
			return;
		}
		
		String email = req.getParameter("email");
		if (!email.contains("@") || !email.contains(".com") || !email.contains(".it")) {
			alert(req, resp, "Invalid Email", "/WEB-INF/SignupPage.jsp");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			
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
}
