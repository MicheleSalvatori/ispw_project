package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.bean.VerbalizedBean;
import logic.controller.ViewVerbalizedExamsController;
import logic.exceptions.NullException;
import logic.exceptions.RecordNotFoundException;
import logic.view.card.element.ExamCard;

public class ExamPageView implements Initializable {

	@FXML
	private Label labelVE, labelGPA, labelWPA;

	@FXML
	private VBox vboxExam;
	
	private ViewVerbalizedExamsController controller;

	private List<VerbalizedBean> verbs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		controller = new ViewVerbalizedExamsController();

		try {
			verbs = controller.getVerbalizedExams();
			for (VerbalizedBean verbBean : verbs) {
				ExamCard examCard = new ExamCard(verbBean, verbs.indexOf(verbBean) + 1);
				vboxExam.getChildren().add(examCard);
			}
			
		} catch (NullException e) {
			vboxExam.getChildren().add(new Label("No exam found"));
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup media
		labelVE.setText(Integer.toString(verbs.size()));
		labelGPA.setText(Double.toString(controller.GPA(verbs)));
		labelWPA.setText(Double.toString(controller.WPA(verbs)));
	}
}