package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.VerbalizedBean;
import logic.view.card.controller.ExamCardView;
import logic.view.graphic.GraphicElement;

public class ExamCard extends GraphicElement {

	private ExamCardView examCardView = new ExamCardView();

	public ExamCard(VerbalizedBean verb, int num) {
		FXMLLoader loader = getLoader("src/res/fxml/card/ExamCard.fxml");
		loader.setController(examCardView);
		load(loader);

		examCardView.setCard(verb, num);
	}
}
