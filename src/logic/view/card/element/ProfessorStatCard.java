package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.ProfessorStatCardView;

public class ProfessorStatCard extends AnchorPane {

	private ProfessorStatCardView professorCardView = new ProfessorStatCardView();
	
	public ProfessorStatCard(int num) throws IOException {
		URL url = new File("src/res/fxml/ProfessorCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(professorCardView);
		this.getChildren().add(loader.load());
		
		professorCardView.setCard(num);
	}
}
