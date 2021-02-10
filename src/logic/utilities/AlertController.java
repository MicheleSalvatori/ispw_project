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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import logic.exceptions.CancelException;

public class AlertController {
	
	private static Stage stage;
	private static String error = "An error as occured.\nTry later.";
	public static final String PAGE_ERROR = "Page loading error";
	
	private static String alertRes = "/res/style/Alert.css";
	
	private static ColorAdjust adj;
	
	private AlertController() {
		
	}
	
	public static void setStage(Stage stage) {
		adj = new ColorAdjust(0, -0.9, -0.5, 0);
		GaussianBlur blur = new GaussianBlur(55);
		adj.setInput(blur);
		AlertController.stage = stage;
	}
	
	public static String getError() {
		return error;
	}
	
	public static void infoAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		setAlert(alert);
		alert.setContentText(message);
	   
		show(alert);
	}

	public static boolean confirmationAlert(String message) {
	 	Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
	 	setAlert(alert);
		
	    show(alert);
		
		return alert.getResult() == ButtonType.YES;
	}
		
	public static String changePassword() throws CancelException {
		Dialog<String> dialog = new Dialog<>();
		dialog.setHeaderText("Insert a new password");
		dialog.setGraphic(null);
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
		dialog.getDialogPane().getStylesheets().add(AlertController.class.getResource(alertRes).toExternalForm());
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		centerButtons(dialog.getDialogPane());
		dialog.initStyle(StageStyle.TRANSPARENT);
		
		PasswordField pwd = new PasswordField();
		pwd.setPromptText("Password");
		HBox content = new HBox();
	    content.setAlignment(Pos.CENTER);
	    HBox.setHgrow(pwd, Priority.ALWAYS);
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
	    
		return showDialog(dialog);
	}
	
	public static String emailInput() throws CancelException {
		Dialog<String> dialog = new Dialog<>();
		dialog.setHeaderText("Insert your email");
		dialog.setGraphic(null);
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
		dialog.getDialogPane().getStylesheets().add(AlertController.class.getResource(alertRes).toExternalForm());
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		centerButtons(dialog.getDialogPane());
		dialog.initStyle(StageStyle.TRANSPARENT);
		
		TextField text = new TextField();
		text.setPromptText("Email");
		HBox content = new HBox();
	    content.setAlignment(Pos.CENTER);
	    HBox.setHgrow(text, Priority.ALWAYS);
	    content.getChildren().add(text);
	    
	    
	    // Disable OK button until all text fields aren't empty
	    dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(Bindings.isEmpty(text.textProperty()));
	    
	    dialog.getDialogPane().setContent(content);
	    
	    dialog.setResultConverter(dialogButton -> {
	        if (dialogButton == ButtonType.OK) {
	            return text.getText();
	        }
	        return null;
	    });
	    
		return showDialog(dialog);
	}
	
	private static String showDialog(Dialog<String> dialog) throws CancelException {
		stage.getScene().getRoot().setEffect(adj);
		
		dialog.show();
		animation(dialog);
		dialog.close();
		
		Optional<String> result = dialog.showAndWait();
		stage.getScene().getRoot().setEffect(null);
		
		if (result.isPresent()) {
	    	return result.get();
	    }
		
	    throw new CancelException("Button cancel pressed");
	}
	
	public static int courseRequest(List<String> courses) {
		ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
		
		ObservableList<String> list = choiceDialog.getItems();
		for (String course : courses) {
			list.add(course);
		}
		
		choiceDialog.setTitle("Select a course");
		choiceDialog.setHeaderText("Select a course");
		choiceDialog.setGraphic(null);
		choiceDialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
		choiceDialog.getDialogPane().getStylesheets().add(AlertController.class.getResource(alertRes).toExternalForm());
		centerButtons(choiceDialog.getDialogPane());
		choiceDialog.initStyle(StageStyle.TRANSPARENT);
		
		stage.getScene().getRoot().setEffect(adj);
		
		choiceDialog.show();
		animation(choiceDialog);
		choiceDialog.close();
		
		choiceDialog.showAndWait();
		stage.getScene().getRoot().setEffect(null);
		
		Node comboBox = choiceDialog.getDialogPane().lookup(".combo-box");
		return ((ComboBox<?>) comboBox).getSelectionModel().getSelectedIndex();
	}
	
	public static Pair<String, String> timeSelector() {
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
	    	if ((!newValue) && (!hour.getText().matches("^(0[0-9]|1[0-9]|2[0-3])"))) {
	    			hour.setText("");
	    	}
	    });
	    
	    // Only number from 00 to 59
	    minutes.focusedProperty().addListener((arg0, oldValue, newValue) -> {
	    	if ((!newValue) && (!minutes.getText().matches("[0-5][0-9]"))) {
	    		minutes.setText("");
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

	    dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
	    dialog.getDialogPane().getStylesheets().add(AlertController.class.getResource(alertRes).toExternalForm());
	    dialog.initStyle(StageStyle.TRANSPARENT);
	    centerButtons(dialog.getDialogPane());
	    
		stage.getScene().getRoot().setEffect(adj);
		
		dialog.show();
		animation(dialog);
		dialog.close();
	    
	    Optional<Pair<String, String>> result = dialog.showAndWait();
	    stage.getScene().getRoot().setEffect(null);
	    
	    if (result.isPresent()) {
	    	return result.get();
	    }
	    return null;
	}
	
	// Center buttons in DialogPane
	private static void centerButtons(DialogPane dialogPane) {
		Region spacer = new Region();
		ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);	
		HBox.setHgrow(spacer, Priority.ALWAYS);
		dialogPane.applyCss();
		HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
		hboxDialogPane.getChildren().add(spacer);
	}
	
	// Animation for dialog
	private static void animation(Dialog<?> dialog) {
		double yIni = -dialog.getHeight();
		double yEnd = dialog.getY();
		dialog.setY(yIni+500);
		
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->dialog.setY(n1.doubleValue()));
		
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
	
	// Animation for stage
	public static void animation(Stage stage) {
		double yIni = -stage.getHeight();
		double yEnd = stage.getY();
		
		DoubleProperty yProperty = new SimpleDoubleProperty(yIni);
		yProperty.addListener((ob,n,n1)->stage.setY(n1.doubleValue()));
		
		Timeline timeIn = new Timeline();
		timeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(yProperty, yEnd, Interpolator.EASE_BOTH)));
		timeIn.play();
	}
	
	private static void show(Dialog<?> dialog) {
		// Add effect to the scene
		stage.getScene().getRoot().setEffect(adj);
		
		// Show animation of dialog
		dialog.show();
		animation(dialog);
		dialog.close();
		
		// Wait for an input by the user
		dialog.showAndWait();
		stage.getScene().getRoot().setEffect(null);
		dialog.close();
	}
	
	private static void setAlert(Dialog<?> dialog) {
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
		dialog.getDialogPane().getStylesheets().add(AlertController.class.getResource(alertRes).toExternalForm());
		centerButtons(dialog.getDialogPane());
		dialog.initStyle(StageStyle.TRANSPARENT);
	}
	
	public static void setupDialog(Scene scene, Stage stage) {
		scene.setFill(Color.TRANSPARENT);
		
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setResizable(false);
		
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(55);
	    adj.setInput(blur);
		
		PageLoader.getStage().getScene().getRoot().setEffect(adj);
		stage.show();
		animation(stage);
	}
}
