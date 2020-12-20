package logic.view;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.model.User;

public class HomepageView implements Initializable {
	
	@FXML
	private ScrollPane scroll;
	@FXML
	private Label labelUsername;

	private VBox box;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		User user = Session.getSession().getUserLogged();
		
		labelUsername.setText("Hello " + user.getName() + "!");
		
		box = new VBox();
		for (int i=0; i<10; i++) {
			LessonCard lessonCard;
			try {
				lessonCard = new LessonCard(i+"",i+"",i+"");
				box.getChildren().add(lessonCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		box.setSpacing(20);	
		
		scroll.setContent(box);
	}

}
