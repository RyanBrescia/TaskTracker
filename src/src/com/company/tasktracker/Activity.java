package src.com.company.tasktracker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class Activity implements Comparable<Activity>{
	
	//TODO add worth + extra data
	//fields -> activity data
	private String aName;
	private LocalDate dueDate;
	private Double aWorth;
	//time to complete???
	
	public Activity(String pName,LocalDate pDueDate,Double pWorth){
		aName = pName;
		dueDate = pDueDate;
		aWorth = pWorth;
		System.out.println("New activity with worth:"+aWorth);
	}
	
	@Override
	public String toString() {
		return aName + " " + aWorth + " " + dueDate.toString();
	}

	@Override
	public int compareTo(Activity act) {
		// TODO determine comparison
		//sort by due date -> then by amount worth -> then by amount of time -> then other parameters
		return act.computeScore().compareTo(this.computeScore());
	}
	
	public String getName() {
		return this.aName;
	}
	
	public void setName(String newName) {
		aName = newName;
	}
	
	public String getWorth() {
		return String.valueOf(this.aWorth);
	}
	
	public void setWorth(Double newWorth) {
		aWorth = newWorth;
	}
	
	public LocalDate getDate() {
		return dueDate;
	}
	
	public void setDate(LocalDate newDate) {
		dueDate = newDate;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null) {
			return false;
		}else if(o==this) {
			return true;
		}else if(o.getClass()!=this.getClass()) {
			return false;
		}else {
			//compare fields
			return this.aName.equals(((Activity)o).aName) && this.dueDate.equals(((Activity)o).dueDate) && this.aWorth==((Activity)o).aWorth;
		}		
	}
	
	public Double computeScore() {
		//Relative worth should count for more than date
		LocalDate current = LocalDate.now();
		//Integer dateDiff = this.dueDate.compareTo(current);
		long dateDiff = ChronoUnit.DAYS.between(current,this.dueDate);
		System.out.println("Calculating S = " + aWorth + " / " + dateDiff);
		double score;
		
		//avoid division by zero
		if(dateDiff == 0) {
			score = ((double) aWorth)/.01;
			return score;
		}else {
			score = ((double)aWorth)/((double) dateDiff); //check if scores are always integers
			//System.out.println("The calculated Activity Score for "+aName+" : "+ score);
			return score;
		}
	}
	
	public String toCSV() {
		return aName + "," + aWorth + "," + dueDate.toString();
	}

}
