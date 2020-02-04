package src.com.company.ui;

import javafx.stage.Stage;
import src.com.company.tasktracker.EventModel;
import javafx.scene.Scene;

public interface Window {
	
	Scene buildScene(EventModel events,Stage pStage);
	
	//TODO void reset() -> reset window to original 
	void reset();

}
