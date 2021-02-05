package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.view.card.controller.CommunicationCardView;
import logic.view.graphic.GraphicElement;

public class CommunicationCard extends GraphicElement {
	
	private CommunicationCardView communicationCardView = new CommunicationCardView();
	
	public CommunicationCard(String text, String title, String date, String number) {
		FXMLLoader loader = getLoader("src/res/fxml/card/CommunicationCard.fxml");
		loader.setController(communicationCardView);
		load(loader);

		communicationCardView.setLabel(text, title, date, number);
	}
}
