package logic.view;
import java.io.File;
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

	private VBox box;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		User user = Session.getSession().getUserLogged();
		
		try {
			ProfessorStatCard professorCard = new ProfessorStatCard(10);
			hboxStats.getChildren().add(professorCard);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		/*for (int i=0; i<2; i++) {
			StudentStatCard studentCard;
			try {
				studentCard = new StudentStatCard(22, "Verbalized", "Grades");
				hboxStats.getChildren().add(studentCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		Image image;
		JSONArray info = Weather.getInfo();
		int hour = LocalDateTime.now().getHour();
		for (int i = 0; i<5; i++) {
			try {
				JSONArray a = info.getJSONObject(hour+i).getJSONArray("weather");
				JSONObject b = a.getJSONObject(0);
				if ((hour+i) < 6) {
					if (b.getString("main") == "Clear") {
						File file = new File("src/res/png/Weather/Moon.png");
						image = new Image(file.toURI().toString());
					}
					else if (b.getString("main") == "Clouds") {
						File file = new File("src/res/png/Weather/CloudMoon.png");
						image = new Image(file.toURI().toString());
					}
					else if (b.getString("main") == "Rain") {
						File file = new File("src/res/png/Weather/Rain.png");
						image = new Image(file.toURI().toString());
					}
					else if (b.getString("main") == "Thunderstorm") {
						File file = new File("src/res/png/Weather/Thunderstorm.png");
						image = new Image(file.toURI().toString());
					}
					else {
						File file = new File("src/res/png/Weather/Cloud.png");
						image = new Image(file.toURI().toString());
					}
				}
				else {
					if (b.getString("main") == "Clear") {
						File file = new File("src/res/png/Weather/Sun.png");
						image = new Image(file.toURI().toString());
					}
					else if (b.getString("main") == "Clouds") {
						File file = new File("src/res/png/Weather/CloudSun.png");
						image = new Image(file.toURI().toString());
					}
					else if (b.getString("main") == "Rain") {
						File file = new File("src/res/png/Weather/Rain.png");
						image = new Image(file.toURI().toString());
					}
					else if (b.getString("main") == "Thunderstorm") {
						File file = new File("src/res/png/Weather/Thunderstorm.png");
						image = new Image(file.toURI().toString());
					}
					else {
						File file = new File("src/res/png/Weather/Cloud.png");
						image = new Image(file.toURI().toString());
					}
				}
				
				String h;
				if ((hour+i) < 10) {
					h = "0" + (hour+i);
				}
				else {
					h = Integer.toString(hour+i);
				}
				
				WeatherCard weatherCard = new WeatherCard(Weather.convert(info.getJSONObject(hour+i).getDouble("temp")) + "°C", image, h + ":00");
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
