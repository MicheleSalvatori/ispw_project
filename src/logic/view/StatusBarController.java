package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import logic.Session;
import logic.model.User;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class StatusBarController implements Initializable {
	
	@FXML
	private Button btnNotifications;
	@FXML
	private Button btnLogout;
	@FXML
	private ImageView ivProfile;
	@FXML
	private Label labelName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User userLogged = Session.getSession().getUserLogged();
		userLogged.setUsername("Gulyx");
		labelName.setText(userLogged.getUsername());
	}
	
	@FXML
	public void logout(ActionEvent e) throws IOException {
		System.out.println("Logout");
	}
	
	@FXML
	public void notification(ActionEvent e) throws IOException {
		System.out.println("Notifications");
	}
	
	@FXML
	public void profile(javafx.scene.input.MouseEvent e) throws IOException {
		System.out.println("Profile");
	}
}
