package logic.view.page;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ProfessorPageView implements Initializable {
	
	@FXML
	private Label labelSite, labelLinkedin, labelEmail, labelOffice, labelName;
	
	@FXML
	private TextArea textDesc;
	
	@FXML
	private Rectangle rect;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setAvatar("/res/png/avatar.png");
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rect.setFill(pattern);
	}
}
