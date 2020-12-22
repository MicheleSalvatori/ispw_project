package logic.view;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import logic.Session;
import logic.model.User;
import logic.utilities.Weather;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ProfessorStatCard;
import logic.view.card.element.StudentStatCard;
import logic.view.card.element.WeatherCard;

public class HomepageView implements Initializable {
	
	@FXML
	private ScrollPane scroll;
	@FXML
	private Label labelUsername;
	@FXML
	private HBox hboxWeather, hboxStats;
	@FXML
	private VBox vboxScroll;
	@FXML
	private WebView webMap;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		User user = Session.getSession().getUserLogged();
		
		labelUsername.setText("Hello " + user.getName() + "!");
		
		// User is a Student
		if (Session.getSession().getType() == 0) {
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
		}
		// User is a Professor
		else {
			try {
				ProfessorStatCard professorCard = new ProfessorStatCard(10);
				hboxStats.getChildren().add(professorCard);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		addWeatherCards();
		addLessonCards();
		
		webMap.getEngine().load(getClass().getResource("../../res/html/map.html").toString());
	}
	
	// Add Lesson cards to the scene
	private void addLessonCards() {
		for (int i=0; i<10; i++) {
			LessonCard lessonCard;
			try {
				lessonCard = new LessonCard(i+"",i+"",i+"");
				vboxScroll.getChildren().add(lessonCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Add Weather cards to the scene
	private void addWeatherCards() {
		Image image;
		JSONArray info = Weather.getInfo();
		int hour = LocalDateTime.now().getHour();
		
		for (int i=0; i<5; i++) {
			
			try {
				JSONArray hourly = info.getJSONObject(hour+i).getJSONArray("weather");
				JSONObject weather = hourly.getJSONObject(0);
				image = Weather.weatherImage(hour, weather.getString("main"));
				
				String h;
				if ((hour+i) < 10) {
					h = "0" + (hour+i);
				}
				else {
					h = Integer.toString(hour+i);
				}
				
				WeatherCard weatherCard = new WeatherCard(Weather.kelvinToCelsius(info.getJSONObject(hour+i).getDouble("temp")) + "°C", image, h + ":00");
				hboxWeather.getChildren().add(weatherCard);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
