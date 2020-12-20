package logic.view.card.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StudentStatCardView implements Initializable {
	
	@FXML
	private Label labelNumber, label1, label2;
	
	public void setCard(int num, String first, String second) {
		labelNumber.setText(Integer.toString(num));
		label1.setText(first);
		label2.setText(second);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
