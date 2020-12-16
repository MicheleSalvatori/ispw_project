package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NavBarController implements Initializable {
	
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
	public void homeButton(ActionEvent e) throws IOException {
		System.out.println("HomeButton");
		PageLoader pageLoader = new PageLoader(Page.HOMEPAGE, e);
	}
	
	@FXML
	public void examsButton(ActionEvent e) throws IOException {
		System.out.println("Examspage");
		PageLoader pageLoader = new PageLoader(Page.LOGIN, e);
	}
	
	
}