package logic.view.menu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.bean.UserBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NavigationBarView implements Initializable {

	@FXML
	private Button btnHome;
	
	@FXML
	private Button btnExams;
	
	@FXML
	private Button btnProfile;
	
	@FXML
	private Button btnForum;
	
	@FXML
	private Button btnNews;
	
	@FXML
	private Button btnSettings;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnRequest;
	
	@FXML
	private Button btnSchedule;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		switch (UserBean.getInstance().getRole()) {
		
		case STUDENT:
			// User logged as Student
			btnRequest.setVisible(false);
			btnSchedule.setVisible(false);
			btnSettings.setVisible(false);
			break;
			
		case PROFESSOR:
			// User logged as Professor
			btnExams.setVisible(false);
			btnSettings.setVisible(false);
			break;
			
		case ADMIN:
			// User logged as Professor
			btnRequest.setVisible(false);
			btnSchedule.setVisible(false);
			btnExams.setVisible(false);
			btnHome.setVisible(false);
			btnForum.setVisible(false);
			btnProfile.setVisible(false);
			btnNews.setVisible(false);
			break;
			
		default:
			break;
		}
	}
	
	@FXML
	public void settingsButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.ADMINISTRATION_PAGE);
	}

	@FXML
	public void homeButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.HOMEPAGE);
	}

	@FXML
	public void newsButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.COMMUNICATIONS);
	}

	@FXML
	public void profileButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.PROFILE);
	}

	@FXML
	public void examsButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.EXAM);
	}

	@FXML
	public void forumButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.FORUM);
	}

	@FXML
	public void backButton(ActionEvent event) {
		PageLoader.getInstance().goBack();
	}
	
	@FXML
	public void requestButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.REQUEST);
	}
	
	@FXML
	public void scheduleButton(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.SCHEDULE);
	}
}
