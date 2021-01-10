package logic;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.utilities.AlertController;
import logic.utilities.AppProperties;

public class MainStandalone extends Application{
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(AppProperties.getInstance().getProperty("title"));
		URL url = new File("src/res/fxml/page/LoginPage.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		AlertController.setStage(primaryStage); // Set stage for alert controller
		
		Scene scene = new Scene(root, 1440, 900);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(false);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/res/png/Logo.png"));
		primaryStage.show();
	}
}
