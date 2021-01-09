package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logic.Session;
import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.InsertAnswerController;
import logic.utilities.AlertController;
import logic.utilities.SQLConverter;
import logic.view.card.element.AnswerCard;

public class QuestionPageView implements Initializable{

	@FXML
	private ScrollPane scrollAnswers;
	
	@FXML
	private Label labelSubjectQuestion, labelAuthor, labelDate;
	
	@FXML
	private TextArea textQuestion;
	
	@FXML
	private Button btnAnswer;
	
	@FXML
	private VBox vboxAnswer;
	
	private QuestionBean question;
	private Stage dialogStage;
	private EventHandler<ActionEvent> addAnswerEvent, cancAddAnswerEvent;
	private Label label;
	private TextArea textAnswer;
	private Button btnSubmit, btnCancel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelAuthor.setText("");
	}
	
	public void setBean(Object obj) {
		this.question = (QuestionBean) obj;
		textQuestion.setText(question.getText());
		labelSubjectQuestion.setText(question.getTitle());
		labelAuthor.setText(question.getStudent().getName() + " " + question.getStudent().getSurname());
		labelDate.setText(SQLConverter.date(question.getDate()));
		loadAnswer();
	}

	private void loadAnswer() {
		vboxAnswer.getChildren().clear();
		if (question.getAnswers() == null) {
			vboxAnswer.getChildren().add(new Label("\"No one seems to have a solution. Be the first!\""));
			return;
		}
		
		for (AnswerBean answer : question.getAnswers()) {
			AnswerCard answerCard;
			try {
				
				answerCard = new AnswerCard(answer);
				vboxAnswer.getChildren().add(answerCard);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void addAnswer(ActionEvent ae) throws IOException {
		try {
			setupAnswerDialog();
			
		} catch (IOException e) {
			// TODO 
			e.printStackTrace();
		}
	}

	private void setupAnswerDialog() throws IOException {
		dialogStage = new Stage();
		VBox box = new VBox();
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.initStyle(StageStyle.TRANSPARENT); 
		label = new Label("Answer:");
		textAnswer = new TextArea();
		btnSubmit = new Button("Submit");
		btnCancel = new Button("Cancel");
		btnSubmit.disableProperty().bind(Bindings.isEmpty(textAnswer.textProperty()));
		setupEvent();
		btnSubmit.setOnAction(addAnswerEvent);
		btnCancel.setOnAction(cancAddAnswerEvent);
		HBox hbox = new HBox(btnCancel, btnSubmit);
		hbox.setSpacing(20d);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		box.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15d), Insets.EMPTY)));
		box.getChildren().addAll(label, textAnswer, hbox);
		VBox.setMargin(textAnswer, new Insets(10d));
		VBox.setMargin(hbox, new Insets(10d));
		VBox.setMargin(label, new Insets(10d));
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
	    
		Scene scene = new Scene(box);
		scene.setFill(Color.TRANSPARENT);
		labelAuthor.getScene().getRoot().setEffect(adj);
		scene.getStylesheets().add(QuestionPageView.class.getResource("/res/style/InsertAnswer.css").toExternalForm());
		dialogStage.setScene(scene);
		dialogStage.setTitle("App - Insert Answer");
		dialogStage.setResizable(false);
		
		dialogStage.show();
		animation(dialogStage);
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
		this.addAnswerEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				saveAnswer(textAnswer.getText());
			}
		};
		
		this.cancAddAnswerEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				closeStage(dialogStage);
				labelAuthor.getScene().getRoot().setEffect(null);
			}
		};
	}

	private void saveAnswer(String text) {
		InsertAnswerController controller = new InsertAnswerController();
		
		UserBean userBean = new UserBean();
		userBean.setEmail(Session.getSession().getUserLogged().getEmail());
		userBean.setName(Session.getSession().getUserLogged().getName());
		userBean.setPassword(Session.getSession().getPassword());
		userBean.setSurname(Session.getSession().getUserLogged().getSurname());
		userBean.setUsername(Session.getSession().getUsername());
		
		AnswerBean answer = new AnswerBean();
		answer.setId(question.getId());
		answer.setUser(userBean);
		answer.setText(text);
		answer.setDate(new Date(System.currentTimeMillis()));
		
		try {
			controller.save(answer);
			closeStage(dialogStage);
			AlertController.buildInfoAlert("The answer has been entered correctly!", "", scrollAnswers);
			this.question.addAnswers(answer);
			loadAnswer();
			
		} catch (SQLException e) {
			closeStage(dialogStage);
			AlertController.buildInfoAlert("Something happened, the answer was not acquired..", "Bad news..", scrollAnswers);
		}
	}

	private void closeStage(Stage stage) {
		stage.close();
	}
}
