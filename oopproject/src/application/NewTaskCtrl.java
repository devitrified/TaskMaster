package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewTaskCtrl extends TaskForm implements Tasks{

	private Stage stage;


	   @FXML
	    private Button addBtn;

	    @FXML
	    private DatePicker datePicker;

	    @FXML
	    private Button homeBtn;

	    @FXML
	    private ToggleGroup importanceLevel;

	    @FXML
	    private Button taskBtn;

	    @FXML
	    private TextArea taskDesc;

	    @FXML
	    private TextField taskName;

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
	    

 
 @FXML
 public void onDoneClicked(ActionEvent event) {
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		String insertSql = "INSERT INTO tasks (name, deadline, importance, priority,description,checkbox) VALUES (?,?,?, ?, ?,?)";
		PreparedStatement insertStmt = conn.prepareStatement(insertSql);
			   PriorityCalculator pc = new PriorityCalculator();
			   LocalDate now = LocalDate.now();
		       String tskName = taskName.getText();
		    
		       LocalDate deadline = datePicker.getValue();
		       
		       RadioButton selectedButton = (RadioButton) importanceLevel.getSelectedToggle();
	
		       String tskDesc = taskDesc.getText();
		       
		       if (tskName.trim().isEmpty() || deadline == null || selectedButton == null || tskDesc.trim().isEmpty() ) {
		    	   Alert nullValue = new Alert(AlertType.ERROR);
		    	   nullValue.setTitle("Notification");
			       nullValue.setHeaderText("One or More Blank Fields, Please Fill in all Fields");
			       nullValue.showAndWait();
		       }else if(deadline.isBefore(now)) {
		    	   Alert invalidDate = new Alert(AlertType.ERROR);
		    	   invalidDate.setTitle("Notification");
		    	   invalidDate.setHeaderText("Invalid Date Input");
		    	   invalidDate.showAndWait();
		       }else {
		       
		    	   Instant instant = Instant.from(deadline.atStartOfDay(ZoneId.systemDefault()));
			       Date date = Date.from(instant);
			       java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			       String selectedOption = selectedButton.getText();
		       // Set the parameters in the INSERT statement
		       insertStmt.setString(1, tskName);
		       insertStmt.setDate(2, sqlDate);
		       insertStmt.setString(3, selectedOption);
		       insertStmt.setInt(4, pc.priorityLvl(selectedOption, deadline));
		       insertStmt.setString(5, tskDesc);
		       insertStmt.setBoolean(6, false);
		       

		       // Execute the INSERT statement
		       insertStmt.executeUpdate();
		       Alert alert = new Alert(AlertType.INFORMATION);
		       alert.setTitle("Notification");
		       alert.setHeaderText("Task Successfully Added");
		       alert.showAndWait();
		       }
		} catch (Exception e) {
			e.printStackTrace();
		}
 }

// 	public int priorityLvl(String selectedOption, LocalDate deadline) {
// 	String imptLvl = selectedOption;
// 	LocalDate dueDate = deadline;
// 	LocalDate currentDate = LocalDate.now();
// 	int priorityLevel;
// 	int importance;
// 	
// 	if (imptLvl.equals("Not Important")) {
// 		importance = 1;
// 	}else if(imptLvl.equals("Important")) {
// 		importance = 2;
// 	}else{
// 		importance = 3;
// 	}
// 	int daysUntilDue = dueDate.compareTo(currentDate);
// 	if	(daysUntilDue < 1) {
// 		priorityLevel = importance + 100;
// 	}else if (daysUntilDue <= 3) {
//         priorityLevel = importance + 5;
// 	}else if (daysUntilDue <= 7) {
//         priorityLevel = importance + 2;
//     } else if (daysUntilDue <= 14) {
//         priorityLevel = importance + 1;
//     } else {
//         priorityLevel = importance;
//     }
//     return priorityLevel;
//
// 	
// }


      
}

