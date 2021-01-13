package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CommunicationCardView {

	@FXML
	private Label labelTitle;
	
	@FXML
	private Label labelDate;
	
	@FXML
	private Label labelNumber;
	
	@FXML
	private Button btnView;
	
	public void setLabel(String title, String date, String number) {
		labelTitle.setText(title);
		labelDate.setText(date);
		labelNumber.setText(number);
	}
}
