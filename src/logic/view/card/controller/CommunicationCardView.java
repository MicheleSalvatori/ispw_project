package logic.view.card.controller;

import java.awt.TextArea;

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
	
	@FXML
	private TextArea textArea;
	
	public void setLabel(String text, String title, String date, String number) {
		textArea.setText(text);
		labelTitle.setText(title);
		labelDate.setText(date);
		labelNumber.setText(number);
	}
}
