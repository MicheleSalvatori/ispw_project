package logic.view.page;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import logic.Session;
import logic.model.Lesson;
import logic.model.User;
import logic.model.dao.LessonDAO;
import logic.utilities.SQLConverter;
import logic.utilities.Weather;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ProfessorStatCard;
import logic.view.card.element.StudentStatCard;
import logic.view.card.element.WeatherCard;

public class HomepageView implements Initializable {
	
	@FXML
	private Label labelUsername;
	
	@FXML
	private HBox hboxWeather, hboxStats;
	
	@FXML
	private VBox vboxScroll;
	
	@FXML
	private WebView webMap;
	
	@FXML
	private AnchorPane anchorNextLesson;
	
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
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		addLessonCards(date, time);

		webMap.getEngine().load(getClass().getResource("/res/html/map.html").toString());
	}
	
	// Add Lesson cards to the scene
	private void addLessonCards(Date date, Time time) {
		try {
			List<Lesson> lessons = LessonDAO.getNextLessons(date, time);
			
			if (lessons == null) {
				Label label = new Label("There are no future lessons today");
				vboxScroll.getChildren().add(label);
				return;
			}
			
			if (lessons.size() == 1) {
				Label label = new Label("There are no future lessons today");
				vboxScroll.getChildren().add(label);
			}
			
			Boolean i = true;
			for (Lesson lesson : lessons) {
				try {
					LessonCard lessonCard = new LessonCard(lesson.getCourse().getAbbrevation(), lesson.getClassroom().getName(), SQLConverter.time(lesson.getTime()));
					if (i) {
						anchorNextLesson.getChildren().add(lessonCard);
						i = false;
					}
					else {
						vboxScroll.getChildren().add(lessonCard);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				
				int hourMod = (hour+i)%24;
				image = Weather.weatherImage(hourMod, weather.getString("main"));
				
				String h;
				if ((hourMod) < 10) {
					h = "0" + (hourMod);
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
