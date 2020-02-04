package src.com.company.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.com.company.tasktracker.EventModel;

//import com.company.fridgefinder.FridgeFinder;

public class AddButton extends Parent implements EventHandler<ActionEvent> {

	//fields
	private Button button = new Button("Add task(s)");
	
	//scene model -> update when Action occurs
	private Stage aStage;
	private EventModel aModel;
	
	AddButton(Stage pStage, EventModel pModel){
		
		aStage = pStage;
		aModel = pModel;
		
		getChildren().add(button);
		
		//event handler -> generate different window
		button.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		aStage.setScene(new AddWindow().buildScene(aModel,aStage));
		aStage.show();
	}
	
}
