package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.bean.CourseBean;
import logic.controller.AddCourseController;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NewCoursePageView implements Initializable {
	
	@FXML
	private Button btnAddCourse;
	
	@FXML
	private TextField textName;
	
	@FXML
	private TextField textAbbreviation;
	
	@FXML
	private TextField textProfessor;
	
	@FXML
	private TextField textYear;
	
	@FXML
	private TextField textCredits;
	
	@FXML
	private TextField textSemester;
	
	@FXML
	private TextArea textPrerequisites;
	
	@FXML
	private TextArea textReception;
	
	@FXML
	private TextArea textGoal;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnAddCourse.disableProperty().bind(Bindings.isEmpty(textName.textProperty())
				.or(Bindings.isEmpty(textAbbreviation.textProperty()))
				.or(Bindings.isEmpty(textProfessor.textProperty()))
				.or(Bindings.isEmpty(textProfessor.textProperty()))
				.or(Bindings.isEmpty(textCredits.textProperty()))
				.or(Bindings.isEmpty(textSemester.textProperty()))
				.or(Bindings.isEmpty(textPrerequisites.textProperty()))
				.or(Bindings.isEmpty(textReception.textProperty()))
				.or(Bindings.isEmpty(textGoal.textProperty())));
	}
	
	@FXML
	private void addCourse(ActionEvent event) {
		
		CourseBean courseBean = new CourseBean();
		courseBean.setAbbreviation(textAbbreviation.getText());
		courseBean.setCredits(textCredits.getText());
		courseBean.setGoal(textGoal.getText());
		courseBean.setName(textName.getText());
		courseBean.setPrerequisites(textPrerequisites.getText());
		courseBean.setReception(textReception.getText());
		courseBean.setSemester(textSemester.getText());
		courseBean.setYear(textYear.getText());
		
		AddCourseController controller = new AddCourseController();
		
		try {
			controller.addCourse(courseBean);
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
		}
		
		PageLoader.getInstance().buildPage(Page.ADMINISTRATION_PAGE);
	}

}
