package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import logic.bean.AssignmentBean;
import logic.bean.CourseBean;
import logic.bean.UserBean;
import logic.controller.AddAssignmentController;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.utilities.SQLConverter;

public class AssignmentPageView implements Initializable {

	@FXML
	private Label labelDeadline;
	
	@FXML
	private Label labelTitle;
	
	@FXML
	private TextArea textDesc;
	
	@FXML
	private Button btnCourse;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnRemove;
	
	@FXML
	private AnchorPane anchorDeliver;
	
	private AssignmentBean assignment;

	
	@FXML
	private void course(ActionEvent event) {
		CourseBean courseBean = new CourseBean();
		courseBean.setAbbreviation(assignment.getCourse());
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
			anchorDeliver.setVisible(false);
			btnAdd.setVisible(false);
			btnRemove.setVisible(false);
		}
	}
	
	public void setBean(Object obj) {
		AddAssignmentController controller = new AddAssignmentController();
		try {
			this.assignment = controller.getAssignmentByID(((AssignmentBean) obj).getId());
		
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().buildPage(Page.HOMEPAGE);
		}

		setPage();
	}
	
	private void setPage() {
		btnCourse.setText(assignment.getCourse());
		labelDeadline.setText(SQLConverter.date(assignment.getDate()));
		labelTitle.setText(assignment.getTitle());
		textDesc.setText(assignment.getText());
	}
}