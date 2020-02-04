package src.com.company.ui;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import src.com.company.tasktracker.Activity;
import src.com.company.tasktracker.EventModel;

public class EditTile extends Parent {
	
	//Class to edit an Activity object -> wraps an activity object
	String old;
	Activity editable;
	VBox elements;
	TextField nameField;
	TextField worthField;
	DatePicker datePicker;
	
	EventModel aModel;
	
	EditTile(EventModel pModel){
		//generate fields to edit
		aModel = pModel;
		Label newNameLabel = new Label("Name:");
		TextField newName = new TextField(); //changes
		nameField = newName;
		Label newWorthLabel = new Label("Worth");
		TextField newWorth = new TextField(); //changes
		worthField = newWorth;
		Label newDateLabel = new Label("Date");
		DatePicker newDate = new DatePicker(); //changes
		datePicker = newDate;
		datePicker.setEditable(false);
		VBox vertical = new VBox(newNameLabel,newName,newWorthLabel,newWorth,newDateLabel,newDate);
		elements = vertical;
		getChildren().add(elements);
	}
	
	public void newActivity(Activity newActivity) {
		System.out.println("Updating EditTile with: "+newActivity.toString());
		editable = newActivity;
		old = newActivity.toString();
		//update fields
		if(editable!=null) {
			reset();
		}
	}
	
	public void reset() {
		nameField.setText(editable.getName());
		worthField.setText(editable.getWorth());
		datePicker.setValue(editable.getDate());
	}
	
	public void updateActivity() {
		editable.setName(nameField.getText());
		editable.setWorth(Double.valueOf(worthField.getText()));
		editable.setDate(datePicker.getValue());		
	}
	
	public void updateActivities() {
		System.out.println("New: "+editable.toString());
		aModel.addActivity(editable);
		System.out.println("Old: "+old);
		aModel.deleteItem(old.toString());
		//editable = null;
		nameField.setText(null);
		worthField.setText(null);
		datePicker.setValue(null);
	}
	
	public void refresh() {
		nameField.setText(null);
		worthField.setText(null);
		datePicker.setValue(null);
	}
	
}
