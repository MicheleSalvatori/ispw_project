package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import logic.bean.CommunicationBean;
import logic.controller.PostCommunicationController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.card.element.CommunicationCard;

public class AdministrationPageView implements Initializable {

	@FXML
	private VBox vboxComm;

	@FXML
	private Button btnAddCommunication;
	
	@FXML
	private Button btnAddCourse;
	
	@FXML
	private Button btnCredentialsProfessor;
	
	@FXML
	private ScrollPane scroll;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		PostCommunicationController controller = new PostCommunicationController();
		List<CommunicationBean> communications = null;
		try {
			communications = controller.getCommunications();
			for (CommunicationBean c : communications) {
				CommunicationCard communicationCard = new CommunicationCard(c.getText(), c.getTitle(),
						SQLConverter.date(c.getDate()), c.getId() + "");
				vboxComm.getChildren().add(communicationCard.getPane());
			}

		} catch (SQLException e1) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
			
		} catch (RecordNotFoundException e1) {
			vboxComm.getChildren().add(new Label("No exam found"));
		}
	}

	@FXML
	public void postCommunication(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.POST_COMMUNICATION);
	}

	@FXML
	public void assignProfessor(ActionEvent event) {
		System.out.println("Professor Credentials");
	}

	@FXML
	public void addCourse(ActionEvent event) {
		System.out.println("Add Course");
	}
}
