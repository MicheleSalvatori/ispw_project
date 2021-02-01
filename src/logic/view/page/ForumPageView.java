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
import logic.bean.AssignmentBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.AddAssignmentController;
import logic.controller.AllQuestionController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.view.card.element.AssignmentCard;
import logic.view.card.element.QuestionCard;

public class ForumPageView implements Initializable {

	@FXML
	private Button btnMyQuestions, btnNewQuestion, btnAllQuestions, btnNewAssignment;
	
	@FXML
	private Label labelLoading;
	
	@FXML
	private VBox vboxQuestion, vboxAssignment;
	
	private AllQuestionController allQuestionController;
	private AddAssignmentController addAssignmentController;
	
	private List<QuestionBean> allQuestions, myQuestions;
	private List<AssignmentBean> assignments;

	@FXML
	private void myQuestion(ActionEvent event) throws IOException {
		vboxQuestion.getChildren().clear();
		
		if (myQuestions == null) {
			myQuestions = new ArrayList<>();
			if (allQuestions != null) {
				for (QuestionBean q : allQuestions) {
					QuestionCard questionCard;
					String userQuestion = q.getStudent().getUsername();
					String userSession = UserBean.getInstance().getUsername();
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
	private void allQuestions(ActionEvent event) {
		setAllQuestions();
		btnAllQuestions.setDisable(true);
		btnMyQuestions.setDisable(false);
	}

	@FXML
	private void newQuestion(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.NEWQUESTION, event);
	}
	
	@FXML
	private void newAssignment(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.NEWASSIGNMENT, event);
	}

	private void getAllQuestions() {
		try {
			allQuestions = allQuestionController.getAllQuestions(UserBean.getInstance());
			
		} catch (SQLException e) {
			AlertController.infoAlert(e.getMessage());
			btnMyQuestions.setDisable(true);
			btnAllQuestions.setDisable(true);
			
		} catch (RecordNotFoundException e) {
			AlertController.infoAlert(e.getMessage());
			e.printStackTrace();
		}
	}

	private void setAllQuestions() {
		if (vboxQuestion.getChildren() != null) {
			vboxQuestion.getChildren().clear();
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
		int nCourses;
		try {
			nCourses = allQuestionController.getNumberCourses(UserBean.getInstance());
		} catch (SQLException e) {
			nCourses = 0;
		}
		
		Role roleLogged = UserBean.getInstance().getRole();
		switch (roleLogged) {
		
		case PROFESSOR:
			btnNewQuestion.setVisible(false);
			btnMyQuestions.setVisible(false);
			btnAllQuestions.setVisible(false);
			
			btnNewAssignment.setVisible(true);
			if (nCourses== 0) {
				labelLoading.setText("You are not assigned to any course at the moment.");
				return;
			}
			break;
			
		case STUDENT:
			if (nCourses == 0) {
				btnNewQuestion.setDisable(true);
				btnMyQuestions.setDisable(true);
				labelLoading.setText("You are not enrolled in any course, you cannot ask questions.");
				return;
			}
			break;
			
		default:
			break;
		}
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getAllQuestions();
				setAllQuestions();
			}
		});
		
		try {
			
			addAssignmentController = new AddAssignmentController();
			
			assignments = addAssignmentController.getAssignments(UserBean.getInstance());
			
			for (AssignmentBean assignmentBean : assignments) {
				AssignmentCard assignmentCard = new AssignmentCard(assignmentBean);
				vboxAssignment.getChildren().add(assignmentCard);
			}
			
		} catch (RecordNotFoundException e) {
			vboxAssignment.getChildren().add(new Label("No assignment found"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  
	public boolean setSolved(int questionID) {
		return allQuestionController.setSolved(questionID);
	}
}

