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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.model.User;
import logic.view.card.element.ProfessorStatCard;
import logic.view.card.element.StudentStatCard;

public class HomepageView implements Initializable {
	
	@FXML
	private ScrollPane scroll;
	@FXML
	private Label labelUsername;
	@FXML
	private HBox hboxWeather, hboxStats;

	private VBox box;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		User user = Session.getSession().getUserLogged();
		
		/*try {
			ProfessorCard professorCard = new ProfessorCard(10);
			hboxStats.getChildren().add(professorCard);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		for (int i=0; i<2; i++) {
			StudentStatCard studentCard;
			try {
				studentCard = new StudentStatCard(22, "Verbalized", "Grades");
				hboxStats.getChildren().add(studentCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for (int i = 0; i<5; i++) {
			try {
				WeatherCard weatherCard = new WeatherCard("1°C", "10:00");
				hboxWeather.getChildren().add(weatherCard);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
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
