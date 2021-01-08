package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import logic.bean.AnswerBean;
import logic.utilities.SQLConverter;

public class AnswerCardView {

    @FXML
    private TextArea textAnswer;

    @FXML
    private Label labelStudent;

    @FXML
    private Label labelDate;
    
    // TODO 
    private AnswerBean answer;
    
    public void setCard(AnswerBean answer) {
    	this.answer = answer;
    	
    	labelStudent.setText(answer.getStudent().getName() + " " + answer.getStudent().getSurname());
		labelDate.setText(SQLConverter.date(answer.getDate()));
		textAnswer.setText(answer.getText());
    }
}