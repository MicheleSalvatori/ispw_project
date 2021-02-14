package logic.view.page;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

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
import logic.bean.UserBean;
import logic.controller.BookASeatController;
import logic.controller.CheckWeatherController;
import logic.controller.ViewNextLessonController;
import logic.exceptions.SeatAlreadyBookedException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.utilities.SQLConverter;
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
	private BookASeatController controlSeat;
	private GridPane gridSeat;
	private SeatBean mySeat;
	private ObservableList<Node> buttonList;

	private static final String FREE_SEAT = "free-seat";
	private static final String YOUR_SEAT = "your-seat";
	private static final String BOOKED_SEAT = "booked-seat";

	private enum SeatState {
		FREE, BOOKED, YOUR
	}

	@FXML
	private void course(ActionEvent event) {
		CourseBean courseBean = new CourseBean();
		courseBean.setAbbreviation(lesson.getCourse());
		PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}

	public void setBean(Object lesson) {

		ViewNextLessonController lessonController = new ViewNextLessonController();
		controlSeat = new BookASeatController();

		try {
			this.lesson = lessonController.getLesson((LessonBean) lesson);
			this.classroom = this.lesson.getClassroom();
			this.classroom.setSeat(controlSeat.getOccupateSeatOf(this.lesson));
			if (UserBean.getInstance().getRole() == Role.STUDENT) {
				this.mySeat = controlSeat.getMySeat(this.lesson, UserBean.getInstance());
			}else {
				mySeat = null;
			}
			setPage();

		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
	}

	public void setPage() {
		btnCourse.setText(lesson.getCourse());
		labelClassroom.setText(lesson.getClassroom().getName());
		labelTime.setText(SQLConverter.time(lesson.getTime()));
		labelDate.setText(SQLConverter.date(lesson.getDate()));
		labelProfessor.setText(lesson.getProfessor().getName() + " " + lesson.getProfessor().getSurname());
		textTopic.setText(lesson.getTopic());

		setupEvent();
		setWeatherCard(lesson.getTime().toString());
		setupRoom();
	}

	private void setupEvent() {
		seatEvent = event -> {
			Button btnSeat = (Button) event.getSource();
			switch (btnSeat.getId()) {

			case YOUR_SEAT:
				if (AlertController.confirmationAlert("Are you sure you want to free your seat?")) {
					freeSeat(btnSeat);
				}
				break;

			case FREE_SEAT:
				if (AlertController.confirmationAlert("Are you sure you want to book this seat?")) {
					bookSeat(btnSeat);
				}
				break;

			default:
				break;
			}
		};
	}

	private void freeSeat(Button buttonSeat) {
		try {
			controlSeat.freeSeat(mySeat, this.lesson, UserBean.getInstance());
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
			newSeat = controlSeat.occupateSeat(seatToBook, lesson, UserBean.getInstance());
			if (mySeat != null) {
				changeState(mySeat, SeatState.FREE);
			}
			mySeat = newSeat;
			changeState(mySeat, SeatState.YOUR);

		} catch (SQLException e) {
			AlertController.infoAlert("Something bad happened, booking failed, please try again!");

		} catch (SeatAlreadyBookedException e) {
			AlertController.infoAlert(e.getMessage());
			changeState(seatToBook, SeatState.BOOKED);
		}
	}

	private void setupRoom() {
		int numRow = classroom.getSeatRow();
		int seatPerRow = classroom.getSeatColumn();
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
				btn.setId(FREE_SEAT);
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
				} else if (!s.isFree()) {
					changeState(s, SeatState.BOOKED);
				}
			}
		}
		
		if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
			for (Node n : buttonList) {
				if (n instanceof Button) {
					((Button) n).setDisable(true);
				}
			}
		}
	}

	private SeatBean getSeat(Button button) {
		int row = GridPane.getRowIndex(button) * classroom.getSeatColumn();
		int col = GridPane.getColumnIndex(button) + 1;
		return new SeatBean(row + col, lesson.getClassroom().getName());
	}

	private void changeState(SeatBean seat, SeatState state) {
		Button button = (Button) buttonList.get(seat.getId() - 1);

		switch (state) {

		case FREE:
			button.setId(FREE_SEAT);
			button.setDisable(false);
			break;

		case BOOKED:
			button.setId(BOOKED_SEAT);
			button.setDisable(true);
			break;

		case YOUR:
			button.setId(YOUR_SEAT);
			button.setDisable(false);
			break;
		}
	}

	private void setWeatherCard(String time) {
		int hour = Integer.parseInt(time.substring(0, 2));
		CheckWeatherController controller = new CheckWeatherController();

		List<String> info = controller.getWeather(hour);
		Image image = new Image(new File("src/res/png/weather/" + info.get(1)).toURI().toString());
		WeatherCard w = new WeatherCard(info.get(0), image, info.get(2));
		weatherCard.getChildren().add(w.getPane());
	}
}