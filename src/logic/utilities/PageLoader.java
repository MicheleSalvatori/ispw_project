package logic.utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.view.NavBar;
import logic.view.StatusBar;

public class PageLoader {
	private static PageLoader instance = null;
	private Page page;
	private Stage primaryStage;
	private HBox mainLayoutHBox;
	private Scene scene;
	
	private PageLoader() {}
	
	public static PageLoader getInstance() {
		if (instance == null) {
			instance = new PageLoader();
		}
		return instance;
	}
	
	public void buildPage(Page page, ActionEvent event) throws IOException {
		this.page = page;
		if (page.getStageTitle() == "App - Signup" || page.getStageTitle() == "App - Login") {
			loadPageNoNavBar(event);
		}
		else {
			loadPage(event);
		}
	}

	private void loadPage(ActionEvent ae) throws IOException {
		Node source = (Node) ae.getSource();
		primaryStage = (Stage) source.getScene().getWindow();
		URL url = new File(page.getRes()).toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		
		configPage(loader.load());
	}
	
	private NavBar configNavBar() throws IOException {
		return NavBar.getInstance();
	}
	
	private HBox configStatusBar() throws IOException {
		HBox statusBarHBox = new HBox(StatusBar.getInstance());
		statusBarHBox.setAlignment(Pos.TOP_RIGHT);
		HBox.setMargin(StatusBar.getInstance(), new Insets(54.0, 80.0, 0, 0));
		return statusBarHBox;
	}
	
	private void configPage(Parent pageView) throws IOException {
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(configStatusBar(), pageView);
		mainLayoutHBox = new HBox(configNavBar(), vBox);						
		HBox.setMargin(NavBar.getInstance(), new Insets(24.0, 72.0, 24.0, 32.0));
		
		scene = new Scene(mainLayoutHBox);
		primaryStage.setScene(scene);
		primaryStage.setTitle(page.getStageTitle());
		primaryStage.show();
	}

	private void loadPageNoNavBar(ActionEvent ae) throws IOException {
		Node source = (Node) ae.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		URL url = new File(page.getRes()).toURI().toURL();
		FXMLLoader loader = new FXMLLoader(url);
		Parent parent = loader.load();

		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle(page.getStageTitle());
		stage.show();
	}

}
