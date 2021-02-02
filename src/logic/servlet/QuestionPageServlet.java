package logic.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.AllQuestionController;
import logic.controller.InsertAnswerController;
import logic.exceptions.RecordNotFoundException;
@WebServlet("/QuestionPageServlet")
public class QuestionPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuestionBean questionBean = new QuestionBean();
		StudentBean student = new StudentBean();
		questionBean.setDate(Date.valueOf(req.getParameter("questionDate")));
		questionBean.setTitle(req.getParameter("questionTitle"));
		questionBean.setText(req.getParameter("questionText"));
		student.setName(req.getParameter("authorName"));
		student.setSurname(req.getParameter("authorSurname"));
		questionBean.setStudent(student);

		AllQuestionController controller = new AllQuestionController();
		List<AnswerBean> answers = null;
		try {
			answers = controller.getAnswersOf(Integer.valueOf(req.getParameter("questionID")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			answers = null;
		}
		
		req.setAttribute("listOfAnswers", answers);
		req.setAttribute("question", questionBean);
		req.setAttribute("questionID", req.getParameter("questionID"));
		req.getRequestDispatcher("/WEB-INF/QuestionPage.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.print("QuestionPageServletPOST: ");
		HttpSession session = req.getSession();
		if (req.getParameter("submitAdd") != null) {
			InsertAnswerController controller = new InsertAnswerController();
			AnswerBean answerBean = new AnswerBean();
			answerBean.setUser((UserBean) session.getAttribute("loggedUser"));
			answerBean.setText(req.getParameter("answer-text"));
			answerBean.setDate(new Date(System.currentTimeMillis()));
			answerBean.setId(Integer.valueOf(req.getParameter("id")));
			try {
				controller.save(answerBean);
				resp.sendRedirect("/ispw_project/ForumPageServlet");			//TODO lanciare popuop di avvenuto inserimento. Problema: non possiamo ricaricare QuestionPage perchè non abbiamo i dati della domanda
			} catch (SQLException e) {
				// TODO
				e.printStackTrace();
			}
		}
	}
}
