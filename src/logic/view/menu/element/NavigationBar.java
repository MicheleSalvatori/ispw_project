package logic.view.menu.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import logic.view.menu.controller.NavigationBarView;

public class NavigationBar {
	
	private static NavigationBar instance = null;
	private static VBox vbox = new VBox();
	
	private NavigationBar() {
		
	}
	
	public static NavigationBar getInstance() {
		if (instance == null) {
			instance = new NavigationBar();
			loadView();
		}
		return instance;
	}
	
	private static void loadView() {
		try {
			URL url = new File("src/res/fxml/menu/NavigationBar.fxml").toURI().toURL();
			FXMLLoader loader = new FXMLLoader(url);
			loader.setController(new NavigationBarView());
			vbox.getChildren().add(loader.load());
		
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Page loading error");
		}
	}
	
	public static void setInstance(NavigationBar navBar) {
		instance = navBar;
	}
	
	public VBox getPane() {
		return vbox;
	}
}
