package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.InvalidInputException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NewProfessorPageView implements Initializable {
	
	@FXML
	private Button btnAddProfessor;
	
	@FXML
	private TextField textUsernameProfessor;

	@FXML
	private TextField textNameProfessor;

	@FXML
	private TextField textSurnameProfessor;

	@FXML
	private TextField textEmailProfessor;

	@FXML
	private PasswordField textPasswordProfessor;

	@FXML
	private PasswordField textConfirmPasswordProfessor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnAddProfessor.disableProperty().bind(Bindings.isEmpty(textNameProfessor.textProperty())
				.or(Bindings.isEmpty(textSurnameProfessor.textProperty())).or(Bindings.isEmpty(textUsernameProfessor.textProperty()))
				.or(Bindings.isEmpty(textEmailProfessor.textProperty())).or(Bindings.isEmpty(textPasswordProfessor.textProperty()))
				.or(Bindings.isEmpty(textConfirmPasswordProfessor.textProperty())));
	}
	
	@FXML
	public void addProfessor(ActionEvent event) {
		
		String username = (textUsernameProfessor.getText().replaceAll("\\s", "")).toLowerCase();
		String password = textPasswordProfessor.getText();
		String email = textEmailProfessor.getText();
		String name = textNameProfessor.getText();
		String surname = textSurnameProfessor.getText();
		
		try {
			checkInput();
			
		} catch (InvalidInputException e) {
			AlertController.infoAlert(e.getMessage());
			return;
		}
		
		UserBean userBean = new UserBean();
		userBean.setUsername(username);
		userBean.setName(name);
		userBean.setSurname(surname);
		userBean.setEmail(email);
		userBean.setPassword(password);

		LoginController controller = new LoginController();
		try {
			controller.addProfessor(userBean);
			AlertController.infoAlert("Registration completed!\nYou will'be redirect to the administration page.");
			PageLoader.getInstance().buildPage(Page.ADMINISTRATION_PAGE);

		} catch (SQLException e) {
			AlertController.infoAlert("Connection failed!");

		} catch (DuplicatedRecordException e) {
			AlertController.infoAlert(e.getMessage());
		}
	}
	
	private void checkInput() throws InvalidInputException {
		String nameProfessor = textNameProfessor.getText();
		String surnameProfessor = textSurnameProfessor.getText();
		String emailProfessor = textEmailProfessor.getText();
		String passwordProfessor = textPasswordProfessor.getText();
		String confirmPasswordProfessor = textConfirmPasswordProfessor.getText();
		Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(nameProfessor + surnameProfessor);

		// Check if email is valid and if passwords are the same
		if (!passwordProfessor.equals(confirmPasswordProfessor)) {
			throw new InvalidInputException("Please make sure your passwords match.");
		}
		
		try {
			InternetAddress emailAddr = new InternetAddress(emailProfessor);
			emailAddr.validate();
		} catch (AddressException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		// Check if name and surname are valid
		if (m.find()) {
			throw new InvalidInputException("The name and surname must contain alpha characters only.");
		}
	}

}
