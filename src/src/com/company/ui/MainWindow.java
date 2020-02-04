package src.com.company.ui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import src.com.company.tasktracker.EventModel;

public class MainWindow implements Window,NewActivityObserver,DeleteObserver{
	
	private EventModel aModel;
	private Stage aStage;
	
	private ObservableList<ActivityLabel> observableLabels;
	private int numberLabels = 0;
	
	private ArrayList<ActivityLabel> aLabels;

	public Scene buildScene(EventModel pModel, Stage pStage) {
		//build UI
		
		aModel = pModel;
		aStage = pStage;
		
		//add window to EventModel
		aModel.addNewActivityObserver(this);
		aModel.addDeleteObserver(this);
				
		//Title
		Label title = new Label("Task Tracker");
		title.setAlignment(Pos.CENTER);
		
		//ObservableList<ActivityLabel> labels;
		ArrayList<ActivityLabel> labels = new ArrayList<ActivityLabel>();
		
		//generate arraylist of 5 ActivityLabels	
		for(int i=0;i<5;i++) {
			labels.add(new ActivityLabel(aModel,i));
		}
		
		//Update local collection of labels
		aLabels = labels;
		this.numberLabels = aModel.getNumberActivities();
		System.out.println("New NumberLabels: "+this.numberLabels);
		
		// put a for loop -> with addition
		ObservableList<ActivityLabel> obsLabelList = FXCollections.observableArrayList(labels.subList(0, aModel.getNumberActivities()));
		observableLabels = obsLabelList;
		ListView<ActivityLabel> labelList = new ListView<ActivityLabel>(observableLabels);
				
		labelList.setPrefHeight(160);
		
		//Buttons
		HBox buttons = new HBox(new AddButton(aStage,aModel),new DeleteButton(aStage));
		
		//Create scene with the parts
		VBox root = new VBox(title,labelList,buttons);
		root.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(root);
		return scene;
	}

	@Override
	public void newActivities(ArrayList<String> newActivities) {
		//Callback method for NewActivityObserver
		for(String str:newActivities) {
			if(this.numberLabels<5) {
				//update labels
				observableLabels.add(aLabels.get(numberLabels));
				this.numberLabels++;
				System.out.println("New NumberLabels: "+this.numberLabels);
			}
		}
		
		//update order on existing labels
		for(int i=0;i<this.numberLabels;i++) {
			this.aLabels.get(i).updateOrder();
		}
		
	}

	@Override
	public void deleteItem(String deleted) {
		//Callback Method for DeleteObserver
		System.out.println("Removing label"+deleted);
		this.numberLabels--;
		System.out.println("New NumberLabels: "+this.numberLabels);
		observableLabels.clear();
		for(int i=0;i<this.numberLabels;i++) {
			System.out.println("Updating order of index: "+i);
			aLabels.get(i).updateOrder();
			observableLabels.add(aLabels.get(i));
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
