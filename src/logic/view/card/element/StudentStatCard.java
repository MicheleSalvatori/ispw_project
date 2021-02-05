package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.view.card.controller.StudentStatCardView;
import logic.view.graphic.GraphicElement;

public class StudentStatCard extends GraphicElement {
	
	private StudentStatCardView studentCardView = new StudentStatCardView();
	
	public StudentStatCard(int num, String first, String second) {
		FXMLLoader loader = getLoader("src/res/fxml/card/StudentCard.fxml");
		loader.setController(studentCardView);
		load(loader);
		
		studentCardView.setCard(num, first, second);
	}
}
