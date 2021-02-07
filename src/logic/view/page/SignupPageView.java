package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import logic.exceptions.InvalidInputException;

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
import logic.exceptions.DuplicatedRecordException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class SignupPageView implements Initializable {

	@FXML
	private TextField textUsername;

	@FXML
	private TextField textName;

	@FXML
	private TextField textSurname;

	@FXML
	private TextField textEmail;

	@FXML
	private PasswordField textPassword;

	@FXML
	private PasswordField textConfirmPassword;

	@FXML
	private Button btnSignup;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnFacebook;

	@FXML
	private Button btnGoogle;

	@FXML
	void gotoLogin(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.LOGIN);
	}

	@FXML
	void signup(ActionEvent event) {

		String username = (textUsername.getText().replaceAll("\\s", "")).toLowerCase();
		String password = textPassword.getText();
		String email = textEmail.getText();
		String name = textName.getText();
		String surname = textSurname.getText();

		if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
			AlertController.infoAlert("One or more fields are empty");
			return;
		}
		try {
			checkInput();
		} catch (InvalidInputException e) {
			AlertController.infoAlert(e.getMessage());
			return;
		}
		if (AlertController.confirmationAlert("Are your data correct?\nYour username will be:\n" + username)) {
			UserBean userBean = new UserBean();
			userBean.setUsername(username);
			userBean.setName(name);
			userBean.setSurname(surname);
			userBean.setEmail(email);
			userBean.setPassword(password);

			LoginController controller = new LoginController();
			try {
				controller.signup(userBean);
				AlertController.infoAlert("Registration completed!\nYou will'be redirect to the login page.");
				PageLoader.getInstance().buildPage(Page.LOGIN);

			} catch (SQLException e) {
				AlertController.infoAlert("Connection failed!");

			} catch (DuplicatedRecordException e) {
				AlertController.infoAlert(e.getMessage());
			}
		}
	}

	private void checkInput() throws InvalidInputException {
		String name = textName.getText();
		String surname = textSurname.getText();
		String email = textEmail.getText();
		String password = textPassword.getText();
		String confirmPassword = textConfirmPassword.getText();
		Pattern p = Pattern.compile("[^A-Za-z]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name + surname);

		// Check if email is valid and if passwords are the same
		if (!password.equals(confirmPassword)) {
			throw new InvalidInputException("Please make sure your passwords match.");
		}
		
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		// Check if name and surname are valid
		if (m.find()) {
			throw new InvalidInputException("The name and surname must contain alpha characters only.");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnSignup.disableProperty().bind(Bindings.isEmpty(textName.textProperty())
				.or(Bindings.isEmpty(textSurname.textProperty())).or(Bindings.isEmpty(textUsername.textProperty()))
				.or(Bindings.isEmpty(textEmail.textProperty())).or(Bindings.isEmpty(textPassword.textProperty()))
				.or(Bindings.isEmpty(textConfirmPassword.textProperty())));

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				signup(event);
			}
		};

		textUsername.setOnAction(eventHandler);
		textPassword.setOnAction(eventHandler);
		textEmail.setOnAction(eventHandler);
		textConfirmPassword.setOnAction(eventHandler);
		textName.setOnAction(eventHandler);
		textSurname.setOnAction(eventHandler);
	}

	@FXML
	void facebookSignup(ActionEvent event) {
		AlertController.infoAlert("Functionality not yet implemented");
	}

	@FXML
	void googleSignup(ActionEvent event) {
		AlertController.infoAlert("Functionality not yet implemented");
	}
}
