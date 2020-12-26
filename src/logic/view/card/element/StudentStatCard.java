package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.StudentStatCardView;

public class StudentStatCard extends AnchorPane {
	
	private StudentStatCardView studentCardView = new StudentStatCardView();
	
	public StudentStatCard(int num, String first, String second) throws IOException {
		URL url = new File("src/res/fxml/card/StudentCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(studentCardView);
		this.getChildren().add(loader.load());
		
		studentCardView.setCard(num, first, second);
	}
}
