package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.UserBean;
import logic.controller.LoginController;

public class LoginView implements Initializable{

    @FXML
    private Button btnLogin;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    
    private UserBean userBean;
    private LoginController loginController;
    

    @FXML
    void loginUser(ActionEvent event) {
    	boolean logged;
    	String user = textUsername.getText();
    	String password = textPassword.getText();
    	
    	userBean = UserBean.getUserBeanInstance(user);
    	userBean.setUsbPassword(password);
    	
    	loginController = new LoginController();
    	logged = loginController.login(userBean);
    	
    	if (!logged) {
    		UserBean.setInstance(null);
    		// show Alert o gestione exception?
    	}
    	
    	// load Homepage
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textUsername.requestFocus();
		btnLogin.setDisable(false);
	}

}
