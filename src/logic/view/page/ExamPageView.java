package logic.view.page;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.StudentBean;
import logic.bean.VerbalizedBean;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.NullException;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.view.card.element.ExamCard;

public class ExamPageView implements Initializable {

	@FXML
	private Label labelVE, labelGPA, labelWPA;

	@FXML
	private VBox vboxExam;
	
	@FXML
	private Button btnAddExam;
	
	private ViewVerbalizedExamsController controller;

	private Stage dialogStage;
	private List<VerbalizedBean> verbs;
	private List<CourseBean> courses;
	
	private EventHandler<ActionEvent> addExamEvent, cancAddExamEvent;
	private ComboBox<Integer> comboGrade;
	private ComboBox<String> comboCourse;
	private Button btnSubmit, btnCancel;
	private DatePicker dateVerb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		controller = new ViewVerbalizedExamsController();

		try {
			verbs = controller.getVerbalizedExams();
			for (VerbalizedBean verbBean : verbs) {
				ExamCard examCard = new ExamCard(verbBean, verbs.indexOf(verbBean) + 1);
				vboxExam.getChildren().add(examCard);
			}
			
		} catch (NullException e) {
			verbs = new ArrayList<>();
			vboxExam.getChildren().add(new Label("No exam found"));
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup media
		labelVE.setText(Integer.toString(verbs.size()));
		labelGPA.setText(Double.toString(controller.GPA(verbs)));
		labelWPA.setText(Double.toString(controller.WPA(verbs)));
	}
	
	@FXML
	private void addExam(ActionEvent event) throws IOException, SQLException {
		controller = new ViewVerbalizedExamsController();
		
		try {
			courses = controller.getCourses();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (NullException e) {
			courses = new ArrayList<>();
		}
		
		List<Integer> grades = IntStream.rangeClosed(18, 30).boxed().collect(Collectors.toList());

		setupExamDialog(courses, grades);
	}
	
	private void setupExamDialog(List<CourseBean> courses, List<Integer> grades) throws IOException {
		dialogStage = new Stage();
		
		URL url = new File("src/res/fxml/dialog/ExamDialog.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(QuestionPageView.class.getResource("/res/style/dialog/ExamDialog.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);
		
		dialogStage.setScene(scene);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.initStyle(StageStyle.TRANSPARENT);
		dialogStage.setResizable(false);
		dialogStage.setTitle("App - Insert Exam");
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		
		labelVE.getScene().getRoot().setEffect(adj);
		dialogStage.show();
		animation(dialogStage);
		
		btnSubmit = (Button) scene.lookup("#btnSubmit");
		btnCancel = (Button) scene.lookup("#btnCancel");
		
		AnchorPane course = (AnchorPane) scene.lookup("#paneCourse");
		AnchorPane grade = (AnchorPane) scene.lookup("#paneGrade");
		
		comboCourse = new ComboBox<String>();
		comboGrade = new ComboBox<Integer>();
		
		comboCourse.setPromptText("Course");
		comboGrade.setPromptText("Grade");
		
		course.getChildren().add(comboCourse);
		grade.getChildren().add(comboGrade);
		
		dateVerb = (DatePicker) scene.lookup("#dateVerb");
		
		btnSubmit.disableProperty().bind(comboCourse.valueProperty().isNull()
									.or(comboGrade.valueProperty().isNull())
									.or(dateVerb.valueProperty().isNull()));
		
		setupEvent();
		btnSubmit.setOnAction(addExamEvent);
		btnCancel.setOnAction(cancAddExamEvent);

		for (CourseBean courseBean : courses) {
			comboCourse.getItems().add(courseBean.getAbbrevation());
		}
		
		for (Integer g : grades) {
			comboGrade.getItems().add(g);
		}

	}
	
	private void animation(Stage stage) {
		double yIni = -stage.getHeight();
		double yEnd = stage.getY();
		
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->stage.setY(n1.doubleValue()));
		
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
	
	private void setupEvent() {
		this.addExamEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				saveExam();
			}
		};
		
		this.cancAddExamEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dialogStage.close();
				labelVE.getScene().getRoot().setEffect(null);	//TODO
			}
		};
	}
	
	private void saveExam() {
		
		controller = new ViewVerbalizedExamsController();
		
		StudentBean studentBean = new StudentBean();
		studentBean.setEmail(Session.getSession().getUserLogged().getEmail());
		studentBean.setName(Session.getSession().getUserLogged().getName());
		studentBean.setPassword(Session.getSession().getPassword());
		studentBean.setSurname(Session.getSession().getUserLogged().getSurname());
		studentBean.setUsername(Session.getSession().getUsername());
		
		CourseBean courseBean = courses.get(comboCourse.getSelectionModel().getSelectedIndex());
		
		VerbalizedBean verb = new VerbalizedBean();
		verb.setCourse(courseBean);
		verb.setDate(Date.valueOf(dateVerb.getValue()));
		verb.setGrade(comboGrade.getValue());
		verb.setStudent(studentBean);
		
		try {
			controller.saveVerbalizedExam(verb);
			dialogStage.close();
			AlertController.buildInfoAlert("The exam has been entered correctly!", "", labelVE);
			verbs.add(verb);
			updateVerbalizedExams();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteVerbalizedExam(VerbalizedBean verb) throws IOException {
		controller = new ViewVerbalizedExamsController();
		controller.deleteVerbalizedExam(verb);
		verbs.remove(verb);
		updateVerbalizedExams();
	}
	
	private void updateVerbalizedExams() throws IOException {
		vboxExam.getChildren().clear();
		
		if (verbs.isEmpty()) {
			vboxExam.getChildren().add(new Label("No exam found"));
			labelVE.setText("0");
			labelGPA.setText("0");
			labelWPA.setText("0.0");
			return;
		}
		
		for (VerbalizedBean verbBean : verbs) {
			ExamCard examCard = new ExamCard(verbBean, verbs.indexOf(verbBean) + 1);
			vboxExam.getChildren().add(examCard);
		}
		
		controller = new ViewVerbalizedExamsController();
		
		// Setup media
		labelVE.setText(Integer.toString(verbs.size()));
		labelGPA.setText(Double.toString(controller.GPA(verbs)));
		labelWPA.setText(Double.toString(controller.WPA(verbs)));
	}
}