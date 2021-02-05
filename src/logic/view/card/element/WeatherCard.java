package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import logic.view.card.controller.WeatherCardView;
import logic.view.graphic.GraphicElement;

public class WeatherCard extends GraphicElement {
	
	private WeatherCardView weatherCardView = new WeatherCardView();
	
	public WeatherCard(String temp, Image img, String time) {
		FXMLLoader loader = getLoader("src/res/fxml/card/WeatherCard.fxml");
		loader.setController(weatherCardView);
		load(loader);
		
		weatherCardView.setCard(temp, img, time);
	}
}
