package src.com.company.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import src.com.company.tasktracker.Activity;
import src.com.company.tasktracker.EventModel;
import src.com.company.tasktracker.SceneName;

public class DeleteWindow implements Window, NewActivityObserver,DeleteObserver {
	
	//fields
	GridPane root;
	Stage aStage;
	EventModel aModel;
	Scene aScene;
	
	//save the header and footer
	Label aTitle;
	BackButton aBack;
	EditTile localEditTile;
	
	//observable activities
	ObservableList<String> observableActivities;
	
	@Override
	public Scene buildScene(EventModel events, Stage pStage) {
		
		//Build DeleteWindow Scene to be stored with other scenes
		aStage = pStage;
		aModel = events;
		
		aModel.addNewActivityObserver(this);
		aModel.addDeleteObserver(this);
		
		//Title
		Label title = new Label("Items");
		aTitle = title;
		
		//ListView of Activities
		ObservableList<String> activities = FXCollections.observableArrayList(aModel.getSummary());
		this.observableActivities = activities;
		ListView<String> activityList = new ListView<String>(this.observableActivities);
		
		//SEARCH BAR -> update list with relevant Activities
		TextField search = new TextField();
		search.setPromptText("Find Item");
		search.textProperty().addListener((observable,old,newItem)-> {
			System.out.println("Old: "+old+" New:"+newItem);
			//update the list of items to be displayed
			if(newItem.isEmpty()) {
				System.out.println("Clearing");
				this.observableActivities.clear(); //need to notify ListView Object
				ObservableList<String> newActivities = FXCollections.observableArrayList(aModel.getSummary());
				for(String newAct:newActivities) {
					this.observableActivities.add(newAct);
				}
			}else {
				System.out.println("Generating new list of options");
				this.update(newItem);
			}
		});
		
		//back button
		BackButton back = new BackButton(events,pStage,SceneName.Main);
		
		VBox searchTile = new VBox(search,activityList,back);
		
		//TODO EditTile -> VBox(Title + Activity TextFields+DatePickers)
		//Edit Activity Title
		Label editTitle = new Label("Edit Activity");
		editTitle.setAlignment(Pos.CENTER);
		
		EditTile editTile = new EditTile(aModel);
		
		//Reset Button
		Button reset = new Button("Reset Fields");
		reset.setOnAction((event)->{
			if(!aModel.isEmpty()) {
				editTile.reset();
			}
		});
		
		//TODO Save Changes Button
		Button saveChanges = new Button("Save Changes");
		saveChanges.setOnAction((event)->{
			activityList.getSelectionModel().clearSelection();
			editTile.updateActivity();
			editTile.updateActivities();
		});
		
		//Delete Button
		Button delete = new Button("Delete");
		delete.setOnAction((event)->{
			System.out.println("Remove: " + activityList.getSelectionModel().getSelectedItem());
			aModel.deleteItem(activityList.getSelectionModel().getSelectedItem());
			editTile.refresh();
		});
		
		//Summary of Activity Object
		activityList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			//update EditTile with selected activity
			if(newValue!=null) {
				//enable buttons
				editTile.newActivity(aModel.getActivityFromString(newValue));
			}
		});
		
		HBox editButtons = new HBox(delete,reset,saveChanges);
		
		VBox editSection = new VBox(editTitle,editTile,editButtons);
		editSection.setAlignment(Pos.TOP_CENTER);
		
		//SearchEditTile
		HBox searchEditSection = new HBox(searchTile,editSection);
		
		//CompleteTile -> VBox(Title,HBox(SearchTile,EditTile))
		VBox completeTile = new VBox(title,searchEditSection);
		Scene newScene = new Scene(completeTile);
		aScene = newScene;
		return aScene;
	}
	
	public void update(String changed) {
		//loop through contents and delete
		ArrayList<Activity> events = new ArrayList<Activity>(aModel.getActivities());
		ArrayList<Activity> selected = new ArrayList<Activity>();
		
		System.out.println("Updating");
		
		for(Activity event:events) {
			boolean matched = true;
			String name = event.getName();
			if(name.length()<changed.length()) {
				continue;
			}
			for(int i=0;i<changed.length();i++) {
				if(name.charAt(i)!=changed.charAt(i)) {
					System.out.println("not equal");
					matched = false;
					continue;
				}				
			}
			if(matched==true) {
				selected.add(event);
				System.out.println("Found a match:"+ name.substring(0, changed.length()) +" "+changed.substring(0, changed.length()));
			}
		}
		
		System.out.println("Selected: "+selected.toString());
		
		observableActivities.clear();
		
		//loop through selected and see if exists in current ObservableList
		for(Activity act:selected) {
			observableActivities.add(act.toString());
		}
		
	}

	@Override
	public void newActivities(ArrayList<String> newActivities) {
		//callback method for NewActivityObservers
		for(String newActivity:newActivities) {
			observableActivities.add(newActivity);
		}
	}

	@Override
	public void deleteItem(String deleted) {
		//Callback method for DeleteObserver
		System.out.println("deleting item: " + deleted);
		observableActivities.remove(deleted);
		//TODO turn off delete button if model is empty
		
		//TODO refresh editTile
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
