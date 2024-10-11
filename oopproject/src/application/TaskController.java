package application;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TaskController implements Initializable, Tasks {
	
	private Stage stage;

	@FXML
    private Button addBtn;
	
	@FXML
    private Button exportBtn;
	
	@FXML
    private Button delRows;

    @FXML
    private Button homeBtn;

    @FXML
    private Button taskBtn;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private TextField csvName;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Task, String> taskDate;
    
    @FXML
    private TableColumn<Task, String> taskId;

    @FXML
    private TableColumn<Task, String> taskDesc;

    @FXML
    private TableColumn<Task, String> taskImpt;
    
    @FXML
    private TableColumn<Task, String> taskPrior;

    @FXML
    private TableView<Task> taskList;

    @FXML
    private TableColumn<Task, String> taskName;
    
    @FXML
    private TableColumn<Task, String> taskCheck;
    
    ObservableList<Task> observableTasks;

SceneController sc = new SceneController();
    
    @FXML
    public void onAddBtnClicked(ActionEvent event) {
    	sc.switchToAdd(event);
    }
    
    @FXML
    public void onHomeBtnClicked(ActionEvent event) {
    	sc.switchToHome(event);
    }
    
    @FXML
    public void onTaskBtnClicked(ActionEvent event) {
    	sc.switchToTasks(event);
    }
    
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			  String selectSql = "SELECT * FROM tasks";
			  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			  ResultSet result = selectStmt.executeQuery();
			  List<Task> tasks = new ArrayList<>();
			  PriorityCalculator pc = new PriorityCalculator();
			  
			  
			  while (result.next()) {
				int tid = result.getInt("id");
				String id = Integer.toString(tid);
			    String name = result.getString("name");
			    Date deadline = result.getDate("deadline");
			    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			    String date = formatter.format(deadline);
			    String importance = result.getString("importance");
			    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate localDate = LocalDate.parse(date, formatter1);
				boolean checkbox = result.getBoolean("checkbox");
				 
			   
			    int prior = pc.priorityLvl(importance, localDate);
			    String priority = Integer.toString(prior);
			    if(prior>100) {
			    	priority = "Overdue";
			    	String sql = "UPDATE tasks SET priority = "+ prior + " WHERE id =  "+ id;
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					
			    }else {
			    	String sql = "UPDATE tasks SET priority = "+ prior + " WHERE id =  "+ id;
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
			    }
			    
			    String description = result.getString("description");
			   
			    
			    


			    Task task = new Task(id, name, date, priority,importance, description);
			    if (checkbox == true){
					 task.isChecked();
			    }else {
			    	task.isnotChecked();
			    }
			    tasks.add(task);
			    
			    

			  } 
			  observableTasks = FXCollections.observableArrayList(tasks);

			  
			  	taskId.setCellValueFactory(new PropertyValueFactory<Task, String>("id"));
				taskName.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
				taskDate.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));
				taskImpt.setCellValueFactory(new PropertyValueFactory<Task, String>("importance"));
				taskDesc.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
				taskPrior.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
				taskCheck.setCellValueFactory(new PropertyValueFactory<Task, String>("checkbox"));

				taskList.setItems(observableTasks);
				
				

        FilteredList<Task> filteredData = new FilteredList<>(observableTasks, b -> true);
        
		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(task -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (task.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; 
				} else if (task.getImportance().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (task.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				     return true;
				}
				else if(task.getPriority().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			    	 return true;
				}else if(task.getDeadline().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			    	 return true;
				}else
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Task> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(taskList.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		taskList.setItems(sortedData);
		
		}catch (Exception e) {
			  System.out.println(e);
		  }
		
		
               
        
    }    
	
	
	@FXML
    void onDoubleClicked(MouseEvent event) {
		SceneController sc = new SceneController();
		if (event.getClickCount() == 2) {
			  Task selectedTask = (Task) taskList.getSelectionModel().getSelectedItem();
		        if (selectedTask != null) {
		        	sc.switchToEditTasks(event, selectedTask);
		        }
		  }

    }
	
	 @FXML
	 void onExportClicked(ActionEvent event) throws IOException {
		 ExportCSV export = new ExportCSV();
		 String nameCsv = csvName.getText();
		 if (nameCsv.equals("")){
			 Alert alert = new Alert(AlertType.ERROR);
		       alert.setTitle("Notification");
		       alert.setHeaderText("Please enter file name for export");
		       alert.showAndWait();
			 }
		 else {
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	       alert.setTitle("Notification");
	       alert.setHeaderText("Are you sure you want to export the tasks as '" + nameCsv + ".csv'?");
	       alert.setContentText("'"+ nameCsv+ ".csv' can be accessed via the Downloads folder\n If you have entered the name of an existing csv file, the contents of the\nfile will be overidden ");
	       Optional<ButtonType> result = alert.showAndWait();
	       if (result.get() == ButtonType.OK){
	    	   export.Export(nameCsv, observableTasks);
		 }
	 }
	 }
	
	
	 @FXML
	    void deleteRows(ActionEvent event) {
		 Task item = taskList.getSelectionModel().getSelectedItem();
		 try {
   		  Class.forName("com.mysql.cj.jdbc.Driver");
 			  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
 			  String delSql = "DELETE FROM tasks WHERE ID = ?";
 			  PreparedStatement delStmt = conn.prepareStatement(delSql);
	    	ObservableList<Task> taskRemove = FXCollections.observableArrayList();
	  	 
			  String id = item.getId();
			  delStmt.setInt(1, Integer.parseInt(id));
			  delStmt.executeUpdate();
	    		
	    	observableTasks.removeAll(taskRemove);
	    	conn.close();
		 }
		 catch (Exception e)
		 {
			System.out.println(e);
		 }
		 SceneController sc = new SceneController();
		 sc.switchToTasks(event);
		 
		 Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Notification");
	     alert.setHeaderText("Task '" + item.getName() + "' Successfully Deleted");
	     alert.showAndWait();
    
    
    
}
	 
//	 public int priorityLvl(String selectedOption, LocalDate deadline) {
//		 	String imptLvl = selectedOption;
//		 	LocalDate dueDate = deadline;
//		 	LocalDate currentDate = LocalDate.now();
//		 	int priorityLevel;
//		 	int importance;
//		 	
//		 	if (imptLvl.equals("Not Important")) {
//		 		importance = 1;
//		 	}else if(imptLvl.equals("Important")) {
//		 		importance = 2;
//		 	}else{
//		 		importance = 3;
//		 	}
//		 	int daysUntilDue = dueDate.compareTo(currentDate);
//		 	if	(daysUntilDue < 1) {
//		 		priorityLevel = importance + 100;
//		 	}else if (daysUntilDue <= 3) {
//		         priorityLevel = importance + 5;
//		 	}else if (daysUntilDue <= 7) {
//		         priorityLevel = importance + 2;
//		     } else if (daysUntilDue <= 14) {
//		         priorityLevel = importance + 1;
//		     } else {
//		         priorityLevel = importance;
//		     }
//		     return priorityLevel;
//
//		 	
//		 }
}

