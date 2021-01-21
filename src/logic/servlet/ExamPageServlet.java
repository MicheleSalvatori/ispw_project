package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.bean.VerbalizedBean;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/ExamPageServlet")
public class ExamPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		ViewVerbalizedExamsController controller = new ViewVerbalizedExamsController();
		
		try {
			List<VerbalizedBean> exams = controller.getVerbalizedExams((UserBean) session.getAttribute("loggedUser"));
			session.setAttribute("listOfExam", exams);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/WEB-INF/ExamPage.jsp").forward(request, response);	
	}
}
