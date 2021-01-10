package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.AdminStatCardView;

public class AdminStatCard extends AnchorPane {

	private AdminStatCardView adminCardView = new AdminStatCardView();
	
	public AdminStatCard(int num) throws IOException {
		URL url = new File("src/res/fxml/card/AdminCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(adminCardView);
		this.getChildren().add(loader.load());
		
		adminCardView.setCard(num);
	}
}
