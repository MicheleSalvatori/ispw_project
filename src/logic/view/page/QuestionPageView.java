package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Session;
import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.controller.InsertAnswerController;
import logic.utilities.AlertController;
import logic.view.card.element.AnswerCard;

public class QuestionPageView implements Initializable {

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
	private QuestionBean bean;
	private List<AnswerBean> answers;
	private Stage dialogStage;
	private EventHandler<ActionEvent> addAnswerEvent;
	private Label label;
	private TextArea textAnswer;
	private Button btnSubmit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setBean(Object obj) {
		this.bean = (QuestionBean) obj;
		textQuestion.setText(bean.getText());
		labelSubjectQuestion.setText(bean.getTitle());
		String author = bean.getStudent().getName() + " " + bean.getStudent().getSurname();
		labelAuthor.setText(author);
		labelDate.setText(bean.getDate().toString().replaceAll("-", "/"));
		loadAnswer();
	}

	private void loadAnswer() {
		vboxAnswer.getChildren().removeAll(vboxAnswer.getChildren());
		answers = bean.getAnswers();
		if (answers == null) {
			vboxAnswer.getChildren().add(new Label("\"No one seems to have a solution. Be the first!"));
			return;
		}
		for (AnswerBean a : answers) {
			AnswerCard answerCard;
			try {
				String student = a.getUser().getName() + " " + a.getUser().getSurname();
				answerCard = new AnswerCard(student, a.getDate().toString(), a.getText());
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
			// Sollevata da IOException per file css
		}
	}

	private void setupAnswerDialog() throws IOException {
		dialogStage = new Stage();
		VBox box = new VBox();
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		label = new Label("Answer:");
		textAnswer = new TextArea();
		btnSubmit = new Button("Submit");
		btnSubmit.disableProperty().bind(Bindings.isEmpty(textAnswer.textProperty()));
		setupEvent();
		btnSubmit.setOnAction(addAnswerEvent);
		HBox hbox = new HBox(btnSubmit);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		box.getChildren().add(label);
		box.getChildren().add(textAnswer);
		box.getChildren().add(hbox);
		VBox.setMargin(textAnswer, new Insets(10d));
		VBox.setMargin(hbox, new Insets(10d));
		VBox.setMargin(label, new Insets(10d));

		Scene scene = new Scene(box);
//		da errori il caricamento dei font ma funziona uguale
		scene.getStylesheets()
				.add(QuestionPageView.class.getResource("../../../res/style/InsertAnswer.css").toExternalForm());
		dialogStage.setScene(scene);
		dialogStage.setTitle("App - Insert Answer");
		dialogStage.setResizable(false);
		dialogStage.show();
	}

	private void setupEvent() {
		this.addAnswerEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				saveAnswer(textAnswer.getText());
			}
		};
	}

	private void saveAnswer(String text) {
		InsertAnswerController controller = new InsertAnswerController();
		AnswerBean answer = new AnswerBean();
		answer.setId(bean.getId());
		answer.setUser(Session.getSession().getUserLogged());
		answer.setText(text);
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		answer.setDate(date);
		try {
			controller.save(answer);
			closeStage(dialogStage);
			AlertController.buildInfoAlert("The answer has been entered correctly!", "", scrollAnswers);
			this.bean.addAnswers(answer);
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
