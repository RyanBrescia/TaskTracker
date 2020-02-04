package src.com.company.tasktracker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import src.com.company.ui.DeleteObserver;
import src.com.company.ui.NewActivityObserver;
import src.com.company.ui.Observer;
import src.com.company.ui.RowEntry;

public class EventModel {
	
	//hold a list of observers -> that change with every item update
	
	//priority queue of food items
	private final static List<Activity> activities = new ArrayList<Activity>();
	
	private Observer entryObserver; //this is just for updating the AddWindow with summaries
	
	private final ArrayList<NewActivityObserver> newActivityObservers = new ArrayList<NewActivityObserver>();
	private final ArrayList<DeleteObserver> deleteObservers = new ArrayList<DeleteObserver>();
	//private final ArrayList<NewOrderObserver> newOrderObserver = new ArrayList<NewOrderObserver>();
	
	private static HashMap<String,Activity> hashedActivities = new HashMap<String,Activity>();
	
	//current time
	LocalDate current = LocalDate.now();
	
	//file location
	final String fileLocation = "data.txt";
	
	//final String fileLocated = "/data.txt";
	
	//constructor
	EventModel(){
		//read from file
		try {
			generateFromFile(fileLocation);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(activities);
		for(Activity act:activities) {
			System.out.println("The score for activity "+act.getName()+"is: " + act.computeScore());
		}
		System.out.println(activities.toString());
	}
	
	//utility functions to add various observers to the EventModel
	public void setEntryObserver(Observer pObs) {
		this.entryObserver = pObs;
	}
	
	public void addNewActivityObserver(NewActivityObserver pObs) {
		this.newActivityObservers.add(pObs);
	}
	
	public void addDeleteObserver(DeleteObserver pObs) {
		this.deleteObservers.add(pObs);
	}
	
	public void removeDeleteObserver(DeleteObserver pObs) {
		this.deleteObservers.remove(pObs);
	}
	
	public static Activity getActivity(int i) {
		return activities.get(i);
	}
	
	public static Activity getActivityFromString(String actID) {
		return hashedActivities.get(actID);
	}
	
	public void addItems() {
	    //ArrayLists to hold various data
		ArrayList<RowEntry> entries = this.entryObserver.getRows();
		ArrayList<String> messages = new ArrayList<String>();
		ArrayList<String> newActivities = new ArrayList<String>();
		 
		//loop through rows and convert to Activity instances
		for(RowEntry row:entries) {
			
			if(row.rowToActivity()!=null) {
				
				Activity newActivity = row.rowToActivity();
				
				//avoid duplicate Activity creation
				if(!activities.contains(newActivity)) {
					
					//update activity aggregates
					activities.add(newActivity);
					newActivities.add(newActivity.toString());
					hashedActivities.put(newActivity.toString(),newActivity);
					
					//write new activities to the local file
					try {
						writeToFile(newActivity);
						System.out.println("Successfully Added" + newActivity.toString());
						messages.add("Successfully Added: " + newActivity.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					//output unsuccessful addition
					System.out.println("Error already exists");
					messages.add("Unsuccessfully Added: " + newActivity.toString()+ ", already exists.");
				}
			}
		 }
		
		//sort activities according to Activity criteria
		System.out.println(activities);
		Collections.sort(activities);
		System.out.println("Sorted Activities:"+activities);
		
		//notify NewActivityObserver of new activities 
		for(NewActivityObserver obs:this.newActivityObservers) {
			obs.newActivities(newActivities);
		}
		
		//Notify EntryObserver of Success of addition
		entryObserver.update(messages);
	}
	
	//addItem utility function -> figure out how to combine with addItems() -> where addItems() is n*addActivity()
	public void addActivity(Activity newAct) {
		//add activity
		System.out.println("Adding activity");
		this.activities.add(newAct);
		this.hashedActivities.put(newAct.toString(),newAct);
		
		//notify observers
		ArrayList<String> tempActivities = new ArrayList<String>();
		tempActivities.add(newAct.toString());
		
		//notify NewActivityObserver of new activities
		for(NewActivityObserver obs:this.newActivityObservers) {
			obs.newActivities(tempActivities);
		}
		
	}
	
	public void deleteItem(String act) {
		//check if item already exists -> avoid duplicates
		if(!hashedActivities.containsKey(act)) {
			System.out.println("hash does not contain");
			return;
		}
		System.out.println("Removing activity from model");
		
		//remove activity from model and update local aggregates
		Activity newAct = hashedActivities.get(act);
		activities.remove(newAct);
		hashedActivities.remove(act);
		Collections.sort(activities);
		
		System.out.println("After removal:"+activities);
		
		//notify observers
		for(DeleteObserver dObs:this.deleteObservers) {
			dObs.deleteItem(act);
		}
		
		//delete from file
		rewriteFile();
	}
	
	public void rewriteFile(){
		//Use PrintWriter to delete existing contents of data file
		try {
			PrintWriter pw = new PrintWriter(fileLocation);
			pw.close();
		} catch (FileNotFoundException e) {
			//File not found
			e.printStackTrace();
		}
		//write each Activity line by line to the file
		if(!activities.isEmpty()) {
			for(Activity act:activities) {
				try {
					writeToFile(act);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void writeToFile(Activity pAct) throws IOException {
		//Writes a single Activity to the file
		FileWriter writer = new FileWriter(new File(fileLocation),true);
		BufferedWriter bw = new BufferedWriter(writer);
		PrintWriter pw = new PrintWriter(bw);
		pw.println(pAct.toCSV());
		pw.close();
	}
	
	public void generateFromFile(String filename) throws FileNotFoundException {
		//read through file and add all items to the priority queue
		
		//open file for readings
		
		//TODO: use InputStreams for files
		//InputStream is = EventModel.class.getResourceAsStream(fileLocated);
		//System.out.println(is==null);
		//Scanner scanner2 = new Scanner(is);
		//scanner2.close();
		
		Scanner scanner = new Scanner(new File(filename));
		
		//process data.txt line by line and format into Food objects
		while(scanner.hasNextLine()) {
			
			//Read file line
			String line = scanner.nextLine();
			System.out.println("Line = :"+line);
			
			//Split line by whitespace into three strings representing the three variables
			//TODO: change file format to .csv
			String[] result = line.split(",");
			
			//Create Activity object from line of data
			LocalDate date = LocalDate.parse(result[2]);
			Activity newAct = new Activity(result[0],date,Double.valueOf(result[1]));
			
			//update local aggregates of Activity objects
			activities.add(newAct);
			hashedActivities.put(newAct.toString(), newAct);
			
			System.out.println("name:"+result[0]);
			System.out.println("date:"+result[1]);
			System.out.println("Succesfully added: "+activities.get(activities.size()-1).toString());
		}
		scanner.close();
		
	}
	
	public ArrayList<String> getSummary(){
		ArrayList<String> names = new ArrayList<String>();
		for(Activity act:this.getActivities()) {
			names.add(act.toString());
		}
		return names;
	}
	
	//return copy of activities
	public ArrayList<Activity> getActivities(){
		return new ArrayList<Activity>(activities);
	}
	
	public boolean isEmpty() {
		return activities.isEmpty();
	}
	
	public static int getNumberActivities() {
		return activities.size();
	}

}
