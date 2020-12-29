package logic.view.page;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import logic.utilities.AlertController;

public class SchedulePageView implements Initializable {
	
	@FXML
	private TextField textTimeLesson, textTimeExam;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void time(ActionEvent ae) {
		Pair<String, String> result = AlertController.time(ae);
		
		switch (((Node) ae.getSource()).getId()) {
			
		case "btnTimeLesson":
			if (result != null) {	
				textTimeLesson.setText(result.getKey() + ":" + result.getValue());
			}
			break;
			
		case "btnTimeExam":
			if (result != null) {	
				textTimeExam.setText(result.getKey() + ":" + result.getValue());
			}
			break;
		}
		
	}

}
