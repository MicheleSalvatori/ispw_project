package logic.view.menu.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import logic.view.menu.controller.NavigationBarView;

public class NavigationBar extends VBox {
	private static NavigationBar instance = null;
	
	private NavigationBar() {
	}
	
	public static NavigationBar getInstance() throws IOException  {
		if (instance == null) {
			instance = new NavigationBar();
			loadView();
		}
		
		return instance;
	}
	
	private static void loadView() throws IOException {
		URL url = new File("src/res/fxml/menu/NavigationBar.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(new NavigationBarView());
		instance.getChildren().add(loader.load());
	}
	
	public static void setInstance(NavigationBar navBar) {
		instance = navBar;
	}
}
