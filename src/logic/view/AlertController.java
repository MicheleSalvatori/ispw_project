package logic.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

		alert.show();
		animation(alert);
	}

	public static boolean confirmation(String message) {
	 	Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
	 	alert.setTitle("Confirmation");
	 	alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.initStyle(StageStyle.TRANSPARENT);
		
		alert.show();
		animation(alert);
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
	
	private static void animation(Alert alert) {
		double yIni = -alert.getHeight();
		double yEnd = alert.getY();
		alert.setY(yIni+500);
		
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->alert.setY(n1.doubleValue()));
		
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
}
