package src.com.company.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.com.company.tasktracker.EventModel;
import src.com.company.tasktracker.SceneName;
import src.com.company.tasktracker.TaskTracker;

public class BackButton extends Parent implements EventHandler<ActionEvent> {
	
	//fields
	Button back = new Button("Back");
	EventModel aModel;
	Stage aStage;
	
	SceneName current;
	
	//window hierarchy -> tree saved as an array?
	
	BackButton(EventModel pModel, Stage pStage, SceneName prev){
		aModel = pModel;
		aStage = pStage;
		current = prev;
		back.setOnAction(this);
		getChildren().add(back);
	}

	@Override
	public void handle(ActionEvent arg0) {
		//handler for back button
		//TODO reset window 
		aStage.setScene(TaskTracker.getScenes().get(current));
		aStage.show();
		
	}

}
