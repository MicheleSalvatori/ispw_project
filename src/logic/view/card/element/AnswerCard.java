package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.AnswerBean;
import logic.view.card.controller.AnswerCardView;
import logic.view.graphic.GraphicElement;

public class AnswerCard extends GraphicElement {

	private AnswerCardView answerCardView = new AnswerCardView();

	public AnswerCard(AnswerBean answer) {
		FXMLLoader loader = getLoader("src/res/fxml/card/AnswerCard.fxml");
		loader.setController(answerCardView);
		load(loader);

		answerCardView.setCard(answer);
	}
}