package src.com.company.ui;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import src.com.company.tasktracker.Activity;
import src.com.company.tasktracker.EventModel;

public class RowEntry extends Parent {
	
	//comprised of fields for data entry -> TextField name, Int worth, int estimated time? LocalDate due date
	
	TextField text = new TextField();
	TextField worth = new TextField();
	DatePicker date = new DatePicker();
	
	Stage aStage;
	EventModel aModel;
	
	ArrayList<String> allowed = new ArrayList<>();
	
	RowEntry(Stage pStage,EventModel pModel){
		aStage = pStage;
		aModel = pModel;
		
		populateAllowed();
		
		//limit what can go in the worth TextField() -> i.e. 0-100 (no letters,characters,etc.)
		//StringConverter<Integer> value = new IntRangeStringConverter<Integer>(0,100);
		TextFormatter<String> formatter = new TextFormatter<String>(change -> {
			
			//if text is 2 long and leading number is not 1 do not add
			if(!text.getText().contains(".")) {
				allowed.add(".");
			}
	
			//check if allowed value and also 100 or less 
			//i.e. get length of current Text -> if 2 and leading number != 1 no more additions
			System.out.println(change.getText());
			if(!allowed.contains(change.getText())) {
				change.setText("");
				return change;
			}
			
			if(change.getText().equals(".")) {
				System.out.println("removing period");
				allowed.remove(".");
			}
			return change;
		});
		
		worth.setTextFormatter(formatter);
		
		date.setEditable(false);
		
		HBox hbox = new HBox(text,worth,date);
		
		getChildren().add(hbox);
		
	}
	
	public void populateAllowed() {
		String[] allowedArray = {"0","1","2","3","4","5","6","7","8","9","."};
		for(int i=0;i<allowedArray.length;i++) { allowed.add(allowedArray[i]);}
	}
	
	public Activity rowToActivity() {
		if(text.getText().isEmpty()) {
			return null;
		}else {
			//TODO convert String to Integer
			return new Activity(text.getText(),date.getValue(),Double.valueOf(worth.getText()));
		}
	}

}
