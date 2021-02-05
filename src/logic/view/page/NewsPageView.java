package logic.view.page;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import logic.view.card.element.CommunicationCard;
import logic.view.card.element.NewsCard;

public class NewsPageView implements Initializable {

	@FXML
	private VBox vboxComm, vboxNews;
	
	@FXML
	private ScrollPane scroll;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		for (int i=0; i<10; i++) {
			CommunicationCard communicationCard = new CommunicationCard(i+"",i+"",i+"");
			vboxComm.getChildren().add(communicationCard.getPane());
		}
		
		for (int i=0; i<10; i++) {
			NewsCard newsCard = new NewsCard(i+"",i+"",i+"");
			vboxNews.getChildren().add(newsCard.getPane());
		}
	}
}
