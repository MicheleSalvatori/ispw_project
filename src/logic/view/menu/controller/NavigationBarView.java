package logic.view.menu.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
	private Button btnBack;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@FXML
	public void homeButton(ActionEvent event) throws IOException {
		System.out.println("HomeButton");
		PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
	}

	@FXML
	public void newsButton(ActionEvent event) throws IOException {
		System.out.println("NewsButton");
		PageLoader.getInstance().buildPage(Page.NEWS, event);
	}

	@FXML
	public void profileButton(ActionEvent event) throws IOException {
		System.out.println("ProfileButton");
		PageLoader.getInstance().buildPage(Page.PROFILE, event);
	}

	@FXML
	public void examsButton(ActionEvent event) throws IOException {
		System.out.println("ExamsButton");
		PageLoader.getInstance().buildPage(Page.EXAM, event);
	}

	@FXML
	public void forumButton(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.FORUM, event);
	}

	@FXML
	public void backButton(ActionEvent event) throws IOException {
		System.out.println("Back");
		PageLoader.getInstance().buildPage(Page.COURSE, event);
	}
}
