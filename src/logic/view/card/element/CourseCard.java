package logic.view.card.element;

import java.util.List;

import javafx.fxml.FXMLLoader;
import logic.bean.CourseBean;
import logic.bean.UserBean;
import logic.view.card.controller.CourseCardView;
import logic.view.graphic.GraphicElement;

public class CourseCard extends GraphicElement {
	
	private CourseCardView courseCardView = new CourseCardView();
	
	public CourseCard(CourseBean courseBean, List<UserBean> professorsBean, Type type) {
		FXMLLoader loader = getLoader("src/res/fxml/card/CourseCard.fxml");
		loader.setController(courseCardView);
		load(loader);

		courseCardView.setCourse(courseBean, professorsBean, type);
	}
	
	public enum Type {
		FOLLOW,
		REQUEST;
	}
}
