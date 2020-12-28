package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.WeatherCardView;

public class WeatherCard extends AnchorPane {
	
	private WeatherCardView weatherCardView = new WeatherCardView();
	
	public WeatherCard(String temp, Image img, String time) throws IOException {
		URL url = new File("src/res/fxml/card/WeatherCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(weatherCardView);
		this.getChildren().add(loader.load());
		
		weatherCardView.setCard(temp, img, time);
	}

}
