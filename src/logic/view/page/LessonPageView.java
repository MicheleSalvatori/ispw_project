package logic.view.page;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import logic.exceptions.DuplicatedRecordException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.utilities.Weather;
import logic.view.card.element.WeatherCard;

public class LessonPageView {

	@FXML
	private Button btnCourse;

	@FXML
	private Label labelProfessor, labelClassroom, labelTime, labelDate;

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
	private GridPane gridSeat;
	private SeatBean mySeat;
	private ObservableList<Node> buttonList;

	private enum SeatState {
		FREE, BOOKED, YOUR
	};
	
	@FXML
	private void course(ActionEvent event) throws IOException, SQLException {
		CourseBean courseBean = lesson.getCourse();
		PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}

	public void setBean(Object lesson) {
		controlSeat = new BookSeatController();
		this.lesson = (LessonBean) lesson;
		try {
			this.classroom = this.lesson.getClassroom();
			this.classroom.setSeat(controlSeat.getOccupateSeatOf(this.lesson)); // mettiamo nella classroom SOLO i posti
																				// occupati
			this.mySeat = controlSeat.getMySeat(this.lesson);
			setPage();
		} catch (SQLException e) {
			System.out.println("CATCH");
		}
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

	private void setupEvent() {
		seatEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btnSeat = (Button) event.getSource();
				switch (btnSeat.getId()) {
				case "your-seat":
					if (AlertController.confirmationAlert("Are you sure you want to free your seat?")) {
						freeSeat(btnSeat);
					}
					break;
				case "free-seat":
					if (AlertController.confirmationAlert("Are you sure you want to book this seat?")) {
						bookSeat(btnSeat);
					}
					break;
				default:
					break;
				}
			}
		};
	}

	private void freeSeat(Button buttonSeat) {
		try {
			controlSeat.freeSeat(mySeat, this.lesson);
			changeState(getSeat(buttonSeat), SeatState.FREE);
			mySeat = null;
		} catch (SQLException e) {
			AlertController.infoAlert("The system was unable to accommodate your request, please try again!");
		}
	}

	private void bookSeat(Button buttonSeat) {
		SeatBean seatToBook = getSeat(buttonSeat);
		SeatBean newSeat = null;
		try {
			newSeat = controlSeat.occupateSeat(seatToBook, lesson);
			if (mySeat != null) {
				changeState(mySeat, SeatState.FREE);
			}
			mySeat = newSeat;
			changeState(mySeat, SeatState.YOUR);
		} catch (SQLException e) {
			AlertController.infoAlert("Something bad happened, booking failed, please try again!");
		} catch (DuplicatedRecordException e) {
			AlertController.infoAlert(e.getMessage());
			changeState(seatToBook, SeatState.BOOKED);
		}
	}

	private void setupRoom() {
		int numRow = classroom.getSeatRow();
		int seatPerRow = classroom.getSeatColumn();
		System.out.println(numRow + " " + seatPerRow);
		gridSeat = new GridPane();
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
				btn.setId("free-seat");
				btn.setMaxWidth(1);
				btn.setOnAction(seatEvent);
				gridSeat.add(btn, j, i);

				GridPane.setFillHeight(btn, false);
				GridPane.setHgrow(btn, Priority.ALWAYS);
				GridPane.setVgrow(btn, Priority.ALWAYS);
				GridPane.setHalignment(btn, HPos.CENTER);
			}
		}

		for (int j = 0; j < numRow; j++) {
			Label label = new Label(String.valueOf((char) (65 + j)));
			gridSeat.add(label, seatPerRow, j);
		}
		setupSeatStatus();
	}

	private void setupSeatStatus() {
		buttonList = gridSeat.getChildren();
		if (classroom.getSeat() != null) {
			for (SeatBean s : classroom.getSeat()) {
				if (mySeat != null && s.getId() == mySeat.getId()) {
					changeState(s, SeatState.YOUR);
				} else {
					changeState(s, SeatState.BOOKED);
				}
			}
		}
	}

	private SeatBean getSeat(Button button) {
		int row = GridPane.getRowIndex(button) * classroom.getSeatColumn();
		int col = GridPane.getColumnIndex(button) + 1;
		System.out.println(row + col);
		SeatBean seat = new SeatBean(row + col, lesson.getClassroom().getName());
		return seat;
	}

	private void changeState(SeatBean seat, SeatState state) {
		Button button = (Button) buttonList.get(seat.getId() - 1);
		System.out.println(state);
		switch (state) {
		case FREE:
			button.setId("free-seat");
			button.setDisable(false);
			break;
		case BOOKED:
			button.setId("booked-seat");
			button.setDisable(true);
			break;
		case YOUR:
			button.setId("your-seat");
			button.setDisable(false);
			break;
		}
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
			a = new WeatherCard(
					Weather.kelvinToCelsius(info.getJSONObject(hour).getDouble("temp")) + String.valueOf(248) + "C",
					image,

					h + ":00");
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