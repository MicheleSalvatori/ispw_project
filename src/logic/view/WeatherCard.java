package logic.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class WeatherCard extends AnchorPane {
	
	private WeatherCardView weatherCardView = new WeatherCardView();
	
	public WeatherCard(String temp, String time) throws IOException {
		URL url = new File("src/res/fxml/WeatherCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(weatherCardView);
		this.getChildren().add(loader.load());
		
		weatherCardView.setCard(temp, time);
	}

}
