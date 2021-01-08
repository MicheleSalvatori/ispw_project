package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
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
import logic.exceptions.RecordNotFoundException;
import logic.model.dao.RoleDAO;
import logic.utilities.AlertController;
import logic.utilities.Email;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class LoginView implements Initializable {

	@FXML
	private Button btnLogin;
	
	@FXML
	private TextField textUsername;
	
	@FXML
	private PasswordField textPassword;
	
	private LoginController loginController;
	private String type;

	@FXML
	void loginUser(ActionEvent event) throws IOException {

		String username = textUsername.getText();
		String password = textPassword.getText();

		if (username.isEmpty() || password.isEmpty()) {
			System.out.println("One or more fields are empty");
			return;
		}
		
		// get type user
		UserBean userBean = new UserBean();
		userBean.setPassword(password);
		userBean.setUsername(username);
		loginController = new LoginController();
		try {
			type = loginController.getTypeUser(userBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			AlertController.buildInfoAlert("User not found: incorrect username or password.\nTry again or signup!", "Login failed", event);
			return;
		}
		
		// switch for type user login
		try {
			switch (type) {
			
			case "student":
				loginController.loginAsStudent(userBean);
				break;
				
			case "professor":
				loginController.loginAsProfessor(userBean);
				break;
				
			case "admin":
				loginController.loginAsAdmin(userBean);
				break;
			}
			
		} catch (SQLException e) {
			AlertController.buildInfoAlert("Connection failed!", "Warning", event);
			
		} catch (RecordNotFoundException e) {
			AlertController.buildInfoAlert("User not found: incorrect username or password.\nTry again or signup!", "Login failed", event);
		
		} finally {
			// Build Homepage
			PageLoader.getInstance().buildPage(Page.HOMEPAGE, event, null);
		}
	}
  
	@FXML
	private void forgotPassword(ActionEvent event) {
		
		String email = AlertController.emailInput(event);

		try {
			String password = RoleDAO.getPasswordByEmail(email);
			Email.password(email, password);
			
		} catch (NullPointerException e) {
			System.out.println("NULL");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			AlertController.buildInfoAlert(e.getMessage(), "error", event);
			return;
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AlertController.buildInfoAlert("Your password will be sended to your e-mail.", "password", event);
	}

	@FXML
	void gotoSignup(ActionEvent event) throws IOException {
		// load Signup Page
		PageLoader.getInstance().buildPage(Page.SIGNUP, event, null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnLogin.disableProperty()
				.bind(Bindings.isEmpty(textUsername.textProperty())
				.or(Bindings.isEmpty(textPassword.textProperty())));
		
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