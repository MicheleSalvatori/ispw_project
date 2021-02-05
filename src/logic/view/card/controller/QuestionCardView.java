package logic.view.card.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.page.ForumPageView;

public class QuestionCardView {

	@FXML
	private Label labelName;
	
	@FXML
	private Label labelSurname;
	
	@FXML
	private Label labelNumber;
	
	@FXML
	private Label labelQuestionObject;
	
	@FXML
	private Label labelQuestionDate;
	
	@FXML
	private Button btnCourse;
	
	@FXML
	private Button btnView;
	
	@FXML
	private Button btnSolved;
	
	private QuestionBean question;

	public void setCard(QuestionBean question) {
		this.question = question;

		btnCourse.setText(question.getCourse());
		labelName.setText(question.getStudent().getName());
		labelSurname.setText(question.getStudent().getSurname());
		labelNumber.setText(String.valueOf(question.getId()));
		labelQuestionDate.setText(SQLConverter.date(question.getDate()));
		labelQuestionObject.setText(question.getTitle());

		if (question.isSolved()) {
			btnSolved.setDisable(true);
			btnSolved.setText("Solved");
			btnSolved.setStyle("-fx-text-fill: green");
			
		} else {
//			Only the author of this question can be set it to solved
			if (question.getStudent().getUsername().equals(UserBean.getInstance().getUsername())) {
				btnSolved.setDisable(false);
				btnSolved.setText("Set Solved");
			} else {
				btnSolved.setText("Unsolved");
			}
			btnSolved.setStyle("-fx-text-fill: red");
		}
	}

	@FXML
	private void viewQuestion(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.QUESTION, question);
	}

	@FXML
	private void setSolved(ActionEvent ae) {
		ForumPageView controller = (ForumPageView) PageLoader.getInstance().getController();
		if (controller.setSolved(question.getId())) {
			question.setSolved(true);
			setCard(question);
		}
	}

	@FXML
	private void course(ActionEvent event) {
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbreviation(question.getCourse());
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
}