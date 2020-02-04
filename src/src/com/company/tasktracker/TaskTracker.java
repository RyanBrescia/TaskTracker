package src.com.company.tasktracker;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.com.company.ui.AddWindow;
import src.com.company.ui.DeleteWindow;
import src.com.company.ui.MainWindow;

public class TaskTracker extends Application{
	
	private static Map<SceneName,Scene> scenes = new HashMap<SceneName,Scene>();
	
	public static void main(String[] args) {
		//Main method
		launch(args);
	}

	@Override
	public void start(Stage pStage) throws Exception {
		
		//create new model to pass to all observer objects
		EventModel events = new EventModel();
		
		//put scenes in a map to get later
		scenes.put(SceneName.Main,new MainWindow().buildScene(events,pStage));
		scenes.put(SceneName.AddMenu, new AddWindow().buildScene(events,pStage));
		scenes.put(SceneName.DeleteMenu, new DeleteWindow().buildScene(events,pStage));
		
		//setScene and set size 
		pStage.setScene(scenes.get(SceneName.Main));
		pStage.setHeight(500);
		pStage.setWidth(500);
		pStage.show();
	}
	
	public static Map<SceneName,Scene> getScenes(){
		return scenes;
	}

}
