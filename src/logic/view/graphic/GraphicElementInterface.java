package logic.view.graphic;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GraphicElementInterface {
	
	private String element;
	
	public GraphicElementInterface(String element) {
		this.element = element;
	}
	
	public AnchorPane draw() throws IOException {
		return FXMLLoader.<AnchorPane>load(getClass().getResource(element));
	}
}
