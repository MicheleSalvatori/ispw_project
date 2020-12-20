package logic.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertController {
	
	private AlertController() {}
	
	public static void buildInfoAlert(String message, String titleAlert) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleAlert);
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(message);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		alert.showAndWait();
	}

	public static boolean confirmation(String message) {
	 	Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
	 	alert.setHeaderText("");
		alert.setTitle("Confirmation");
		alert.showAndWait();
	
		return alert.getResult() == ButtonType.YES;
	}
}
