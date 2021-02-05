package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.QuestionBean;
import logic.view.card.controller.QuestionCardView;
import logic.view.graphic.GraphicElement;

public class QuestionCard extends GraphicElement {
	
	private QuestionCardView questionCardView = new QuestionCardView();
	
	public QuestionCard(QuestionBean question) {
		FXMLLoader loader = getLoader("src/res/fxml/card/QuestionCard.fxml");
		loader.setController(questionCardView);
		load(loader);	
		questionCardView.setCard(question);
	}
}