package logic.view.page;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AnswerView implements Initializable {
	
	@FXML
	private TextArea textQuestion, textAnswer;
	
	@FXML
	private Button btnSubmit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textQuestion.setText("Quanto fa 2+2?");	
	}
}
