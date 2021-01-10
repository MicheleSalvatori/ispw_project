package logic.view.page;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import logic.bean.LessonBean;
import logic.controller.ViewNextLessonController;
import logic.utilities.Role;
import logic.utilities.Weather;
import logic.view.card.element.AdminStatCard;
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
	
	
	private ViewNextLessonController viewNextLessonController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setUserName(Session.getSession().getUserLogged().getName());
		
		switch (Session.getSession().getType()) {
		case STUDENT:
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
			break;
		case PROFESSOR:
			try {
				ProfessorStatCard professorCard = new ProfessorStatCard(10);
				hboxStats.getChildren().add(professorCard);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			break;
		case ADMIN:
			try {
				AdminStatCard adminCard = new AdminStatCard(10);
				hboxStats.getChildren().add(adminCard);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			break;

		default:
			break;
		}
		
		// User is a Student
		if (Session.getSession().getType() == Role.STUDENT) {
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
		else if (Session.getSession().getType() == Role.PROFESSOR) {
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
		addWebMap();
	}
	
	// Set name
	private void setUserName(String name) {
		labelUsername.setText("Hello " + name + "!");
	}
	
	
	
	// Add Lesson cards to the scene
	private void addLessonCards() {
		try {
			
			viewNextLessonController = new ViewNextLessonController();
			List<LessonBean> lessons = viewNextLessonController.getTodayLessons();
			
			if (lessons.size() == 1) {
				vboxScroll.getChildren().add(new Label("There are no future lessons today"));
			}

			for (LessonBean lessonBean : lessons) {
				LessonCard lessonCard = new LessonCard(lessonBean);
				
				// AnchorPane case
				if (lessonBean == lessons.get(0)) {
					anchorNextLesson.getChildren().add(lessonCard);
				}
				
				// ScrollPane case
				else {
					vboxScroll.getChildren().add(lessonCard);
				}
			}
		
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("There are no future lessons today"));
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
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
	
	// Add map
	private void addWebMap() {
		webMap.getEngine().load(getClass().getResource("/res/html/map.html").toString());
	}
}
