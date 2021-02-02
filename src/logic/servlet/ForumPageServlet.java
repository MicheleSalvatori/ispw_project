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

import logic.bean.AssignmentBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.AddAssignmentController;
import logic.controller.AllQuestionController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/ForumPageServlet")
public class ForumPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		AllQuestionController controller = new AllQuestionController();
		List<QuestionBean> questions = null;
		try {
			questions = controller.getAllQuestions((UserBean) session.getAttribute("loggedUser"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("listOfQuestion", questions);

		AddAssignmentController assignmentController = new AddAssignmentController();
		List<AssignmentBean> assignments = null;
		try {
			assignments = assignmentController.getAssignments((UserBean) session.getAttribute("loggedUser"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		request.setAttribute("listOfAssignment", assignments);
		request.getRequestDispatcher("/WEB-INF/ForumPage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.print("doPost ForumServlet: ");
		System.out.println(req.getParameter("set-solved"));
		
		if (req.getParameter("set-solved") != null) {
			int questionID = Integer.valueOf(req.getParameter("set-solved"));
			AllQuestionController controller = new AllQuestionController();
			controller.setSolved(questionID);
		}
		
		resp.sendRedirect("/ispw_project/ForumPageServlet");
	}
}
