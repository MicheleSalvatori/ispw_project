package logic.view.page;

import java.io.IOException;
import java.sql.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.controller.BookSeatController;
import logic.model.Classroom;
import logic.model.Course;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.utilities.Weather;
import logic.view.card.element.WeatherCard;

public class LessonPageView {

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

	private LessonBean lesson;
	private ClassroomBean classroom;
	private EventHandler<ActionEvent> seatEvent;
	private BookSeatController controlSeat;

	public void setBean(Object lesson) {
		controlSeat = new BookSeatController();
		this.lesson = (LessonBean) lesson;
		this.classroom = this.lesson.getClassroomBean();
		setPage();
	}

	public void setPage() {
		btnCourse.setText(lesson.getCourse().getAbbrevation());
		labelClassroom.setText(lesson.getClassroom().getName());
		labelTime.setText(SQLConverter.time(lesson.getTime()));
		labelDate.setText(SQLConverter.date(lesson.getDate()));
		labelProfessor.setText(lesson.getProfessor().getName() + " " + lesson.getProfessor().getSurname());
		textTopic.setText(lesson.getTopic());

		setupEvent();
		setWeatherCard(lesson.getTime());
		setupRoom();
	}

	@FXML
	private void course(ActionEvent event) throws IOException {
		CourseBean courseBean= lesson.getCourse();
		PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}

	private void setupEvent() {
		seatEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btnSeat = (Button) event.getSource();
				int row = GridPane.getRowIndex(btnSeat) * classroom.getSeatColumn();
				int col = GridPane.getColumnIndex(btnSeat) + 1;
				int seatId = row + col;
				SeatBean seat = new SeatBean();
				seat.setId(seatId);
				seat.setClassroomName(classroom.getName());
				bookSeat(seat, btnSeat);
			}
		};
	}

	private void bookSeat(SeatBean seat, Button btn) {
		try {
			controlSeat.occupateSeat(seat);
			classroom.getSeats().get(seat.getId()-1).occupateSeat();
			btn.setStyle("-fx-background-color: #3899D0;");
//			AlertConfirmation
		} catch (SQLException e) {
//			AlerControl nuova
		}
		
	}

	private void setupRoom() {
		int numRow = classroom.getSeatRow();
		int seatPerRow = classroom.getSeatColumn();
		System.out.println(numRow + " " + seatPerRow);
		GridPane gridSeat = new GridPane();
		paneSeat.getChildren().add(gridSeat);
		AnchorPane.setTopAnchor(gridSeat, 0d);
		AnchorPane.setLeftAnchor(gridSeat, 0d);
		AnchorPane.setRightAnchor(gridSeat, 0d);

		scrollPane.setFitToWidth(true);

		gridSeat.setVgap(10);

		gridSeat.setAlignment(Pos.TOP_CENTER);

		for (int i = 0; i < numRow; i++) {

			for (int j = 0; j < seatPerRow; j++) {

				Button btn = new Button(j + 1 + "");
				btn.getStylesheets().add("/res/style/SeatButton.css");
				btn.setMaxWidth(1);
				btn.setOnAction(seatEvent);
				gridSeat.add(btn, j, i);

				GridPane.setFillHeight(btn, false);
				GridPane.setHgrow(btn, Priority.ALWAYS);
				GridPane.setVgrow(btn, Priority.ALWAYS);
				GridPane.setHalignment(btn, HPos.CENTER);
				setupSeatStatus(btn);
			}
		}

		for (int j = 0; j < numRow; j++) {
			Label label = new Label(String.valueOf((char) (65 + j)));
			gridSeat.add(label, seatPerRow, j);
		}
	}

	private void setupSeatStatus(Button buttonSeat) {
		int seatID = (GridPane.getColumnIndex(buttonSeat) + 1)
				+ (GridPane.getRowIndex(buttonSeat) * classroom.getSeatColumn());
		System.out.println("INDEX: "+seatID);
		
		if (!classroom.getSeats().get(seatID-1).getState()) {
			buttonSeat.getStyleClass().remove("button");
			buttonSeat.getStyleClass().add("bookead-seat");
			buttonSeat.setDisable(true);
		}

	private void setWeatherCard(Time time) {

		Image image;
		JSONArray info = Weather.getInfo();
		int hour = time.toLocalTime().getHour();

		JSONArray hourly = info.getJSONObject(hour).getJSONArray("weather");
		JSONObject weather = hourly.getJSONObject(0);

		int hourMod = (hour) % 24;
		image = Weather.weatherImage(hourMod, weather.getString("main"));

		String h;
		if ((hourMod) < 10) {
			h = "0" + (hourMod);
		} else {
			h = Integer.toString(hour);
		}

		WeatherCard a;
		try {
			a = new WeatherCard(Weather.kelvinToCelsius(info.getJSONObject(hour).getDouble("temp")) + String.valueOf(248) + "C", image, h + ":00");			weatherCard.getChildren().add(a);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}