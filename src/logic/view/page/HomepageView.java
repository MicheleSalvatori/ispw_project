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
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.AllQuestionController;
import logic.controller.ViewNextLessonController;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;
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
	
	
	private ViewNextLessonController viewNextLessonController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setUserName(UserBean.getInstance().getName());
		addStatCards();
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
			List<LessonBean> lessons = viewNextLessonController.getTodayLessons(UserBean.getInstance());
			
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
		
		} catch (RecordNotFoundException e) {
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
	
	// Add Stat cards to the scene
	private void addStatCards() {
		
		try {
			
			// User is a Student
			if (UserBean.getInstance().getRole() == Role.STUDENT) {
				
				ViewVerbalizedExamsController controller = new ViewVerbalizedExamsController();
				StudentStatCard studentCard1 = new StudentStatCard(controller.countVerbalizedExams(UserBean.getInstance()), "Verbalized", "Grades");
				StudentStatCard studentCard2 = new StudentStatCard((int)controller.WPA(controller.getVerbalizedExams(UserBean.getInstance())), "Average", "Grade");
				
				hboxStats.getChildren().add(studentCard1);
				hboxStats.getChildren().add(studentCard2);
			}
				
			// User is a Professor
			else if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
				
				AllQuestionController controller = new AllQuestionController();
				ProfessorStatCard professorCard = new ProfessorStatCard(controller.countQuestions(UserBean.getInstance()));
				
				hboxStats.getChildren().add(professorCard);
			}
			
		} catch (RecordNotFoundException e) {
			try {
				
				if (UserBean.getInstance().getRole() == Role.STUDENT) {
					StudentStatCard studentCard1 = new StudentStatCard(0, "Verbalized", "Grades");
					StudentStatCard studentCard2 = new StudentStatCard(0, "Average", "Grade");
					hboxStats.getChildren().clear();
					hboxStats.getChildren().add(studentCard1);
					hboxStats.getChildren().add(studentCard2);
				}
				
				else if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
					ProfessorStatCard professorCard = new ProfessorStatCard(0);
					hboxStats.getChildren().clear();
					hboxStats.getChildren().add(professorCard);
				}
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
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
				
				WeatherCard weatherCard = new WeatherCard(Weather.kelvinToCelsius(info.getJSONObject(hour+i).getDouble("temp")) + " \u2103", image, h + ":00");
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
