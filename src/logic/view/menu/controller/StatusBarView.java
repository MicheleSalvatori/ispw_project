package logic.view.menu.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.utilities.AppProperties;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class StatusBarView implements Initializable {
	
	@FXML
	private Button btnNotifications;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Rectangle rectAvatar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelName.setText(UserBean.getInstance().getName());
		
		String img = "/res/png/avatar/status/" + AppProperties.getInstance().getProperty("avatar") + ".png";
		setAvatar(img);
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		System.out.println("Logout");
		
		LoginController loginController = new LoginController();
		loginController.logout();
		PageLoader.getInstance().buildPage(Page.LOGIN);
	}
	
	@FXML
	public void notification(ActionEvent event) {
		System.out.println("Notifications");
	}
	
	@FXML
	public void profile(ActionEvent event) throws IOException {
		System.out.println("Profile");
		PageLoader.getInstance().buildPage(Page.PROFILE);
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rectAvatar.setFill(pattern);
	}
}
