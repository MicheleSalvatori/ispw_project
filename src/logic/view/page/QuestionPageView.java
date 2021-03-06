package logic.view.page;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.QuestionController;
import logic.controller.AnswerAQuestionController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.card.element.AnswerCard;

public class QuestionPageView {

	@FXML
	private ScrollPane scrollAnswers;

	@FXML
	private Label labelSubjectQuestion;

	@FXML
	private Label labelAuthor;

	@FXML
	private Label labelDate;

	@FXML
	private TextArea textQuestion;

	@FXML
	private Button btnAnswer;

	@FXML
	private VBox vboxAnswer;

	private QuestionBean question;

	private Stage dialogStage;
	private EventHandler<ActionEvent> addAnswerEvent;
	private EventHandler<ActionEvent> cancAddAnswerEvent;

	private QuestionController controller;
	private List<AnswerBean> answersList;

	public void setBean(Object obj) {
		controller = new QuestionController();

		try {
			this.question = controller.getQuestionByID(((QuestionBean) obj).getId());

		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
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
			for (AnswerBean answer : answersList) {
				AnswerCard answerCard;
				answerCard = new AnswerCard(answer);
				vboxAnswer.getChildren().add(answerCard.getPane());
			}

		} catch (RecordNotFoundException e) {
			vboxAnswer.getChildren().add(new Label("No one seems to have a solution. Be the first!"));
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
	}

	@FXML
	private void addAnswer(ActionEvent event) {
		setupAnswerDialog();
	}

	private void setupAnswerDialog() {
		dialogStage = new Stage();

		URL url;
		Parent root = null;

		try {
			url = new File("src/res/fxml/dialog/AnswerDialog.fxml").toURI().toURL();
			root = FXMLLoader.load(url);

		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Page loading error");
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/res/style/dialog/AnswerDialog.css").toExternalForm());
		AlertController.setupDialog(scene, dialogStage);
		dialogStage.setTitle("App - Add an Answer");

		Button btnSubmit = (Button) scene.lookup("#btnSubmit");
		Button btnCancel = (Button) scene.lookup("#btnCancel");

		TextArea textAnswer = (TextArea) scene.lookup("#textAnswer");
		btnSubmit.disableProperty().bind(textAnswer.textProperty().isEmpty());

		setupEvent(textAnswer);
		btnSubmit.setOnAction(addAnswerEvent);
		btnCancel.setOnAction(cancAddAnswerEvent);
	}

	private void setupEvent(TextArea text) {
		
		addAnswerEvent = e -> saveAnswer(text.getText());

		cancAddAnswerEvent = e -> {
			closeStage(dialogStage);
			PageLoader.getStage().getScene().getRoot().setEffect(null);
		};
	}

	private void saveAnswer(String text) {
		AnswerAQuestionController answerController = new AnswerAQuestionController();

		AnswerBean answer = new AnswerBean();
		answer.setId(question.getId());
		answer.setUser(UserBean.getInstance());
		answer.setText(text);
		answer.setDate(new Date(System.currentTimeMillis()));

		try {
			answerController.save(answer);
			closeStage(dialogStage);
			AlertController.infoAlert("The answer has been entered correctly!");
			if (answersList == null) {
				answersList = new ArrayList<>();
				answersList.add(answer);
			}
			loadAnswer();

		} catch (SQLException e) {
			closeStage(dialogStage);
			AlertController.infoAlert("Something happened, the answer was not acquired.");
		}
	}

	private void closeStage(Stage stage) {
		stage.close();
	}
}
