package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.AssignmentCardView;

public class AssignmentCard extends AnchorPane {
	
	
	private AssignmentCardView assignmentCardView = new AssignmentCardView();
	
	public AssignmentCard(String number, String title, String date, String course) throws IOException {
		URL url = new File("src/res/fxml/AssignmentCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(assignmentCardView);
		this.getChildren().add(loader.load());
		
		assignmentCardView.setLabel(number, title, date,course);
	}
}
