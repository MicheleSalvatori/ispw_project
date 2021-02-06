package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.CancelException;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Email;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;

public class LoginPageView implements Initializable {

	@FXML
	private Button btnLogin;

	@FXML
	private TextField textUsername;

	@FXML
	private PasswordField textPassword;

	private LoginController loginController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnLogin.disableProperty()
				.bind(Bindings.isEmpty(textUsername.textProperty()).or(Bindings.isEmpty(textPassword.textProperty())));

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginUser(event);
			}
		};

		textUsername.setOnAction(eventHandler);
		textPassword.setOnAction(eventHandler);
	}

	@FXML
	void loginUser(ActionEvent event) {

		String username = textUsername.getText();
		String password = textPassword.getText();

		if (username.isEmpty() || password.isEmpty()) {
			AlertController.infoAlert("One or more fields are empty");
			return;
		}

		// Get user type
		UserBean userBean = new UserBean();
		userBean.setPassword(password);
		userBean.setUsername(username);
		loginController = new LoginController();
		
		try {
			userBean = loginController.login(userBean);
			UserBean.setInstance(userBean);
			
		} catch (SQLException e) {
			AlertController.infoAlert("Connection failed!");
			return;
			
		} catch (RecordNotFoundException e) {
			AlertController.infoAlert("User not found: incorrect username or password.\nTry again or signup!");
			return;
		}
			
		if (userBean.getRole() == Role.ADMIN) {
			PageLoader.getInstance().buildPage(Page.ADMINISTRATION_PAGE);
		} else {
			PageLoader.getInstance().buildPage(Page.HOMEPAGE);
		}
	}

	@FXML
	private void forgotPassword(ActionEvent event) {
		
		loginController = new LoginController();
		
		try {
			String email = AlertController.emailInput();
			UserBean userBean = new UserBean();
			userBean.setEmail(email);
			
			String password = loginController.getUserByEmail(userBean).getPassword();
			Email.password(email, password);

		} catch (SQLException e) {
			AlertController.infoAlert("Connection failed!");
			return;

		} catch (RecordNotFoundException e) {
			AlertController.infoAlert(e.getMessage());
			return;

		} catch (CancelException e) {
			return;
		}

		AlertController.infoAlert("Your password will be sended to your e-mail.");
	}

	@FXML
	void gotoSignup(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.SIGNUP);
	}
	
	@FXML
	void facebookLogin(ActionEvent event) {
		AlertController.infoAlert("Functionality not yet implemented");
	}
	
	@FXML
	void googleLogin(ActionEvent event) {
		AlertController.infoAlert("Functionality not yet implemented");
	}
}