package logic.utilities;

import java.util.List;
import java.util.Optional;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;

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
	

	public static void buildInfoAlert(String message, String titleAlert, Scene scene) {
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
		scene.getRoot().setEffect(adj);
		
		alert.show();
		animation(alert);
		alert.close();
		
		alert.showAndWait();
		scene.getRoot().setEffect(null);
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
		
	public static String changePassword(ActionEvent event) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setHeaderText("Insert new password");
		dialog.setGraphic(null);
		dialog.getDialogPane().getStylesheets().add(AlertController.class.getResource("/res/style/Alert.css").toExternalForm());
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		centerButtons(dialog.getDialogPane());
		dialog.initStyle(StageStyle.TRANSPARENT);
		
		PasswordField pwd = new PasswordField();
		pwd.setPromptText("Password");
		HBox content = new HBox();
	    content.setAlignment(Pos.CENTER);
	    HBox.setHgrow(pwd, Priority.ALWAYS);
	    //pwd.setMinWidth(dialog.getDialogPane().getWidth());
	    content.getChildren().add(pwd);
	    
	    
	    // Disable OK button until all text fields aren't empty
	    dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty()
	    						.bind(Bindings.isEmpty(pwd.textProperty()));
	    
	    dialog.getDialogPane().setContent(content);
	    
	    dialog.setResultConverter(dialogButton -> {
	        if (dialogButton == ButtonType.OK) {
	            return pwd.getText();
	        }
	        return null;
	    });
	    
		Node source = (Node) event.getSource();
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		dialog.show();
		animation(dialog);
		dialog.close();
		
		Optional<String> result = dialog.showAndWait();
		source.getScene().getRoot().setEffect(null);
		
		if (result.isPresent()) {
	    	return result.get();
	    }
	    return null;
	}
	
	public static int courseRequest(ActionEvent event, List<String> courses) {
		ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
		
		ObservableList<String> list = choiceDialog.getItems();
		for (String course : courses) {
			list.add(course);
		}
		
		choiceDialog.setTitle("Select a course");
		choiceDialog.setHeaderText("Select a course");
		choiceDialog.setGraphic(null);
		choiceDialog.getDialogPane().getStylesheets().add(AlertController.class.getResource("/res/style/Alert.css").toExternalForm());
		centerButtons(choiceDialog.getDialogPane());
		choiceDialog.initStyle(StageStyle.TRANSPARENT);
		
		Node source = (Node) event.getSource();
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		choiceDialog.show();
		animation(choiceDialog);
		choiceDialog.close();
		
		choiceDialog.showAndWait();
		source.getScene().getRoot().setEffect(null);
		
		Node comboBox = choiceDialog.getDialogPane().lookup(".combo-box");
		int index = ((ComboBox<?>) comboBox).getSelectionModel().getSelectedIndex();
		
		return index;
	}
	
	public static Pair<String, String> time(ActionEvent event) {
		// Create the custom dialog.
	    Dialog<Pair<String, String>> dialog = new Dialog<>();
	    dialog.setHeaderText("Insert time");

	    // Set the button types.
	    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(20, 10, 10, 10));

	    TextField hour = new TextField();
	    hour.setPromptText("Hour");
	    TextField minutes = new TextField();
	    minutes.setPromptText("Minutes");
	    
	    // Only number from 00 to 23
	    hour.focusedProperty().addListener((arg0, oldValue, newValue) -> {
	    	if (!newValue) {
	    		if (!hour.getText().matches("^(0[0-9]|1[0-9]|2[0-3])")) {
	    			hour.setText("");
	    		}
	    	}
	    });
	    
	    // Only number from 00 to 59
	    minutes.focusedProperty().addListener((arg0, oldValue, newValue) -> {
	    	if (!newValue) {
	    		if (!minutes.getText().matches("[0-5][0-9]")) {
	    			minutes.setText("");
	    		}
	    	}
	    });
	    
	    // Disable OK button until all text fields aren't empty
	    dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty()
	    						.bind(Bindings.isEmpty(hour.textProperty())
	    						.or(Bindings.isEmpty(minutes.textProperty())));
	    
	    gridPane.add(hour, 0, 0);
	    gridPane.add(new Label(":"), 1, 0);
	    gridPane.add(minutes, 2, 0);

	    dialog.getDialogPane().setContent(gridPane);

	    // Convert the result to a pair when the OK button is clicked.
	    dialog.setResultConverter(dialogButton -> {
	        if (dialogButton == ButtonType.OK) {
	            return new Pair<>(hour.getText(), minutes.getText());
	        }
	        return null;
	    });

	    dialog.getDialogPane().getStylesheets().add(AlertController.class.getResource("/res/style/Alert.css").toExternalForm());
	    dialog.initStyle(StageStyle.TRANSPARENT);
	    centerButtons(dialog.getDialogPane());
	    
	    Node source = (Node) event.getSource();
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		source.getScene().getRoot().setEffect(adj);
		
		dialog.show();
		animation(dialog);
		dialog.close();
	    
	    Optional<Pair<String, String>> result = dialog.showAndWait();
	    source.getScene().getRoot().setEffect(null);
	    
	    if (result.isPresent()) {
	    	return result.get();
	    }
	    return null;
	}
	
	
	
	private static void centerButtons(DialogPane dialogPane) {
		Region spacer = new Region();
		ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);	
		HBox.setHgrow(spacer, Priority.ALWAYS);
		dialogPane.applyCss();
		HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
		hboxDialogPane.getChildren().add(spacer);
	}
	
	// Animation for ChoiceDialog type
	private static void animation(ChoiceDialog<String> alert) {
		double yIni = -alert.getHeight();
		double yEnd = alert.getY();
		alert.setY(yIni+500);
			
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->alert.setY(n1.doubleValue()));
			
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
	
	// Animation for Dialog type
	private static void animation(Dialog<?> alert) {
		double yIni = -alert.getHeight();
		double yEnd = alert.getY();
		alert.setY(yIni+500);
		
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->alert.setY(n1.doubleValue()));
		
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
	
	// Animation for Alert type
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
