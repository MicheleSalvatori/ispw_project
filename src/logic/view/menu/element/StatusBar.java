package logic.view.menu.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import logic.view.menu.controller.StatusBarView;

public class StatusBar {
	
	private static StatusBar instance = null;
	private HBox hbox = new HBox();
	
	private StatusBar() {
		
	}
	
	public static StatusBar getInstance() {
		if (instance == null) {
			instance = new StatusBar();
			loadView();
		}
		return instance;
	}
	
	private static void loadView() {
		try {
			URL url = new File("src/res/fxml/menu/StatusBar.fxml").toURI().toURL();
			FXMLLoader loader = new FXMLLoader(url);
			loader.setController(new StatusBarView());
			instance.hbox.getChildren().add(loader.load());
			
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Page loading error");
		}
	}
	
	public static void reset() {
		instance = null;
	}
	
	public HBox getPane() {
		return hbox;
	}
}
