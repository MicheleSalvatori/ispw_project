package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.QuestionCardView;

public class QuestionCard extends AnchorPane {
	
	
	private QuestionCardView questionCardView = new QuestionCardView();
	
	public QuestionCard(String number, String questionObject, String date, String name, String surname, String course) throws IOException {
		URL url = new File("src/res/fxml/QuestionCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(questionCardView);
		this.getChildren().add(loader.load());
		
		questionCardView.setLabel(number, questionObject, date, name, surname, course);
	}
}
