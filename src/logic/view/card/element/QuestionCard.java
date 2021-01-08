package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.bean.QuestionBean;
import logic.view.card.controller.QuestionCardView;

public class QuestionCard extends AnchorPane {
	
	
	private QuestionCardView questionCardView = new QuestionCardView();
	
	public QuestionCard(QuestionBean question) throws IOException {
		URL url = new File("src/res/fxml/card/QuestionCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(questionCardView);
		this.getChildren().add(loader.load());
		questionCardView.setLabel(question);
	}
}
