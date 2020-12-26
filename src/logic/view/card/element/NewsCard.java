package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.NewsCardView;

public class NewsCard extends AnchorPane {
	
	private NewsCardView newsCardView = new NewsCardView();
	
	public NewsCard(String title, String date, String number) throws IOException {
		URL url = new File("src/res/fxml/card/NewsCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(newsCardView);
		this.getChildren().add(loader.load());

		newsCardView.setLabel(title, date, number);
	}
}
