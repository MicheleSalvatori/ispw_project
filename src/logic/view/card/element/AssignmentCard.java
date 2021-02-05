package logic.view.card.element;

import javafx.fxml.FXMLLoader;
import logic.bean.AssignmentBean;
import logic.view.card.controller.AssignmentCardView;
import logic.view.graphic.GraphicElement;

public class AssignmentCard extends GraphicElement {
	
	private AssignmentCardView assignmentCardView = new AssignmentCardView();
	
	public AssignmentCard(AssignmentBean assignment) {
		FXMLLoader loader = getLoader("src/res/fxml/card/AssignmentCard.fxml");
		loader.setController(assignmentCardView);
		load(loader);
		
		assignmentCardView.setCard(assignment);
	}
}
