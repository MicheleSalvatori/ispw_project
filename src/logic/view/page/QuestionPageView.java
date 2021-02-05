package logic.view.page;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.AllQuestionController;
import logic.controller.InsertAnswerController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.PageLoader;
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
	private TextArea textAnswer;
	private Button btnSubmit, btnCancel;
	private AllQuestionController controller; 
	private List<AnswerBean> answersList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelAuthor.setText("");
	}
	
	public void setBean(Object obj) {
		controller = new AllQuestionController();
		try {
			this.question = controller.getQuestionByID(((QuestionBean) obj).getId());
		} catch (SQLException | RecordNotFoundException e) {
			System.out.println("CATCHHHH");
		}
		textQuestion.setText(question.getText());
		labelSubjectQuestion.setText(question.getTitle());
		labelAuthor.setText(question.getStudent().getName() + " " + question.getStudent().getSurname());
		labelDate.setText(SQLConverter.date(question.getDate()));
		loadAnswer();
	}

	private void loadAnswer() {
		vboxAnswer.getChildren().clear();
		try {
			answersList = controller.getAnswersOf(question.getId());
		} catch (RecordNotFoundException e1) {
			System.out.println("ENTRO");
			vboxAnswer.getChildren().add(new Label("No one seems to have a solution. Be the first!"));
			return;
		}
		
		for (AnswerBean answer : answersList) {
			AnswerCard answerCard;
			try {
				
				answerCard = new AnswerCard(answer);
				vboxAnswer.getChildren().add(answerCard.getPane());
				
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
		
		URL url = new File("src/res/fxml/dialog/AnswerDialog.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(QuestionPageView.class.getResource("/res/style/dialog/AnswerDialog.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);
		
		dialogStage.setScene(scene);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.initStyle(StageStyle.TRANSPARENT);
		dialogStage.setResizable(false);
		dialogStage.setTitle("App - Insert Answer");
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
	    
	    PageLoader.getStage().getScene().getRoot().setEffect(adj);
		dialogStage.show();
		animation(dialogStage);
		
		btnSubmit = (Button) scene.lookup("#btnSubmit");
		btnCancel = (Button) scene.lookup("#btnCancel");
		
		textAnswer = (TextArea) scene.lookup("#textAnswer");
		
		btnSubmit.disableProperty().bind(textAnswer.textProperty().isEmpty());
		
		setupEvent();
		btnSubmit.setOnAction(addAnswerEvent);
		btnCancel.setOnAction(cancAddAnswerEvent);
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
				PageLoader.getStage().getScene().getRoot().setEffect(null);
			}
		};
	}

	private void saveAnswer(String text) {
		InsertAnswerController controller = new InsertAnswerController();
		
		AnswerBean answer = new AnswerBean();
		answer.setId(question.getId());
		answer.setUser(UserBean.getInstance());
		answer.setText(text);
		answer.setDate(new Date(System.currentTimeMillis()));
		
		try {
			controller.save(answer);
			closeStage(dialogStage);
			AlertController.infoAlert("The answer has been entered correctly!");
			if (answersList == null) {
				answersList = new ArrayList<>();
				answersList.add(answer);
			}
			loadAnswer();
			
		} catch (SQLException e) {
			closeStage(dialogStage);
			AlertController.infoAlert("Something happened, the answer was not acquired..");
		}
	}

	private void closeStage(Stage stage) {
		stage.close();
	}
}
