package logic.view.graphic;

import javafx.scene.layout.AnchorPane;

public abstract class GraphicElement {
	
	private AnchorPane pane = new AnchorPane();
	
	protected GraphicElement() {
		
	}
	
	public AnchorPane getPane() {
		return pane;
	}
}
