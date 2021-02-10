package logic.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.QuestionController;
import logic.controller.AnswerAQuestionController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/QuestionPageServlet")
public class QuestionPageServlet extends HttpServlet {
	
	private static String loggedAttribute = "loggedUser";
	private static String alertString = "An error as occured. Try later.";
	private static String alertAttribute = "alertMsg";
	private static String questionAttribute = "questionID";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if (session.getAttribute(loggedAttribute) == null) {
	        resp.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		QuestionController controller = new QuestionController();
		List<AnswerBean> answers = null;
		QuestionBean questionBean = null;
		try {
			questionBean = controller.getQuestionByID(Integer.parseInt(req.getParameter(questionAttribute)));
			answers = controller.getAnswersOf(Integer.parseInt(req.getParameter(questionAttribute)));
			
		} catch (RecordNotFoundException e) {
			answers = new ArrayList<>();
			
		} catch (SQLException e) {
			req.setAttribute(alertAttribute, alertString);
			req.getRequestDispatcher("/WEB-INF/HomePage.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("listOfAnswers", answers);
		req.setAttribute("question", questionBean);
		req.setAttribute(questionAttribute, req.getParameter(questionAttribute));
		req.getRequestDispatcher("/WEB-INF/QuestionPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (req.getParameter("submitAdd") != null) {
			AnswerAQuestionController controller = new AnswerAQuestionController();
			AnswerBean answerBean = new AnswerBean();
			answerBean.setUser((UserBean) session.getAttribute(loggedAttribute));
			answerBean.setText(req.getParameter("answer-text"));
			answerBean.setDate(new Date(System.currentTimeMillis()));
			answerBean.setId(Integer.parseInt(req.getParameter("id")));
			
			try {
				controller.save(answerBean);
				req.setAttribute(alertAttribute, "Answer correctly added.");
				
			} catch (SQLException e) {
				req.setAttribute(alertAttribute, alertString);
				req.getRequestDispatcher("/WEB-INF/ForumPage.jsp").forward(req, resp);
				return;
			}
			
			req.getRequestDispatcher("/WEB-INF/ForumPage.jsp").forward(req, resp);		//TODO Mettere script nel jsp e controllare se funziona
		}
	}
}
