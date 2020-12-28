package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CourseCardView {

	@FXML
	private Label labelName, labelProfessor, labelYear, labelSemester;
	
	public void setLabel(String name, String professor, String year, String semester) {
		labelName.setText(name);
		labelProfessor.setText(professor);
		labelYear.setText(year);
		labelSemester.setText(semester);
	}
}
