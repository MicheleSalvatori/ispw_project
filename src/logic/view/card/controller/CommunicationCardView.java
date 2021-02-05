package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CommunicationCardView {

	@FXML
	private Label labelTitle;
	
	@FXML
	private Label labelDate;
	
	@FXML
	private Label labelNumber;
	
	@FXML
	private TextArea textArea;
	
	public void setLabel(String text, String title, String date, String number) {
		textArea.setText(text);
		labelTitle.setText(title);
		labelDate.setText(date);
		labelNumber.setText(number);
	}
}
