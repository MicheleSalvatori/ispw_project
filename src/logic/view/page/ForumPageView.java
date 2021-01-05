package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.bean.QuestionBean;
import logic.controller.AllQuestionController;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.QuestionCard;

public class ForumPageView implements Initializable {

	@FXML
	private Button btnMyQuestions, btnNewQuestion, btnAllQuestions;
	@FXML
	private Label labelLoading;
	@FXML
	private VBox vboxQuestion, vboxAssignment;
	private AllQuestionController allQuestionController;
	private List<QuestionBean> allQuestions, myQuestions;

	@FXML
	private void myQuestion(ActionEvent ae) throws IOException {
		vboxQuestion.getChildren().removeAll(vboxQuestion.getChildren());
		if (myQuestions == null) {
			myQuestions = new ArrayList<>();
			if (allQuestions != null) {
				for (QuestionBean q : allQuestions) {
					QuestionCard questionCard;
					String userQuestion = q.getStudent().getUsername();
					String userSession = Session.getSession().getUserLogged().getUsername();
					if (userQuestion.equals(userSession)) {
						myQuestions.add(q);
						questionCard = new QuestionCard(q);
						vboxQuestion.getChildren().add(questionCard);
					}
				}
			} else
				return;
		} else {
			for (QuestionBean q : myQuestions) {
				QuestionCard questionCard = new QuestionCard(q);
				vboxQuestion.getChildren().add(questionCard);
			}
		}
		btnMyQuestions.setDisable(true);
		btnAllQuestions.setDisable(false);
	}

	@FXML
	private void allQuestions(ActionEvent ae) {
		setAllQuestions();
		btnAllQuestions.setDisable(true);
		btnMyQuestions.setDisable(false);
	}

	@FXML
	private void newQuestion(ActionEvent ae) throws IOException {
		PageLoader.getInstance().buildPage(Page.NEWQUESTION, ae);
	}

	private void getAllQuestions() {
		try {
			allQuestions = allQuestionController.getAllQuestions();
		} catch (SQLException e) {
			AlertController.buildInfoAlert(e.getMessage(), "Bad news..", btnMyQuestions);
			btnMyQuestions.setDisable(true);
			btnAllQuestions.setDisable(true);
		}
	}

	private void setAllQuestions() {
		if (vboxQuestion.getChildren() != null) {
			vboxQuestion.getChildren().removeAll(vboxQuestion.getChildren());
		}
		if (allQuestions == null) {
			labelLoading.setText("No one seems to have any questions to ask in your courses. Be the first!");
			btnMyQuestions.setDisable(true);
			btnAllQuestions.setDisable(true);
			return;
		}
		labelLoading.setVisible(false);
		for (QuestionBean q : allQuestions) {
			QuestionCard questionCard;
			try {
				questionCard = new QuestionCard(q);
				vboxQuestion.getChildren().add(questionCard);
			} catch (IOException e) {
				// TODO caricamento fxml card
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		allQuestionController = new AllQuestionController();
		try {
			if (allQuestionController.getNumberCourses() == 0) {
				btnNewQuestion.setDisable(true);
				btnMyQuestions.setDisable(true);
				labelLoading.setText("You are not enrolled in any course, you cannot ask questions.");
				return;
			}
		} catch (SQLException e) {
			//Sollevata da CourseDAO.getCourseNumberOf(username)
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getAllQuestions();
				setAllQuestions();
			}
		});
	}
}
