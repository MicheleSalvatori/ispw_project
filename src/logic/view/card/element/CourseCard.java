package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.bean.CourseBean;
import logic.bean.ProfessorBean;
import logic.view.card.controller.CourseCardView;

public class CourseCard extends AnchorPane {
	
	private CourseCardView courseCardView = new CourseCardView();
	
	public CourseCard(CourseBean courseBean, List<ProfessorBean> professorsBean, Type type) throws IOException, SQLException {
		URL url = new File("src/res/fxml/card/CourseCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(courseCardView);
		this.getChildren().add(loader.load());

		courseCardView.setCourse(courseBean, professorsBean, type);
	}
	
	public enum Type {
		FOLLOW,
		REQUEST;
	}
}
