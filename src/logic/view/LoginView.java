package logic.view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.bean.ProfessorBean;
import logic.bean.StudentBean;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class LoginView implements Initializable{

    @FXML
    private Button btnLogin;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    private RadioButton radioSelected;
    @FXML
    private ToggleGroup radioGroup;
    private LoginController loginController;
    

    @FXML
    void loginUser(ActionEvent event) throws IOException {
    	
    	String username = textUsername.getText();
    	String password = textPassword.getText();
    	
    	if (username.isEmpty() || password.isEmpty()) {
    		System.out.println("One or more fields are empty");
    		return;
    	}
    	
    	radioSelected = (RadioButton) radioGroup.getSelectedToggle();
    	
    	switch (radioSelected.getId()) {
		case "rdProfessor":
//			login professor
			ProfessorBean professorBean = new ProfessorBean();
			professorBean.setPassword(password);
			professorBean.setUsername(username);
			
			loginController = new LoginController();
			try {
				loginController.login(professorBean);
				PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
				System.out.println("User '" + username + "' whit password '" + password + "' not found.\nShowing alert.\n");
			}
			break;
		case "rdStudent":
//			login student
			StudentBean studentBean = new StudentBean();
			studentBean.setPassword(password);
			studentBean.setSurname(username);
			
			loginController = new LoginController();
//			loginController.login(studentBean);
			break;
		default:
			break;
		}
//    	UserBean userBean = new UserBean();
//    	userBean.setUsername(username);
//    	userBean.setPassword(password);
//    	System.out.println("LOGIN DATA:\n\tuser: "+userBean.getUsername() + "\n\tpass: "+userBean.getPassword());
    	
//    	loginController = new LoginController();
//    	try {
////			loginController.login(userBean);
//			PageLoader.getInstance().buildPage(Page.HOMEPAGE, event);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			
////		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
////		} catch (RecordNotFoundException e) {
//			// Alert user not found o qualcosa del genere
//			System.out.println("User '" + username + "' whit password '" + password + "' not found.\nShowing alert.\n");
//		}
    }
    
    @FXML
    void gotoSignup(ActionEvent event) throws IOException {
    	// load Signup Page
    	PageLoader.getInstance().buildPage(Page.SIGNUP, event);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnLogin.disableProperty().bind(Bindings.isEmpty(textUsername.textProperty())
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
