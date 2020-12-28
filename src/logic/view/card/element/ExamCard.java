package logic.view.card.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import logic.view.card.controller.ExamCardView;

public class ExamCard extends AnchorPane {

	private ExamCardView examCardView = new ExamCardView();

	public ExamCard(String number, String date, String cfu, String vote, String name, String course)
			throws IOException {
		URL url = new File("src/res/fxml/card/ExamCard.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(examCardView);
		this.getChildren().add(loader.load());

		examCardView.setLabel(number, date, cfu, vote, name, course);
	}
}
