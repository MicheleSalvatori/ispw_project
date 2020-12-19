package logic.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LessonCard extends AnchorPane {
	
	
	private LessonCardView lessonCardView = new LessonCardView();
	
	public LessonCard(String lesson, String classroom, String time) throws IOException {
		URL url = new File("src/res/fxml/LessonCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(lessonCardView);
		this.getChildren().add(loader.load());
		
		lessonCardView.setLabel(lesson, classroom, time);
	}
}
