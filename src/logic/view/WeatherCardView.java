package logic.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WeatherCardView implements Initializable {

	@FXML
	private Label labelTemp, labelTime;
	@FXML
	private ImageView imgWeather;
	
	public void setCard(String temp, String time) throws FileNotFoundException {
		labelTemp.setText(temp);
		//imgWeather.setImage(img);
		labelTime.setText(time);
		
		InputStream stream = new FileInputStream("src/res/png/Weather/CloudSun.png");
		Image img = new Image(stream);
		imgWeather.setImage(img);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
