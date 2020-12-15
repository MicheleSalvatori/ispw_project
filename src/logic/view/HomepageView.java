package logic.view;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class HomepageView implements Initializable {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnExams;
    
    
    @FXML
    public void homeButton(ActionEvent event) throws IOException {
    	PageLoader pageLoader = new PageLoader(Page.HOMEPAGE, event);
    	System.out.println("Homepage");
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
