package logic.view.menu.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.Session;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;

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
	private Button btnBack;
	@FXML
	private Button btnRequest;
	@FXML
	private Button btnSchedule;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// User logged as Student
		if (Session.getSession().getRole() == Role.STUDENT) {
			btnRequest.setVisible(false);
			btnSchedule.setVisible(false);
		}
		
		// User logged as Professor
		else if (Session.getSession().getRole() == Role.PROFESSOR){
			btnExams.setVisible(false);
		}
	}

	@FXML
	public void homeButton(ActionEvent event) throws IOException {
		System.out.println("HomeButton");
		PageLoader.getInstance().buildPage(Page.HOMEPAGE, event, null);
	}

	@FXML
	public void newsButton(ActionEvent event) throws IOException {
		System.out.println("NewsButton");
		PageLoader.getInstance().buildPage(Page.NEWS, event, null);
	}

	@FXML
	public void profileButton(ActionEvent event) throws IOException {
		System.out.println("ProfileButton");
		PageLoader.getInstance().buildPage(Page.PROFILE, event, null);
	}

	@FXML
	public void examsButton(ActionEvent event) throws IOException {
		System.out.println("ExamsButton");
		PageLoader.getInstance().buildPage(Page.EXAM, event, null);
	}

	@FXML
	public void forumButton(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.FORUM, event, null);
	}

	@FXML
	public void backButton(ActionEvent event) throws IOException {
		System.out.println("Back");
	}
	
	@FXML
	public void requestButton(ActionEvent event) throws IOException {
		System.out.println("Request");
		PageLoader.getInstance().buildPage(Page.REQUEST, event, null);
	}
	
	@FXML
	public void scheduleButton(ActionEvent event) throws IOException {
		System.out.println("Schedule");
		PageLoader.getInstance().buildPage(Page.SCHEDULE, event, null);
	}
}
