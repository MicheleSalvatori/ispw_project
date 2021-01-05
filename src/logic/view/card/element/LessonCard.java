package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.LessonCardView;

public class LessonCard extends AnchorPane {
	
	private LessonCardView lessonCardView = new LessonCardView();
	
	public LessonCard(String lesson, String classroom, String time) throws IOException {
		URL url = new File("src/res/fxml/card/LessonCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(lessonCardView);
		this.getChildren().add(loader.load());
		
		lessonCardView.setLabel(lesson, classroom, time);
	}
	
	public LessonCard(String day, String classroom, String course, String time) throws IOException {
		URL url = new File("src/res/fxml/card/LessonCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(lessonCardView);
		this.getChildren().add(loader.load());
		
		lessonCardView.setLabel(day, classroom, course, time);
	}
}
