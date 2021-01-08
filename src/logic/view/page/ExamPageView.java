package logic.view.page;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.bean.VerbalizedBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Verbalized;
import logic.model.dao.VerbalizedDAO;
import logic.view.card.element.ExamCard;

public class ExamPageView implements Initializable {

    @FXML
    private Label labelVE, labelGPA, labelWPA;
    
    @FXML
    private VBox vboxExam;
    
    private List<Verbalized> verbs;  //TODO usare List<BEAN> SI

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			verbs = VerbalizedDAO.getVerbalizedExams(Session.getSession().getUsername());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Verbalized verb : verbs) {
			try {
				VerbalizedBean verbBean = new VerbalizedBean();
				verbBean.setCourse(verb.getCourse());
				verbBean.setDate(verb.getDate());
				verbBean.setGrade(verb.getGrade());
				verbBean.setStudent(verb.getStudent());
				
				ExamCard examCard = new ExamCard(verbBean, verbs.indexOf(verb)+1);
				vboxExam.getChildren().add(examCard);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Setup media
		labelVE.setText(Integer.toString(verbs.size()));
		labelGPA.setText(Double.toString(GPA()));
		labelWPA.setText(Double.toString(WPA()));
	}
	
	// Calculate GPA
	private double GPA() {

		double tot = 0;
		for (Verbalized verb : verbs) {
			tot += verb.getGrade();
		}
		
		return tot/verbs.size();
	}
	
	// Calculate WPA
	private double WPA() {
		
		double tot = 0;
		int credits = 0;
		for (Verbalized verb : verbs) {
			tot += verb.getGrade() * Integer.parseInt(verb.getCourse().getCredits());
			credits += Integer.parseInt(verb.getCourse().getCredits());
		}
		System.out.println(tot + "   " + credits);
		
		return round(tot/credits);
	}
	
	// Round double by 2 decimal places
	private double round(double value) {
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}