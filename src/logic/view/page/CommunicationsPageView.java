package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class CommunicationsPageView implements Initializable {

	@FXML
	private VBox vboxComm;
	
	@FXML
	private VBox vboxNews;

	@FXML
	private ScrollPane scroll;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		PostCommunicationController controller = new PostCommunicationController();
		List<CommunicationBean> communications = null;
		try {
			communications = controller.getCommunications();
			for (CommunicationBean c : communications) {
				CommunicationCard communicationCard = new CommunicationCard(c.getText(), c.getTitle(), SQLConverter.date(c.getDate()), c.getId() + "");
				vboxComm.getChildren().add(communicationCard.getPane());
			}
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().buildPage(Page.HOMEPAGE);
			
		} catch (RecordNotFoundException e) {
			vboxComm.getChildren().add(new Label("No communication found."));
		}
	}

}
