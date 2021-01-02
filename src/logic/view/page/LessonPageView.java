package logic.view.page;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.model.Course;
import logic.model.dao.CourseDAO;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.utilities.Weather;
import logic.view.card.element.WeatherCard;

public class LessonPageView implements Initializable {

    @FXML
    private Button btnCourse;

    @FXML
    private Label labelProfessor;

    @FXML
    private Label labelClassroom;

    @FXML
    private Label labelTime;
    
    @FXML
    private Label labelDate;

    @FXML
    private TextArea textTopic;

    @FXML
    private AnchorPane paneSeat;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private AnchorPane weatherCard;
    
    
    public void setPage(LessonBean lessonBean) {
    	btnCourse.setText(lessonBean.getCourse().getAbbrevation());
    	labelClassroom.setText(lessonBean.getClassroom().getName());
    	labelTime.setText(SQLConverter.time(lessonBean.getTime()));
    	labelDate.setText(SQLConverter.date(lessonBean.getDate()));
    	labelProfessor.setText(lessonBean.getCourse().getProfessor().getName() + " " + lessonBean.getCourse().getProfessor().getSurname());
    	textTopic.setText(lessonBean.getTopic());
    	
    	setWeatherCard(lessonBean.getTime());
    }

    
    @FXML
    private void course(ActionEvent event) throws IOException, SQLException {
    	PageLoader.getInstance().buildPage(Page.COURSE, event);
    	CoursePageView coursePageView = (CoursePageView) PageLoader.getInstance().getController();
    	
    	Course course = CourseDAO.getCourseByAbbrevation(btnCourse.getText());
    	
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbrevation(course.getAbbrevation());
    	courseBean.setName(course.getName());
    	courseBean.setProfessor(course.getProfessor());
    	
    	coursePageView.setPage(courseBean);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//int numSeat = 0;
		int numRow = 10;
		//int numCol = 0;
		int seatPerRow = 10;
		
		GridPane gridSeat = new GridPane();
		paneSeat.getChildren().add(gridSeat);
		AnchorPane.setTopAnchor(gridSeat, 0d);
		AnchorPane.setLeftAnchor(gridSeat, 0d);
		AnchorPane.setRightAnchor(gridSeat, 0d);
		//AnchorPane.setBottomAnchor(gridSeat, 5d);
		
		//scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		gridSeat.setVgap(10);

		gridSeat.setAlignment(Pos.TOP_CENTER);
		
		for (int i=0; i<numRow; i++) {
			
			for (int j=0; j<seatPerRow; j++) {
				
				Button btn = new Button(j+"");
				btn.getStylesheets().add("/res/style/SeatButton.css");
				btn.setMaxWidth(1);
				
				gridSeat.add(btn, j, i);
				
				GridPane.setFillHeight(btn, false);
				GridPane.setHgrow(btn, Priority.ALWAYS);
				GridPane.setVgrow(btn, Priority.ALWAYS);
				GridPane.setHalignment(btn, HPos.CENTER);
			}
		}
		
		for (int j=0; j<numRow; j++) {
			Label label = new Label(String.valueOf((char)(65+j)));
			gridSeat.add(label, seatPerRow, j);
		}	
	}
	
	private void setWeatherCard(Time time) {
		
		Image image;
		JSONArray info = Weather.getInfo();
		int hour = time.toLocalTime().getHour();
		
		JSONArray hourly = info.getJSONObject(hour).getJSONArray("weather");
		JSONObject weather = hourly.getJSONObject(0);
		
		int hourMod = (hour)%24;
		image = Weather.weatherImage(hourMod, weather.getString("main"));
		
		String h;
		if ((hourMod) < 10) {
			h = "0" + (hourMod);
		}
		else {
			h = Integer.toString(hour);
		}
		
		WeatherCard a;
		try {
			a = new WeatherCard(Weather.kelvinToCelsius(info.getJSONObject(hour).getDouble("temp")) + "°C", image, h + ":00");
			weatherCard.getChildren().add(a);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
