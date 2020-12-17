package logic.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class LoginView implements Initializable{

    @FXML
    private Button btnLogin;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    
    private LoginController loginController;
    

    @FXML
    void loginUser(ActionEvent event) throws IOException, ClassNotFoundException {
    	boolean logged;
    	String username = textUsername.getText();			//fare controlli sintattici qui?
    	String password = textPassword.getText();
    	// gestire input vuoti
    	
    	UserBean userBean = new UserBean();
    	userBean.setUsername(username);
    	userBean.setPassword(password);
    	System.out.println("LOGIN DATA:\n\tuser: "+userBean.getUsername() + "\n\tpass: "+userBean.getPassword());
    	loginController = new LoginController();
    	try {
			loginController.login(userBean);
			PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
		} catch (SQLException e) {
			e.printStackTrace();
			//Alert user not found o qualcosa del genere
		}
    }
    
    @FXML
    void gotoSignup(ActionEvent event) throws IOException {
    	// load Signup Page
    	PageLoader.getInstance().buildPage(Page.SIGNUP, event);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textUsername.requestFocus();
		btnLogin.setDisable(false);
	}

}
