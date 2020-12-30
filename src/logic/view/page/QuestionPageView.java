package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.AnswerCard;

public class QuestionPageView implements Initializable {

    @FXML
    private ScrollPane scrollAnswers;

    @FXML
    private TextArea textQuestion;
    
    @FXML
    private VBox vboxAnswer;
    
    @FXML
    private Button btnAnswer;
    
	@FXML
	private void answer(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.ANSWER_QUESTION, event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textQuestion.setText("Quanto fa 2+2?");
		
		for (int i = 0; i < 10; i++) {
			AnswerCard answerCard;
			try {
				answerCard = new AnswerCard("Molliccio", "30/12/1850", "Risposta non esaustiva");
				vboxAnswer.getChildren().add(answerCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
