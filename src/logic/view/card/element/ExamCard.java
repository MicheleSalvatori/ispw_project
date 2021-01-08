package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.bean.VerbalizedBean;
import logic.view.card.controller.ExamCardView;

public class ExamCard extends AnchorPane {

	private ExamCardView examCardView = new ExamCardView();

	public ExamCard(VerbalizedBean verb, int num) throws IOException {
		URL url = new File("src/res/fxml/card/ExamCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(examCardView);
		this.getChildren().add(loader.load());

		examCardView.setCard(verb, num);
	}
}
