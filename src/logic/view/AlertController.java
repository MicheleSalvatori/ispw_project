package logic.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

public class AlertController {
	
	private AlertController() {}
	
	public static void buildInfoAlert(String message, String titleAlert) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleAlert);
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(message);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.initStyle(StageStyle.TRANSPARENT);
		
		BoxBlur blur = new BoxBlur(3, 3, 3);
		alert.showAndWait();
	}

	public static boolean confirmation(String message) {
	 	Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
	 	alert.setTitle("Confirmation");
	 	alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.showAndWait();
		
		return alert.getResult() == ButtonType.YES;
	}
	
	private static void centerButtons(DialogPane dialogPane) {
		Region spacer = new Region();
		ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);	
		HBox.setHgrow(spacer, Priority.ALWAYS);
		dialogPane.applyCss();
		HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
		hboxDialogPane.getChildren().add(spacer);
	}
}
