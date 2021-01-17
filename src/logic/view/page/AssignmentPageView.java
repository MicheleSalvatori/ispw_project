package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.utilities.SQLConverter;

public class AssignmentPageView implements Initializable {

	@FXML
	private Label labelDeadline, labelTitle;
	
	@FXML
	private TextArea textDesc;
	
	@FXML
	private Button btnCourse, btnAdd, btnRemove;
	
	@FXML
	private AnchorPane anchorDeliver;
	
	private AssignmentBean assignment;
	
	@FXML
	private void addFile(ActionEvent event) {
		
		// TODO vedere se salvare file (penso di no)
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.pdf", "pdf");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}
	}
	
	@FXML
	private void course(ActionEvent event) throws IOException {
		CourseBean courseBean = assignment.getCourse();
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
	
	
	//TODO
	// public void setBean(AssignmentBean bean) {  (il cast lo fai dal pageloader
	
	
	public void setBean(Object obj) {
		this.assignment = (AssignmentBean) obj;		// TODO Forse high coupling
		setPage();
	}
	
	private void setPage() {
		btnCourse.setText(assignment.getCourse().getAbbrevation());
		labelDeadline.setText(SQLConverter.date(assignment.getDate()));
		labelTitle.setText(assignment.getTitle());
		textDesc.setText(assignment.getText());
	}
}