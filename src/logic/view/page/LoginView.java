package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
  
	// TODO mettere i dati su un file
	@FXML
	private void forgotPassword(ActionEvent event) throws MessagingException {
		
		String FROM = "project.ispw@gmail.com";
		String TO = AlertController.emailInput(event);
		String PASS = "ISPWProject";
		
		if (TO == null) {
			return;
		}
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(FROM, PASS);          
		    }    
		});
	
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		message.setSubject("Account password");

		String password;
		try {
			password = RoleDAO.getPasswordByEmail(TO);
			message.setText("Your password is: " + password);
			
		} catch (NullPointerException e) {
			System.out.println("NULL");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			AlertController.buildInfoAlert(e.getMessage(), "error", event);
			return;
		}
		
		Transport.send(message);
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