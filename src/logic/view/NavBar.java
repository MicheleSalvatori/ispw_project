package logic.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NavBar extends VBox{
	private static NavBar instance = null;
	
	private NavBar() {
	}
	
	public static NavBar getInstance() throws IOException  {
		if (instance == null) {
			instance = new NavBar();
			loadView();
		}
		
		return instance;
	}
	
	private static void loadView() throws IOException {
		URL url = new File("src/res/fxml/NavMenu.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(new NavBarController());
		instance.getChildren().add(loader.load());
	}
}
