package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.view.card.element.ExamCard;

public class ExamPageView implements Initializable {

    @FXML
    private Label labelVE;

    @FXML
    private Label labelGPA;

    @FXML
    private Label labelWPA;
    
    @FXML
    private VBox vboxExam;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(int i = 1; i<10; i++) {
			ExamCard examCard;
			try {
				examCard = new ExamCard(String.valueOf(i), "22/12/2020", "9", "18", "Calcolatori Elettronici", "CE");
				vboxExam.getChildren().add(examCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		//	Setup media
		labelGPA.setText("23.7");
		labelWPA.setText("25");
		labelVE.setText("8");
		
	}

}
