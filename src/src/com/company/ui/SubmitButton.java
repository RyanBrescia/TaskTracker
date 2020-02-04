package src.com.company.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.com.company.tasktracker.EventModel;

public class SubmitButton extends Parent implements EventHandler<ActionEvent> {
	
	Stage aStage;
	EventModel aModel;
	Button submitButton = new Button("Submit");
	AddWindow aWindow;
	
	SubmitButton(Stage pStage,EventModel pModel,AddWindow pWindow){
		aStage = pStage;
		aModel = pModel;
		aWindow = pWindow;
		getChildren().add(submitButton);
		//submitButton.setDisable(true);
		submitButton.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		// get TextFields from Window
		// call add() method on model
		aModel.addItems();
		//update the window with summaries of the addition of Activities
	}
	
	public void enable() {
		submitButton.setDisable(false);
	}

}
