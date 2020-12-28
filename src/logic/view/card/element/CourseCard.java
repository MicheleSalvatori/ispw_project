package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.CourseCardView;

public class CourseCard extends AnchorPane {
	
	private CourseCardView courseCardView = new CourseCardView();
	
	public CourseCard(String name, String professor, String year, String semester) throws IOException {
		URL url = new File("src/res/fxml/card/CourseCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(courseCardView);
		this.getChildren().add(loader.load());

		courseCardView.setLabel(name, professor, year, semester);
	}
}
