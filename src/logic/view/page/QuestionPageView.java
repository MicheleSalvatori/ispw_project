package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import logic.view.card.element.AnswerCard;

public class QuestionPageView implements Initializable {

    @FXML
    private ScrollPane scrollAnswers;

    @FXML
    private TextArea textQuestion;
    private VBox answerBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		answerBox = new VBox();
		for (int i = 0; i < 10; i++) {
			AnswerCard answerCard;
			try {
				answerCard = new AnswerCard("Molliccio", "2030301232123", "Risposta non esaustiva");
				answerBox.getChildren().add(answerCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		answerBox.setSpacing(20.0);
		scrollAnswers.setContent(answerBox);
	}

}
