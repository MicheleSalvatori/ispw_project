package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import logic.bean.CommunicationBean;
import logic.controller.PostCommunicationController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.card.element.CommunicationCard;

public class AdministrationPageView implements Initializable {

	@FXML
	private VBox vboxComm;

	@FXML
	private Button btnAddCommunication, btnAddCourse, btnCredentialsProfessor;
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
			e1.printStackTrace();
			
		} catch (RecordNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void postCommunication(ActionEvent event) {
		System.out.println("Post Communication");
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
