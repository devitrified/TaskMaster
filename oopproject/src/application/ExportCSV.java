package application;

import java.io.FileWriter;

import javafx.collections.ObservableList;

public class ExportCSV {
	public void Export(String nameCsv, ObservableList<Task> observableTasks) {
		try {
 			 
			 FileWriter writer = new FileWriter("C:\\Users\\damie\\Downloads\\" +  nameCsv+ ".csv");
			 
			 // Write the headers
		        writer.append("Task Name");
		        writer.append(",");
		        writer.append("Task Deadline");
		        writer.append(",");
		        writer.append("Task Importance");
		        writer.append(",");
		        writer.append("Task Priority");
		        writer.append(",");
		        writer.append("Task Description");
		        writer.append("\n");
			 for (Task task : observableTasks) {
			     writer.write(task.getName() + "," + task.getDeadline() + "," +task.getImportance() + "," + task.getPriority() +"," +  task.getDescription()  +"\n");
			 }

			 writer.close();
			 }catch (Exception e) {
				 System.out.println(e);
			 }
	}
}
