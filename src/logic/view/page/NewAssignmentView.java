package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.Session;
import logic.bean.AssignmentBean;
import logic.bean.CourseBean;
import logic.bean.ProfessorBean;
import logic.controller.AddAssignmentController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NewAssignmentView implements Initializable {

	@FXML
	private TextArea textDesc;
	
	@FXML
	private TextField textTitle;
	
	@FXML
	private Button btnSubmit;
	
	@FXML
	private ComboBox<String> comboCourse;
	
	@FXML
	private DatePicker dateDeadline;
	
	private AddAssignmentController addAssignmentController;
	
	private List<CourseBean> courses;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		addAssignmentController = new AddAssignmentController();
		
		btnSubmit.disableProperty().bind(textDesc.textProperty().isEmpty()
				.or(textTitle.textProperty().isEmpty())
				.or(comboCourse.valueProperty().isNull())
				.or(dateDeadline.valueProperty().isNull()));
		
		try {
			ProfessorBean professorBean = new ProfessorBean();
			professorBean.setUsername(Session.getSession().getUsername());
			
			courses = addAssignmentController.getCoursesOfProfessor(professorBean);
			for (CourseBean course : courses) {
				comboCourse.getItems().add(course.getAbbrevation());
			}
			
			
		} catch (RecordNotFoundException e) {
			comboCourse.getItems().add(e.getMessage());
		}
		
	}
	
	@FXML
	private void saveAssignment(ActionEvent event) throws SQLException, IOException {
		AssignmentBean assignmentBean = new AssignmentBean();
		assignmentBean.setTitle(textTitle.getText());
		assignmentBean.setText(textDesc.getText());
		assignmentBean.setDate(Date.valueOf(dateDeadline.getValue()));
		assignmentBean.setCourse(courses.get(comboCourse.getSelectionModel().getSelectedIndex()));
	
		addAssignmentController = new AddAssignmentController();
		if (addAssignmentController.saveAssignment(assignmentBean)) {
			AlertController.infoAlert("Assignment Added Correctly");
		}
		else {
			AlertController.infoAlert("Can't add assignment");
		}
		
		PageLoader.getInstance().buildPage(Page.FORUM, event);
	}
}
