package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import logic.view.card.element.CommunicationCard;
import logic.view.card.element.NewsCard;

public class NewsPageView implements Initializable {

	@FXML
	private VBox vboxComm, vboxNews;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		for (int i=0; i<10; i++) {
			try {
				CommunicationCard communicationCard = new CommunicationCard(i+"",i+"",i+"");
				vboxComm.getChildren().add(communicationCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i=0; i<10; i++) {
			try {
				NewsCard newsCard = new NewsCard(i+"",i+"",i+"");
				vboxNews.getChildren().add(newsCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
