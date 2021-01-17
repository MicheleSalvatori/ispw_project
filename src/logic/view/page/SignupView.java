package logic.view.page;

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
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class SignupView implements Initializable {
	
	@FXML
	private TextField textUsername, textName, textSurname, textEmail;
	
	@FXML
	private PasswordField textPassword, textConfirmPassword;
	
	@FXML
	private Button btnSignup, btnLogin, btnFacebook, btnGoogle;
	
	private SignupController signupController;
	
	@FXML
    void gotoLogin(ActionEvent event) throws IOException {
    	// load Login page
		PageLoader.getInstance().buildPage(Page.LOGIN);
    }
	
	@FXML
	void signup(ActionEvent event) throws IOException {
		
		String username = (textUsername.getText().replaceAll("\\s", "")).toLowerCase();
		String password = textPassword.getText();
		String email = textEmail.getText();
		String name = textName.getText();
		String surname = textSurname.getText();
    	
    	if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
    		System.out.println("One or more fields are empty -> Alert");
    		return;
    	}
	
    	if (chekdInput(event) && AlertController.confirmationAlert("Are your data correct?\nYour username will be:\n" + username)){
			UserBean userBean = new UserBean();
			userBean.setUsername(username);
			userBean.setName(name);
			userBean.setSurname(surname);
			userBean.setEmail(email);
			userBean.setPassword(password);
			
			System.out.println("SIGNUP DATA:\n\tuser: " + userBean.getUsername() + "\n\tpass: " + userBean.getPassword());
			signupController = new SignupController();
			try {
				signupController.signup(userBean);
				AlertController.infoAlert("Registration completed!\nYou will'be redirect to the login page.");
				PageLoader.getInstance().buildPage(Page.LOGIN);
				
			} catch (SQLException e) {
				AlertController.infoAlert("Connection failed!");
				
			} catch (DuplicatedRecordException e) {
				AlertController.infoAlert(e.getMessage());
			}
		}
	}

	private boolean chekdInput(ActionEvent event) {
		
		String email = textEmail.getText(); 
		String password = textPassword.getText();
		String confirmPassword = textConfirmPassword.getText();
		
		// Check if email is valid and if passwords are the same
		if (email.contains("@") && (email.contains(".com")|| email.contains(".it"))) {
			
			if (password.equals(confirmPassword)) {
				return true;
			}
			else {
				AlertController.infoAlert("Different passwords");
				return false;
			}
		}
		else {
			AlertController.infoAlert("Invalid email");
			return false;
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnSignup.disableProperty()
				.bind(Bindings.isEmpty(textName.textProperty())
				.or(Bindings.isEmpty(textSurname.textProperty()))
				.or(Bindings.isEmpty(textUsername.textProperty()))
				.or(Bindings.isEmpty(textEmail.textProperty()))
				.or(Bindings.isEmpty(textPassword.textProperty()))
				.or(Bindings.isEmpty(textConfirmPassword.textProperty())));

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
		
		textUsername.setOnAction(eventHandler);
		textPassword.setOnAction(eventHandler);	
		textEmail.setOnAction(eventHandler);
		textConfirmPassword.setOnAction(eventHandler);
		textName.setOnAction(eventHandler);
		textSurname.setOnAction(eventHandler);
	}
}
