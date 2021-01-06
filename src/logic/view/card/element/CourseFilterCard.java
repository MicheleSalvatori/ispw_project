package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.bean.CourseBean;
import logic.view.card.controller.CourseFilterCardView;

public class CourseFilterCard extends AnchorPane {
	
	private CourseFilterCardView courseFilterCardView = new CourseFilterCardView();
	
	public CourseFilterCard(CourseBean course) throws IOException {
		URL url = new File("src/res/fxml/card/CourseFilterCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(courseFilterCardView);
		this.getChildren().add(loader.load());

		courseFilterCardView.setCard(course);
	}
	
	public CourseFilterCardView getController() {
		return courseFilterCardView;
	}
}
