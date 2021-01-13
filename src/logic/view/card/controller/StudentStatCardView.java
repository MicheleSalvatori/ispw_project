package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentStatCardView {
	
	@FXML
	private Label labelNumber;
	
	@FXML
	private Label label1;
	
	@FXML
	private Label label2;
	
	public void setCard(int num, String first, String second) {
		labelNumber.setText(Integer.toString(num));
		label1.setText(first);
		label2.setText(second);
	}
}
