package logic.view.graphic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GraphicElement {
	
	private AnchorPane pane = new AnchorPane();
	
	public FXMLLoader getLoader(String src) {
		
		try {
			URL url = new File(src).toURI().toURL();
			return new FXMLLoader(url);
		
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Element loading error");
			return null;
		}
	}
	
	public AnchorPane getPane() {
		return pane;
	}
	
	public void load(FXMLLoader loader) {
		try {
			getPane().getChildren().add(loader.load());
			
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Element loading error");
		}
	}
}
