package logic.view.card.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AdminStatCardView implements Initializable {
	
	@FXML
	private Label labelNumber;
	
	public void setCard(int num) {
		labelNumber.setText(Integer.toString(num));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
