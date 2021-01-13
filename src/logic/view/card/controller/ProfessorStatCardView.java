package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfessorStatCardView {
	
	@FXML
	private Label labelNumber;
	
	public void setCard(int num) {
		labelNumber.setText(Integer.toString(num));
	}
}
