package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.AssignmentCard;
import logic.view.card.element.QuestionCard;

public class ForumPageView implements Initializable {

	@FXML
	private Button btnMyQuestion, btnNewQuestion;
	@FXML
	private VBox vboxQuestion, vboxAssignment;

	@FXML
	private void myQuestion(ActionEvent ae) {
		System.out.println("MyQuestionButton");
	}

	@FXML
	private void newQuestion(ActionEvent ae) throws IOException {
		PageLoader.getInstance().buildPage(Page.NEWQUESTION, ae);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for (int i = 1; i < 10; i++) {
			QuestionCard questionCard;
			try {
				questionCard = new QuestionCard(String.valueOf(i), "Domanda di merda", "21/12/2020", "Emanuele", "Alfano", "ISPW");
				vboxQuestion.getChildren().add(questionCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i < 10; i++) {
			AssignmentCard assignmentCard;
			try {
				assignmentCard = new AssignmentCard(String.valueOf(i), "Use-Cases", "02/02/2021", "ISPW");
				vboxAssignment.getChildren().add(assignmentCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
