package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.bean.CommunicationBean;
import logic.controller.PostCommunicationController;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class PostCommunicationView implements Initializable {

	@FXML
	private TextArea textCommunication;

	@FXML
	private TextField titleCommunication;

	@FXML
	private Button btnSubmit;

	
	private String text, title;
	private CommunicationBean communication;
	private PostCommunicationController controller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = new PostCommunicationController();
		btnSubmit.disableProperty().bind(Bindings.isEmpty(titleCommunication.textProperty())
				.or(Bindings.isEmpty(textCommunication.textProperty())));
		
	}

	@FXML
	private void submitCommunication(ActionEvent event) {
		communication = new CommunicationBean();
		this.title = titleCommunication.getText();
		this.text = textCommunication.getText();
		communication.setDate(new Date(System.currentTimeMillis()));
		communication.setText(text);
		communication.setTitle(title);
		
		try {
			controller.postCommunication(communication);
			AlertController.infoAlert("Communication has been correctly posted");
			PageLoader.getInstance().buildPage(Page.ADMINISTRATION_PAGE);
		} catch (SQLException e) {
			AlertController.infoAlert("Somethings gone wrong!");
		} catch (IOException e) {
			// Viene dal pageloader
			e.printStackTrace();
		} 
	}

}
