package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.RequestCard;

public class RequestPageView implements Initializable {
	
	@FXML
	private VBox vboxRequest, vboxCourse;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for (int i=0; i<10; i++) {
			try {
				RequestCard requestCard = new RequestCard("lezzo", "penzola");
				vboxRequest.getChildren().add(requestCard);
				
				CourseFilterCard courseFilterCard = new CourseFilterCard("ISPW");
				vboxCourse.getChildren().add(courseFilterCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
