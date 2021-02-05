package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.view.card.controller.ProfessorStatCardView;
import logic.view.graphic.GraphicElement;

public class ProfessorStatCard extends GraphicElement {

	private ProfessorStatCardView professorCardView = new ProfessorStatCardView();
	
	public ProfessorStatCard(int num) {
		FXMLLoader loader = getLoader("src/res/fxml/card/ProfessorCard.fxml");
		loader.setController(professorCardView);
		load(loader);
		
		professorCardView.setCard(num);
	}
}
