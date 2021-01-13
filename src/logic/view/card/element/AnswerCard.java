package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import logic.bean.AnswerBean;
import logic.view.card.controller.AnswerCardView;
import logic.view.graphic.GraphicElement;

public class AnswerCard extends GraphicElement {

	private AnswerCardView answerCardView = new AnswerCardView();

	public AnswerCard(AnswerBean answer) throws IOException {
		URL url = new File("src/res/fxml/card/AnswerCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(answerCardView);
		getPane().getChildren().add(loader.load());

		answerCardView.setCard(answer);
	}
}