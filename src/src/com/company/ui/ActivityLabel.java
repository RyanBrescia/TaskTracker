package src.com.company.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import src.com.company.tasktracker.EventModel;

public class ActivityLabel extends Parent{
	
	//model -> 
	//label -> with Food.toString(); 
	private EventModel aModel;
	//private CheckBox check = new CheckBox("delete");
	private Label info = new Label();
	private int index;
	private Button button = new Button("Completed");
	
	private String summary;
	private String previous;
	
	public ActivityLabel(EventModel pModel, int pIndex) {
		aModel = pModel;
		index = pIndex;
		
		//TODO: put in logic to decide whether or not there is an Activity assigned
		if(aModel.getNumberActivities()-1>=index) {
			summary = aModel.getActivity(index).toString();
			previous = aModel.getActivity(index).toString();
		}
				
		info.setText(summary);
		HBox hbox = new HBox(info,button);
		
		button.setOnAction(event-> aModel.deleteItem(summary));
		button.setAlignment(Pos.CENTER_RIGHT);
		
		getChildren().add(hbox);
	}
	
	public String toString() {
		return summary;
	}

	
	public void updateOrder() {
		// TODO Auto-generated method stub
		System.out.println("Updating Order From: " + previous + " to "
				+ aModel.getActivity(index).toString());
		summary = aModel.getActivity(index).toString();
		info.setText(summary);
		previous = summary.toString();
	}
	
	public String getPrev() {
		return this.previous;
	}
}
