package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.RequestBean;
import logic.view.card.controller.RequestCardView;
import logic.view.graphic.GraphicElement;

public class RequestCard extends GraphicElement {
	
	private RequestCardView requestCardView = new RequestCardView();
	
	public RequestCard(RequestBean request) {
		FXMLLoader loader = getLoader("src/res/fxml/card/RequestCard.fxml");
		loader.setController(requestCardView);
		load(loader);

		requestCardView.setCard(request);
	}
}
