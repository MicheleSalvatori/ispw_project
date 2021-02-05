package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.CourseBean;
import logic.view.card.controller.CourseFilterCardView;
import logic.view.graphic.GraphicElement;

public class CourseFilterCard extends GraphicElement {
	
	private CourseFilterCardView courseFilterCardView = new CourseFilterCardView();
	
	public CourseFilterCard(CourseBean course) {
		FXMLLoader loader = getLoader("src/res/fxml/card/CourseFilterCard.fxml");
		loader.setController(courseFilterCardView);
		load(loader);

		courseFilterCardView.setCard(course);
	}
	
	public CourseFilterCardView getController() {
		return courseFilterCardView;
	}
}
