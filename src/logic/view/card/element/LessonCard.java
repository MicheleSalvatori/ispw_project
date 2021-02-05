package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.LessonBean;
import logic.view.card.controller.LessonCardView;
import logic.view.graphic.GraphicElement;

public class LessonCard extends GraphicElement {
	
	private LessonCardView lessonCardView = new LessonCardView();
	
	public LessonCard(LessonBean lesson) {
		FXMLLoader loader = getLoader("src/res/fxml/card/LessonCard.fxml");
		loader.setController(lessonCardView);
		load(loader);
		
		lessonCardView.setCard(lesson);
	}
}
