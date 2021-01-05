package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.model.Student;
import logic.view.card.controller.RequestCardView;

public class RequestCard extends AnchorPane {
	
	private RequestCardView requestCardView = new RequestCardView();
	
	public RequestCard(Student student, String course) throws IOException {
		URL url = new File("src/res/fxml/card/RequestCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(requestCardView);
		this.getChildren().add(loader.load());

		requestCardView.setCard(student, course);
	}

}
