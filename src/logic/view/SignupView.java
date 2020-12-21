package logic.view;

import java.io.IOException;
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
import logic.controller.SignupController;
import logic.exceptions.DuplicatedRecordException;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class SignupView implements Initializable{
	
	@FXML
	private TextField textName, textSurname, textEmail, textUsername;
	@FXML
	private PasswordField textPassword;
	@FXML
	private Button btnSignup, btnLogin, btnFacebook, btnGoogle;
	
	private SignupController signupController;
	
	@FXML
    void gotoLogin(ActionEvent event) throws IOException {
    	// load Login page
		PageLoader.getInstance().buildPage(Page.LOGIN, event);
    }
	
	@FXML
	void signup(ActionEvent event) throws IOException {
		
		String name = textName.getText();
		String surname = textSurname.getText();
		String username = (textUsername.getText().replaceAll("\\s", "")).toLowerCase();
		String email = textEmail.getText();
    	String password = textPassword.getText();
    	
    	if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
    		System.out.println("One or more fields are empty -> Alert");
    		return;
    	}
	
    	if (chekdInput() && AlertController.confirmation("Are your data correct?\nYour username will be:\n"+username)) {
			UserBean userBean = new UserBean();
			userBean.setUsername(username);
			userBean.setName(name);
			userBean.setSurname(surname);
			userBean.setEmail(email);
			userBean.setPassword(password);
			System.out
					.println("SIGNUP DATA:\n\tuser: " + userBean.getUsername() + "\n\tpass: " + userBean.getPassword());
			signupController = new SignupController();
			try {
				signupController.signup(userBean);
				AlertController.buildInfoAlert("Registration completed!\nYou will'be redirect to the login page.",
						"Welcome");
				PageLoader.getInstance().buildPage(Page.LOGIN, event);
			} catch (SQLException e) {
				AlertController.buildInfoAlert("Connection failed!", "Warning");
			} catch (DuplicatedRecordException e) {
				AlertController.buildInfoAlert("A user already exists with this email!\nTry to login.",
						"Registration Failed");
			}
		}
	}

	private boolean chekdInput() {
		String email = textEmail.getText();
		if (email.contains("@") && (email.contains(".com") || email.contains(".it")))
			return true;
		else {
			AlertController.buildInfoAlert("Invalid email", "Error");
			return false;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnSignup.disableProperty()
				.bind(Bindings.isEmpty(textName.textProperty())
						.or(Bindings.isEmpty(textSurname.textProperty())
								.or(Bindings.isEmpty(textEmail.textProperty())
										.or(Bindings.isEmpty(textUsername.textProperty()))
										.or(Bindings.isEmpty(textPassword.textProperty())))));

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					signup(event);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		textName.setOnAction(eventHandler);
		textSurname.setOnAction(eventHandler);
		textEmail.setOnAction(eventHandler);
		textPassword.setOnAction(eventHandler);
		textUsername.setOnAction(eventHandler);
	}

}
