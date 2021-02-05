package logic.view.page;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

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
import logic.controller.CheckClassPositionController;
import logic.controller.CheckWeatherController;
import logic.controller.LoginController;
import logic.controller.QuestionController;
import logic.controller.ViewNextLessonController;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Role;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ProfessorStatCard;
import logic.view.card.element.StudentStatCard;
import logic.view.card.element.WeatherCard;

public class HomepageView implements Initializable {
	
	@FXML
	private Label labelUsername;
	
	@FXML
	private HBox hboxWeather;
	
	@FXML
	private HBox hboxStats;
	
	@FXML
	private VBox vboxScroll;
	
	@FXML
	private WebView webMap;
	
	@FXML
	private AnchorPane anchorNextLesson;
	
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
			
			ViewNextLessonController viewNextLessonController = new ViewNextLessonController();
			List<LessonBean> lessons = viewNextLessonController.getTodayLessons(UserBean.getInstance());
			
			if (lessons.size() == 1) {
				vboxScroll.getChildren().add(new Label("There are no future lessons today"));
			}

			for (LessonBean lessonBean : lessons) {
				LessonCard lessonCard = new LessonCard(lessonBean);
				
				// AnchorPane case
				if (lessonBean == lessons.get(0)) {
					anchorNextLesson.getChildren().add(lessonCard.getPane());
				}
				
				// ScrollPane case
				else {
					vboxScroll.getChildren().add(lessonCard.getPane());
				}
			}
		
		} catch (RecordNotFoundException e) {
			vboxScroll.getChildren().add(new Label("There are no future lessons today"));
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			LoginController loginController = new LoginController();
			loginController.logout();
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
				
				hboxStats.getChildren().add(studentCard1.getPane());
				hboxStats.getChildren().add(studentCard2.getPane());
			}
				
			// User is a Professor
			else if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
				
				QuestionController controller = new QuestionController();
				ProfessorStatCard professorCard = new ProfessorStatCard(controller.countQuestions(UserBean.getInstance()));
				
				hboxStats.getChildren().add(professorCard.getPane());
			}
			
		} catch (RecordNotFoundException e) {
			if (UserBean.getInstance().getRole() == Role.STUDENT) {
				StudentStatCard studentCard1 = new StudentStatCard(0, "Verbalized", "Grades");
				StudentStatCard studentCard2 = new StudentStatCard(0, "Average", "Grade");
				hboxStats.getChildren().clear();
				hboxStats.getChildren().add(studentCard1.getPane());
				hboxStats.getChildren().add(studentCard2.getPane());
			}
			
			else if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
				ProfessorStatCard professorCard = new ProfessorStatCard(0);
				hboxStats.getChildren().clear();
				hboxStats.getChildren().add(professorCard.getPane());
			}
		
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			LoginController loginController = new LoginController();
			loginController.logout();
		}	
	}
	
	// Add Weather cards to the scene
	private void addWeatherCards() {
		
		int hour = LocalDateTime.now().getHour();
		CheckWeatherController controller = new CheckWeatherController();
		
		for (int i=0; i<5; i++) {
			List<String> info = controller.getWeather(hour+i);
			Image image = new Image(info.get(1));
			WeatherCard weatherCard = new WeatherCard(info.get(0), image, info.get(2));
			hboxWeather.getChildren().add(weatherCard.getPane());
		}
	}
	
	// Add map
	private void addWebMap() {
		CheckClassPositionController controller = new CheckClassPositionController();
		webMap.getEngine().load(controller.getMap());
	}
}
