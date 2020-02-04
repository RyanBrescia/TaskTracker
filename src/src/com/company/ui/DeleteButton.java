package src.com.company.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.com.company.tasktracker.SceneName;
import src.com.company.tasktracker.TaskTracker;

public class DeleteButton extends Parent implements EventHandler<ActionEvent> {
	
	Button button = new Button("Delete Entry");
	Stage aStage;
	
	
	DeleteButton(Stage pStage){
		aStage = pStage;
		button.setOnAction(this);
		getChildren().add(button);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// generate delete menu -> (TITLE) / (INFORMATION) / BUTTONS
		aStage.setScene(TaskTracker.getScenes().get(SceneName.DeleteMenu));
		aStage.show();
	}

}
