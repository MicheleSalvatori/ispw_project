package logic.utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PageLoader {
	private Page page;
	
	public PageLoader(Page page, ActionEvent event) throws IOException {
		this.page = page;
		loadPage(event);
	}
	
	private void loadPage(ActionEvent ae) throws IOException {
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
