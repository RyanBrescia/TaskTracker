package src.com.company.ui;


import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import src.com.company.tasktracker.EventModel;
import src.com.company.tasktracker.SceneName;
import src.com.company.tasktracker.TaskTracker;

public class AddWindow implements Window,Observer{
	
	//TODO add column titles
	//hold reference to Stage, current GridPane and current Scene
	private Stage aStage;
	private EventModel aModel;
	private Scene current;
	private GridPane aRoot;
	
	private HBox bottomButtons;
	private ArrayList<RowEntry> rows = new ArrayList<RowEntry>();
	
	
	public Scene buildScene(EventModel pModel,Stage pStage) {
		
		//update local fields
		aStage = pStage;
		aModel = pModel;
		
		//add to model observers
		aModel.setEntryObserver(this);
		
		//Initialize Gridpane and begin to add Node objects
		GridPane root = new GridPane();
		
		//Generate initial EntryRow object
		RowEntry row = new RowEntry(aStage,aModel);
		rows.add(row);
		
		//add buttons
		BackButton back = new BackButton(pModel,pStage,SceneName.Main);
		Button addRow = new Button("Add Row");
		SubmitButton submit = new SubmitButton(pStage, pModel,this);
		
		HBox buttons = new HBox(back,addRow,submit);
		bottomButtons = buttons;
		
		//add row button event handler
		addRow.setOnAction(e -> {pStage.setScene(addRow());
				pStage.show();});
		
		//add components to root
		root.add(row, 0, 1);
		root.add(buttons, 0, 2);
		
		//update fields
		current = new Scene(root);
		aRoot = root;
		
		return current;
	}
	
	public ArrayList<Node> observableToArrayList(){
		
		ObservableList<Node> obs = aRoot.getChildrenUnmodifiable();
		
		return new ArrayList<Node>(obs);
	}
	
	public Scene addRow() {
		
		//iterate through current root and add all elements to a new root
		ObservableList<Node> children = aRoot.getChildren();
		GridPane newRoot = new GridPane();
		int i = 0;
		int size = children.size();
		
		//iterate through current UI items -> copy to new root -> add in new row of TextString
		while(!children.isEmpty()) {
			//add each Node to the new root
			if(i==size-1) {
				//add new row
				RowEntry newRow = new RowEntry(aStage,aModel);
				rows.add(newRow);
				newRoot.add(newRow, 0, i);
				i++;
			}
			newRoot.add(children.get(0), 0, i);
			i++;
		}
		
		aRoot = newRoot;
		
		return new Scene(newRoot);
	}

	public void update(ArrayList<String> messages) {
		
		ObservableList<Node> children = aRoot.getChildren();
		GridPane newRoot = new GridPane();
		int i = 0;
		int size = children.size();
		
		//add summaries
		for(String message:messages) {
			System.out.println("Added Summary");
			newRoot.add(new Label(message), 0, i);
			i++;
		}
		newRoot.add(bottomButtons, 0, i);
		
		aRoot = newRoot;
		aStage.setScene(new Scene(newRoot));
		aStage.show();
		
	}
	
	@Override
	public ArrayList<RowEntry> getRows() {
		//Method that returns all rows in a window
		return this.rows;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
