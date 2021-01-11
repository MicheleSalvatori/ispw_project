package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.controller.InsertQuestionController;
import logic.model.Student;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class NewNewsView implements Initializable {

	@FXML
	private TextArea textNewsDescr;

	@FXML
	private TextField textNewsTitle;

	@FXML
	private Button btnSubmit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void saveNews(ActionEvent ae) {
		System.out.println("Save News button");
	}

}
