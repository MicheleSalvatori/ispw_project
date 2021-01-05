package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.WeeklyLessonCardView;

public class WeeklyLessonCard extends AnchorPane {

	private WeeklyLessonCardView weeklyLessonCardView = new WeeklyLessonCardView();
	
	public WeeklyLessonCard(String day, String classroom, String time) throws IOException {
		URL url = new File("src/res/fxml/card/WeeklyLessonCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(weeklyLessonCardView);
		this.getChildren().add(loader.load());
		
		weeklyLessonCardView.setCard(day, classroom, time);
	}
}