package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NewsCardView {

	@FXML
	private Label labelTitle, labelDate, labelNumber;
	@FXML
	private Button btnView;
	
	public void setLabel(String title, String date, String number) {
		labelTitle.setText(title);
		labelDate.setText(date);
		labelNumber.setText(number);
	}
}
