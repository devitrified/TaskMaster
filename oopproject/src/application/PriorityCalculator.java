package application;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PriorityCalculator {
	
	public int priorityLvl(String selectedOption, LocalDate deadline) {
	 	String imptLvl = selectedOption;
	 	LocalDate dueDate = deadline;
	 	LocalDate currentDate = LocalDate.now();
	 	
	 	int priorityLevel;
	 	int importance;
	 	
	 	
	 	if (imptLvl.equals("Not Important")) {
	 		importance = 1;
	 	}else if(imptLvl.equals("Important")) {
	 		importance = 2;
	 	}else{
	 		importance = 3;
	 	}
	 	long daysUntilDue = ChronoUnit.DAYS.between(currentDate, dueDate);
//	 	Period period = Period.between(currentDate, dueDate);
//	 	int daysUntilDue = period.getDays();

	 	if	(daysUntilDue < 0) {
	 		priorityLevel = importance + 100;
	 	}else if (daysUntilDue <= 3) {
	         priorityLevel = importance + 5;
	 	}else if (daysUntilDue <= 7) {
	         priorityLevel = importance + 2;
	     } else if (daysUntilDue <= 14) {
	         priorityLevel = importance + 1;
	     } else {
	         priorityLevel = importance;
	     }
	 	System.out.println(dueDate.toString() + "," + currentDate.toString() + ", " + daysUntilDue);
	     return priorityLevel;
	     
	     

	 	
	 }
}
