package logic.view.menu.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import logic.Session;
import logic.controller.LoginController;
import logic.utilities.AppProperties;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class StatusBarView implements Initializable {
	
	@FXML
	private Button btnNotifications, btnLogout;
	
	@FXML
	private ImageView ivProfile;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Rectangle rectAvatar;
	
	private LoginController loginController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelName.setText(Session.getSession().getUserLogged().getName());
		
		String img = "/res/png/avatar/status/" + AppProperties.getInstance().getProperty("avatar").toString() + ".png";
		setAvatar(img);
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("Logout");
		
		loginController = new LoginController();
		loginController.logout();
		PageLoader.getInstance().buildPage(Page.LOGIN, event);
	}
	
	@FXML
	public void notification(ActionEvent event) throws IOException {
		System.out.println("Notifications");
	}
	
	@FXML
	public void profile(ActionEvent event) throws IOException {
		System.out.println("Profile");
		PageLoader.getInstance().buildPage(Page.PROFILE, event);
		
		
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rectAvatar.setFill(pattern);
	}
}
