package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WeatherCardView {

	@FXML
	private Label labelTemp;
	
	@FXML
	private Label labelTime;
	
	@FXML
	private ImageView imgWeather;
	
	public void setCard(String temp, Image img, String time) {
		labelTemp.setText(temp);
		imgWeather.setImage(img);
		labelTime.setText(time);
	}
}
