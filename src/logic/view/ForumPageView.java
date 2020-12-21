package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ForumPageView implements Initializable {

	@FXML
	private ScrollPane scrollQuestion;
	@FXML
	private Button btnMyQuestion, btnNewQuestion;
	private VBox questionBox;

	@FXML
	private void myQuestion() {
		System.out.println("MyQuestionButton");
	}

	@FXML
	private void newQuestion() {
		System.out.println("NewQuestionButton");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		questionBox = new VBox();
		for (int i = 0; i < 10; i++) {
			QuestionCard questionCard;
			try {
				questionCard = new QuestionCard(String.valueOf(i), "Domanda di merda", "21/12/2020", "Emanuele",
						"Alfano", "ISPW");
				questionBox.getChildren().add(questionCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		questionBox.setSpacing(20);
		scrollQuestion.setContent(questionBox);
	}

}
