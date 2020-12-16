package logic.view;

import java.io.IOException;
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
import logic.utilities.Page;
import logic.utilities.PageLoader;

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
    void loginUser(ActionEvent event) throws IOException {
    	boolean logged;
    	String user = textUsername.getText();
    	String password = textPassword.getText();
    	
    	userBean = UserBean.getUserBeanInstance(user);
    	userBean.setUsbPassword(password);
    	
    	loginController = new LoginController();
    	logged = loginController.login(userBean);
    	System.out.println(logged);
    	if (!logged) {
    		UserBean.setInstance(null);
    		// show Alert o gestione exception?
    	}
    	
    	// load Homepage
    	PageLoader pageLoader = new PageLoader(Page.HOMEPAGE, event);
    }
    
    @FXML
    void gotoSignup(ActionEvent ae) throws IOException {
    	// load Signup Page
    	PageLoader pageLoader = new PageLoader(Page.SIGNUP, ae);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textUsername.requestFocus();
		btnLogin.setDisable(false);
	}

}
