package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView implements Initializable{

    @FXML
    private Button btnLogin;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;

    @FXML
    void loginUser(ActionEvent event) {
    	String user = textUsername.getText();
    	String password = textPassword.getText();
    	
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
