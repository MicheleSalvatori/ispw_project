package logic.view.menu.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.controller.AcceptRequestController;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;

public class StatusBarView implements Initializable {
	
	@FXML
	private Button btnNotifications;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private Button btnProfile;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Rectangle rectAvatar;
	
	int reqCount;
	private Stage stage;
	private EventHandler<ActionEvent> addGotoRequestevent;
	private EventHandler<ActionEvent> cancRequestEvent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelName.setText(UserBean.getInstance().getName());
		
		if (UserBean.getInstance().getRole() == Role.ADMIN) {
			btnNotifications.setVisible(false);
			rectAvatar.setVisible(false);
			btnProfile.setDisable(true);
			btnNotifications.setDisable(true);
			rectAvatar.setDisable(true);
			return;
		}
		
		String img = "/res/png/avatar/status/avatar1.png";
		setAvatar(img);
		
		reqCount=0;
		setNotificationStatus();
	}
	
	@FXML
	public void logout(ActionEvent event) {
		LoginController loginController = new LoginController();
		loginController.logout();
		PageLoader.getInstance().buildPage(Page.LOGIN);
	}
	
	@FXML
	public void notification(ActionEvent event) {
		if(UserBean.getInstance().getRole().equals(Role.PROFESSOR)) {
			setupRequestDialog();
		}
	}
	
	private void setupRequestDialog() {
		stage = new Stage();
		
		Parent root = null;
		try {
			URL url = new File("src/res/fxml/dialog/NotificationDialog.fxml").toURI().toURL();
			root = FXMLLoader.load(url);
			
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Page loading error");
			return;
		}
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/res/style/dialog/NotificationDialog.css").toExternalForm());
		AlertController.setupDialog(scene, stage);
		
		stage.setTitle("App - Notification");
		
		Button btnRequest = (Button) scene.lookup("#btnRequest");
		Button btnCancel = (Button) scene.lookup("#btnCancel");
		Label labelNotification = (Label) scene.lookup("#labelNotification");
		
		setupEvent();
		btnRequest.setOnAction(addGotoRequestevent);
		btnCancel.setOnAction(cancRequestEvent);
		
		if(reqCount > 0) {
			labelNotification.setText("You have " + reqCount + " requests.");
		} else {
			labelNotification.setText("You don't have requests.");
		}
	}

	private void setupEvent() {
		addGotoRequestevent = e -> {
			closeStage();
			PageLoader.getInstance().buildPage(Page.REQUEST);
		};
		
		cancRequestEvent = e -> {
			closeStage();
			PageLoader.getStage().getScene().getRoot().setEffect(null);
		};
	}
	
	private void setNotificationStatus() {
		if(UserBean.getInstance().getRole() == Role.PROFESSOR) {
			AcceptRequestController acceptRequestController = new AcceptRequestController();
			try {
				reqCount = acceptRequestController.getRequests(UserBean.getInstance()).size();
				btnNotifications.setStyle("-icon-paint: #FF00FF; -fx-text-fill: #FF00FF");
				btnNotifications.setText(String.valueOf(reqCount));
				
			} catch (RecordNotFoundException e) {
				btnNotifications.setStyle("-icon-paint: black; -fx-text-fill: black");
				btnNotifications.setText("");
				
			} catch (SQLException e) {
				AlertController.infoAlert(AlertController.getError());
				PageLoader.getInstance().goBack();
			}
			
		} else {
			btnNotifications.setVisible(false);
		}
	}
	
	private void closeStage() {
		stage.close();
	}
	
	@FXML
	public void profile(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.PROFILE);
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rectAvatar.setFill(pattern);
	}
}