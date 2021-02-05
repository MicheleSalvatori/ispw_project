package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.WeeklyLessonBean;
import logic.view.card.controller.WeeklyLessonCardView;
import logic.view.graphic.GraphicElement;

public class WeeklyLessonCard extends GraphicElement {

	private WeeklyLessonCardView weeklyLessonCardView = new WeeklyLessonCardView();
	
	public WeeklyLessonCard(WeeklyLessonBean lesson) {
		FXMLLoader loader = getLoader("src/res/fxml/card/WeeklyLessonCard.fxml");
		loader.setController(weeklyLessonCardView);
		load(loader);
		
		weeklyLessonCardView.setCard(lesson);
	}
}
