package logic.view.page;

import java.io.IOException;
import java.net.ConnectException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;
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
	@FXML
	private ToggleGroup radioGroup;
	
	private RadioButton radioSelected;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// switch for type user login
		switch (type) {
		case "student":
			try {
				loginController.loginAsStudent(userBean);
				PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
			} catch (SQLException e) {
				AlertController.buildInfoAlert("Connection failed!", "Warning", event);
			} catch (RecordNotFoundException e) {
				AlertController.buildInfoAlert("User not found: incorrect username or password.\nTry again or signup!", "Login failed", event);
			}
			break;
		case "professor":
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
		case "admin":
			try {
				loginController.loginAsAdmin(userBean);
				PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
			} catch (SQLException e) {
				AlertController.buildInfoAlert("Connection failed!", "Warning", event);
			} catch (ConnectException e) {
				AlertController.buildInfoAlert("Can't connect to internet\n.Try later", "Login failed", event);
			} catch (RecordNotFoundException e) {
				AlertController.buildInfoAlert("User not found: incorrect username or password.\nTry again or signup!", "Login failed", event);
			}
			break;
		}
		
	}
  
	@FXML
	private void forgotPassword(ActionEvent event) throws MessagingException {
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication("account", "password");          
		    }    
		});
	
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("xpunitorex@gmail.com"));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("xpunitorex@outlook.com"));
		message.setSubject("MHANZ");
		message.setText("Lutz");
		
		Transport.send(message);
		
		AlertController.buildInfoAlert("Your password will be sended to your e-mail.", "password", event);
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
