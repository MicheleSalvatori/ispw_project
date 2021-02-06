package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

@WebServlet("/LoginServlet")
public class LoginPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	UserBean userBean = new UserBean();
    	userBean.setUsername(req.getParameter("username"));
        userBean.setPassword(req.getParameter("password"));
        LoginController controller = new LoginController();
        
		try {
			userBean = controller.login(userBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			req.setAttribute("alertMsg", "Not valid login credentials.");
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/LoginPage.jsp");
            rd.include(req, resp);
            return;
		}

        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", userBean);
        if (userBean.getRole() == Role.ADMIN) {
			resp.sendRedirect(req.getContextPath() + "/AdministrationPageServlet");
		} else {
			resp.sendRedirect(req.getContextPath() + "/HomePageServlet");
		}
    }
}
