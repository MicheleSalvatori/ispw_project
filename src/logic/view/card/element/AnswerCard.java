package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.AnswerCardView;

public class AnswerCard extends AnchorPane {

	private AnswerCardView answerCardView = new AnswerCardView();

	public AnswerCard(String student, String date, String text) throws IOException {
		URL url = new File("src/res/fxml/card/AnswerCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(answerCardView);
		this.getChildren().add(loader.load());

		answerCardView.setLabel(student, date, text);
	}
}
