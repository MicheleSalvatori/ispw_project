package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.view.card.element.CourseCard;

public class ProfilePageView implements Initializable {
	
	@FXML
	private VBox vboxScroll;
	
	@FXML
	private Button btnAdd, btnRemove;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (Session.getSession().getType() == Session.PROFESSOR) {
			btnAdd.setVisible(false);
			btnRemove.setVisible(false);
		}
		
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
