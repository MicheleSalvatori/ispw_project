package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.view.card.element.ExamCard;

public class ExamPageView implements Initializable {

    @FXML
    private ScrollPane scrollExams;
    @FXML
    private AnchorPane scrollAnchor;
    @FXML
    private Label labelVE;

    @FXML
    private Label labelGPA;

    @FXML
    private Label labelWPA;
    private VBox examBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		examBox = new VBox();
		for(int i = 1; i<10; i++) {
			ExamCard examCard;
			try {
				examCard = new ExamCard(String.valueOf(i), "22/12/2020", "9", "18", "Calcolatori Elettronici", "CE");
				examBox.getChildren().add(examCard);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		examBox.setSpacing(10);
		examBox.setPadding(new Insets(13, 10, 10, 10));
//		scrollExams.setContent(examBox);
		scrollAnchor.getChildren().add(examBox);
		
//	Setup media
		labelGPA.setText("23.7");
		labelWPA.setText("25");
		labelVE.setText("8");
		
	}

}
