package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.StudentCardView;

public class StudentCard extends AnchorPane {
	
	private StudentCardView studentCardView = new StudentCardView();
	
	public StudentCard(int num, String first, String second) throws IOException {
		URL url = new File("src/res/fxml/StudentCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(studentCardView);
		this.getChildren().add(loader.load());
		
		studentCardView.setCard(num, first, second);
	}
}
