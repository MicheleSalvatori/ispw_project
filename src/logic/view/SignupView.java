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
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class SignupView implements Initializable{
	
	@FXML
	private TextField textName, textSurname, textEmail;
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
		String username = (name.replaceAll("\\s", "")).toLowerCase() + "." +
							(surname.replaceAll("\\s", "")).toLowerCase();
		String email = textEmail.getText();
    	String password = textPassword.getText();
    	
    	if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
    		System.out.println("One or more fields are empty -> Alert");
    		return;
    	}
	
		UserBean userBean = new UserBean();
		userBean.setUsername(username);
    	userBean.setName(name);
    	userBean.setSurname(surname);
    	userBean.setEmail(email);
    	userBean.setPassword(password);
    	System.out.println("SIGNUP DATA:\n\tuser: "+userBean.getUsername() + "\n\tpass: "+userBean.getPassword());
    	signupController = new SignupController();
	    try {
			signupController.signup(userBean);
			PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
			// Alert user not found o qualcosa del genere
			System.out.println("User '" + username + "' whit password '" + password + "' not found.\nShowing alert.\n");
		} catch (DuplicatedRecordException e) {
			e.printStackTrace();
			System.out.println("Username already present.\n Showing error message.\n");
			// TODO implementare alert di errore
		}
	}

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnSignup.disableProperty().bind(Bindings.isEmpty(textName.textProperty())
				  				   .or(Bindings.isEmpty(textSurname.textProperty())
				  				   .or(Bindings.isEmpty(textEmail.textProperty())
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
	}

}
