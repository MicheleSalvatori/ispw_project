package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.CommunicationCardView;

public class CommunicationCard extends AnchorPane {
	
	private CommunicationCardView communicationCardView = new CommunicationCardView();
	
	public CommunicationCard(String text, String title, String date, String number) throws IOException {
		URL url = new File("src/res/fxml/card/CommunicationCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(communicationCardView);
		this.getChildren().add(loader.load());

		communicationCardView.setLabel(text, title, date, number);
	}
}
