package logic.view.ProfessorCard;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ProfessorCard extends AnchorPane {

	private ProfessorCardView professorCardView = new ProfessorCardView();
	
	public ProfessorCard(int num) throws IOException {
		URL url = new File("src/res/fxml/ProfessorCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(professorCardView);
		this.getChildren().add(loader.load());
		
		professorCardView.setCard(num);
	}
}
