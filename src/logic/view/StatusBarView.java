package logic.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import logic.Session;
import logic.controller.LoginController;
import logic.model.User;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class StatusBarView implements Initializable {
	
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
		labelName.setText(userLogged.getUsername());
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("Logout");
		
		PageLoader.getInstance().buildPage(Page.LOGIN, event);
		
		LoginController loginController = new LoginController();
		loginController.logout();
		
		System.out.println("Session: " + Session.getSession().getUserLogged());
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
