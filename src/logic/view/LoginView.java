package logic.view;

import java.io.IOException;
import java.net.ConnectException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class LoginView implements Initializable {

	@FXML
	private Button btnLogin;
	@FXML
	private TextField textUsername;
	@FXML
	private PasswordField textPassword;
	@FXML
	private RadioButton rdProfessor, rdStudent;
	@FXML
	private ToggleGroup radioGroup;
	
	private RadioButton radioSelected;
	private LoginController loginController;

	@FXML
	void loginUser(ActionEvent event) throws IOException {

		String username = textUsername.getText();
		String password = textPassword.getText();

		if (username.isEmpty() || password.isEmpty() || (!rdProfessor.isSelected() && !rdStudent.isSelected())) {
			System.out.println("One or more fields are empty");
			return;
		}

		radioSelected = (RadioButton) radioGroup.getSelectedToggle();
		UserBean userBean = new UserBean();
		switch (radioSelected.getId()) {
//		login professor
		case "rdProfessor":
			userBean.setPassword(password);
			userBean.setUsername(username);

			loginController = new LoginController();
			try {
				loginController.loginAsProfessor(userBean);
				PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
			} catch (SQLException e) {
				AlertController.buildInfoAlert("Connection failed!", "Warning", event);
				
			} catch (ConnectException e) {
				AlertController.buildInfoAlert("Can't connect to internet\n.Try later", "Login failed", event);
			
			} catch (RecordNotFoundException e) {

				AlertController.buildInfoAlert("User not found: incorrect username or password.\nTry again or signup!", "Login failed", event);
			}
			break;

//		login student
		case "rdStudent":
			userBean.setPassword(password);
			userBean.setUsername(username);

			loginController = new LoginController();
			try {
				loginController.loginAsStudent(userBean);
				PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
			} catch (SQLException e) {
				AlertController.buildInfoAlert("Connection failed!", "Warning", event);
			} catch (RecordNotFoundException e) {
				AlertController.buildInfoAlert("User not found: incorrect username or password.\nTry again or signup!", "Login failed", event);
			}
			break;
		}
	}

	@FXML
	void gotoSignup(ActionEvent event) throws IOException {
		// load Signup Page
		PageLoader.getInstance().buildPage(Page.SIGNUP, event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnLogin.disableProperty()
				.bind(Bindings.isEmpty(textUsername.textProperty())
				.or(Bindings.isEmpty(textPassword.textProperty()))
				.or((rdProfessor.selectedProperty().not()).and(rdStudent.selectedProperty().not())));

		
		
		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					loginUser(event);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		// Se premi invio quando sei sul campo username o password fa il login
		textUsername.setOnAction(eventHandler);
		textPassword.setOnAction(eventHandler);
	}

}
