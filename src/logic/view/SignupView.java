package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class SignupView implements Initializable{
	
	@FXML
	private TextField textName, textSurname, textEmail;
	@FXML
	private PasswordField textPassword;
	@FXML
	private RadioButton rbProfessor, rbStudent;
	@FXML
	private Button btnSignup, btnLogin;
	
	@FXML
    void gotoLogin(ActionEvent event) throws IOException {
    	// load Login page
    	PageLoader pageLoader = new PageLoader(Page.LOGIN, event);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnSignup.setDisable(false); // TODO implementare false disable se tutti i campi sono compilati
	}

}
