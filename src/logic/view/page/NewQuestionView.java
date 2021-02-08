package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.InsertQuestionController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NewQuestionView implements Initializable {

	@FXML
	private TextArea textQuestion;

	@FXML
	private TextField textSubject;

	@FXML
	private Button btnSubmit;

	@FXML
	private ComboBox<String> courseComboBox;
	
	private String questionText, questionSubject;
	private QuestionBean questionBean;
	private InsertQuestionController controller;
	private List<CourseBean> courses;

	// TODO mettere il controllo sui numeri dei corso nel forum page e bloccare nel
	// caso il tasto new question
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = new InsertQuestionController();
		btnSubmit.disableProperty().bind(Bindings.isEmpty(textQuestion.textProperty())
				.or(Bindings.isEmpty(textSubject.textProperty())).or((courseComboBox.valueProperty().isNull())));
		
		try {
			courses = controller.getCoursesOfStudent(UserBean.getInstance());
			for (CourseBean c : courses) {
				courseComboBox.getItems().add(c.getAbbreviation());
			}
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void saveQuestion(ActionEvent event) {
		questionBean = new QuestionBean();
		this.questionSubject = textSubject.getText();
		this.questionText = textQuestion.getText();
		
		StudentBean studentBean = new StudentBean();
		studentBean.setEmail(UserBean.getInstance().getEmail());
		studentBean.setName(UserBean.getInstance().getName());
		studentBean.setPassword(UserBean.getInstance().getPassword());
		studentBean.setSurname(UserBean.getInstance().getSurname());
		studentBean.setUsername(UserBean.getInstance().getUsername());

		questionBean.setStudent(studentBean);
		questionBean.setText(questionText);
		questionBean.setTitle(questionSubject);
//		questionBean.setCourseByAbbr(courseComboBox.getValue());
		questionBean.setCourse(courseComboBox.getValue());
		
		try {
			controller.save(questionBean);
			AlertController.infoAlert("Your question has been correctly entered");
		} catch (SQLException e) {
			AlertController.infoAlert("Something happened, your question was not acquired..");
		} finally {
			try {
				PageLoader.getInstance().buildPage(Page.FORUM, event);
				
			} catch (IOException e) {
				// errore caricamento fxml capire come gestirla. Conviene gestirla nel
				// pageLoader
			}
		}
	}

}
