package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.bean.QuestionBean;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.view.card.element.CommunicationCard;
import logic.view.card.element.NewsCard;
import logic.view.card.element.QuestionCard;

public class NewsPageView implements Initializable {
	
	@FXML
	private Button btnNewNews, btnNewCommunication;

	@FXML
	private VBox vboxComm, vboxNews;
	
	@FXML
	private Label labelLoadingNews, labelLoadingCommunications;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// if not admin, insert is not visible
		if(Session.getSession().getType() != Role.ADMIN) {
			btnNewNews.setVisible(false);
			btnNewCommunication.setVisible(false);
			
		}
		// get all news

		// get all communications
	}


	@FXML
	private void newNews(ActionEvent ae) throws IOException {
		System.out.println("NewNews button");
		PageLoader.getInstance().buildPage(Page.NEWNEWS, ae, null);
	}
	
	@FXML
	private void newCommunication(ActionEvent ae) throws IOException {
		System.out.println("NewCommunication button");
		// TODO
		PageLoader.getInstance().buildPage(Page.NEWCOMMUNICATION, ae, null);
	}


}
