package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.view.card.controller.NewsCardView;
import logic.view.graphic.GraphicElement;

public class NewsCard extends GraphicElement {
	
	private NewsCardView newsCardView = new NewsCardView();
	
	public NewsCard(String title, String date, String number) {
		FXMLLoader loader = getLoader("src/res/fxml/card/NewsCard.fxml");
		loader.setController(newsCardView);
		load(loader);

		newsCardView.setLabel(title, date, number);
	}
}
