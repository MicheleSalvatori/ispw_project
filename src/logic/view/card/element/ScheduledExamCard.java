package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.ExamBean;
import logic.view.card.controller.ScheduledExamCardView;
import logic.view.graphic.GraphicElement;

public class ScheduledExamCard extends GraphicElement {
	
	private ScheduledExamCardView scheduledExamCardView = new ScheduledExamCardView();
	
	public ScheduledExamCard(ExamBean exam) {
		FXMLLoader loader = getLoader("src/res/fxml/card/ScheduledExamCard.fxml");
		loader.setController(scheduledExamCardView);
		load(loader);

		scheduledExamCardView.setCard(exam);
	}
}