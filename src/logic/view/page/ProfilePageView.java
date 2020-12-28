package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import logic.view.card.element.CourseCard;

public class ProfilePageView implements Initializable {
	
	@FXML
	private VBox vboxScroll;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for (int i=0; i<20; i++) {
			try {
				CourseCard courseCard = new CourseCard(i+"",i+"",i+"",i+"");
				vboxScroll.getChildren().add(courseCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
