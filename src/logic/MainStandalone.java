package logic;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStandalone extends Application{
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("SystemName");
		URL url = new File("src/res/fxml/Login.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 1440, 900);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(false);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
