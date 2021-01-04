package logic.utilities;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class AlertController {
//	TODO riorganizzare metodi utilizzando costruttori diversi per node e event 
//	private static Node node;
//	private static ActionEvent event;
//	TODO unire confirmation 1 e 2
	private AlertController() {}
	
//	public AlertController(Node node) {
//		this.node = node;
//	}
//	
//	public AlertController(ActionEvent event) {
//		this.event = event;
//	}
	
	public static void buildInfoAlert(String message, String titleAlert, ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleAlert);
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(message);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.initStyle(StageStyle.TRANSPARENT);
		
		Node source = (Node) event.getSource();
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		alert.show();
		animation(alert);
		alert.close();
		
		alert.showAndWait();
		source.getScene().getRoot().setEffect(null);
		alert.close();
	}
	
	public static void buildInfoAlert(String message, String titleAlert, Node source) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleAlert);
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(message);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.initStyle(StageStyle.TRANSPARENT);
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		alert.show();
		animation(alert);
		alert.close();
		
		alert.showAndWait();
		source.getScene().getRoot().setEffect(null);
		alert.close();
	}

	public static boolean confirmation(String message, ActionEvent event) {
	 	Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
	 	alert.setTitle("Confirmation");
	 	alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.initStyle(StageStyle.TRANSPARENT);
		
		Node source = (Node) event.getSource();
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		alert.show();
		animation(alert);
		alert.close();
		
		alert.showAndWait();
		source.getScene().getRoot().setEffect(null);
		
		return alert.getResult() == ButtonType.YES;
	}
	
	public static boolean confirmation_2(String message, Node source) {
	 	Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
	 	alert.setTitle("Confirmation");
	 	alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.getDialogPane().getStylesheets().add(AlertController.class.getResource("../../res/style/Alert.css").toExternalForm());
		centerButtons(alert.getDialogPane());
		alert.initStyle(StageStyle.TRANSPARENT);
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		alert.show();
		animation(alert);
		alert.close();
		
		alert.showAndWait();
		source.getScene().getRoot().setEffect(null);
		
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
