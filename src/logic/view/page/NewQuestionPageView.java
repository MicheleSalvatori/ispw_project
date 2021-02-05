package logic.view.page;

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
import logic.controller.AskAQuestionController;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NewQuestionPageView implements Initializable {

	@FXML
	private TextArea textQuestion;

	@FXML
	private TextField textSubject;

	@FXML
	private Button btnSubmit;

	@FXML
	private ComboBox<String> courseComboBox;
	
	AskAQuestionController controller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = new AskAQuestionController();
		btnSubmit.disableProperty().bind(Bindings.isEmpty(textQuestion.textProperty())
				.or(Bindings.isEmpty(textSubject.textProperty())).or((courseComboBox.valueProperty().isNull())));
		
		try {
			List<CourseBean> courses = controller.getCoursesOfStudent(UserBean.getInstance());
			for (CourseBean c : courses) {
				courseComboBox.getItems().add(c.getAbbreviation());
			}
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
	}

	@FXML
	private void saveQuestion(ActionEvent event) {
		QuestionBean questionBean = new QuestionBean();
		String questionSubject = textSubject.getText();
		String questionText = textQuestion.getText();
		
		StudentBean studentBean = new StudentBean();
		studentBean.setEmail(UserBean.getInstance().getEmail());
		studentBean.setName(UserBean.getInstance().getName());
		studentBean.setPassword(UserBean.getInstance().getPassword());
		studentBean.setSurname(UserBean.getInstance().getSurname());
		studentBean.setUsername(UserBean.getInstance().getUsername());

		questionBean.setStudent(studentBean);
		questionBean.setText(questionText);
		questionBean.setTitle(questionSubject);
		questionBean.setCourse(courseComboBox.getValue());
		
		try {
			controller.save(questionBean);
			AlertController.infoAlert("Your question has been correctly entered");
			
		} catch (SQLException e) {
			AlertController.infoAlert("Something happened, your question was not acquired..");
			
		} finally {
			PageLoader.getInstance().buildPage(Page.FORUM);
		}
	}

}
