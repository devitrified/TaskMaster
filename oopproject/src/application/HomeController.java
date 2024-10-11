package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController implements Initializable, Tasks {
	
	private Stage stage;

	@FXML
    private PieChart pieChart;
	
    @FXML
    private Button addBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button taskBtn;

    @FXML
    private BarChart<String, Number> barChart;
    
    int impt = 0 , notimpt = 0 , veryimpt = 0;
    
    @FXML
    private Label taskDate;

    @FXML
    private Label taskDesc;

    @FXML
    private Label taskImpt;

    @FXML
    private Label taskName;

    @FXML
    private Label tdayDate;

    @FXML
    private Label tdayDay;
    
    @FXML
    private Label taskNum;

    @FXML
    private Label taskOverdue;

    int p=0, i =0, x = 0;
    
    private static boolean alertShown = false;

    
    Map<String, Integer> counts = new HashMap<>();
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
		PriorityCalculator pc = new PriorityCalculator();
		try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			  String selectSql = "SELECT * FROM tasks";
			  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			  ResultSet result = selectStmt.executeQuery();
	
			  
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
				    
				    int i1 = 1,i2 = 2,i3 = 3;
				    int iA = 0;
			    
			    
			    if (importance.equals("Not Important")){
			    	iA = i1;
			    	notimpt++;
			    }else if(importance.equals("Important")){
			    	iA = i2;
			    	impt++;
			    }else {
			    	iA = i3;
			    	veryimpt++;
			    }
			    int prior = pc.priorityLvl(importance, localDate);
			    
			    if (prior > 100) {	

				    	String sql = "UPDATE tasks SET priority = "+ prior + " WHERE id =  "+ id;
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.executeUpdate();						
			    }

			    
			    if(prior > p) {
			    	if(x < iA) {
			    	p = prior;
			    	i = tid;
			    	x = iA;
			    	}
			    }
			    
			   
			  }
			  
			  
			  
				conn.close();
				ObservableList<PieChart.Data>pieChartData = FXCollections.observableArrayList(
						new PieChart.Data("Not Important", notimpt),
						new PieChart.Data("Important", impt),
						new PieChart.Data("Very Important", veryimpt));
				  pieChart.setData(pieChartData);
				  pieChart.setTitle("Distribution of Tasks by Importance");
				  
				  Date date = new Date();
			      DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			      String dateString = dateFormat.format(date);
				  tdayDate.setText(dateString);
				  
				  LocalDate dt = LocalDate.now();
			      String dayOfWeek = dt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			      tdayDay.setText(dayOfWeek);
				  
		
				 
				 
			  
				
		}catch (Exception e) {
				  System.out.println(e);
			  }
		

		
		try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			  String selectSql = "SELECT * FROM tasks WHERE id = ?";
			  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			  selectStmt.setInt(1, i);
			  ResultSet result = selectStmt.executeQuery();
			  

			  while (result.next()) {

				    String name = result.getString("name");

				    Date deadline = result.getDate("deadline");
				    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				    String date = formatter.format(deadline);
				    String importance = result.getString("importance");
				    String description = result.getString("description");
				    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate localDate = LocalDate.parse(date, formatter1);
				    int prior = pc.priorityLvl(importance, localDate);
				    System.out.println(prior);
				    if(prior>100) {
				    	taskDate.setText(date + " (Overdue)");
				    }else {
				    	  taskDate.setText(date);
				    }
				    
				    
				    taskName.setText(name);
				  
				    taskImpt.setText(importance);
				    taskDesc.setText(description);

			  }
			  
		
				 
			  
				
		}catch (Exception e) {
				  System.out.println(e);
			  }
		
		try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			  String selectSql = "SELECT COUNT(*) as count from tasks";
			  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			  ResultSet result = selectStmt.executeQuery();
			  int count = 0;
			  
			  while (result.next()) {

				   count = result.getInt("count");
				    
			  }
			  String counts = Integer.toString(count);
			  taskNum.setText(counts);


		
				 
			  
				
		}catch (Exception e) {
				  System.out.println(e);
			  }
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		  String selectSql = "SELECT COUNT(*) as count from tasks WHERE priority > 100 ";
		  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
		  ResultSet result = selectStmt.executeQuery();
		  int count = 0;
		  while (result.next()) {

			   count = result.getInt("count");
			    
		  }
		  String counts = Integer.toString(count);
		  taskOverdue.setText(counts);
			conn.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			  String selectSql = "SELECT deadline, COUNT(deadline) AS count FROM tasks GROUP BY deadline ORDER BY deadline";
			  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			  ResultSet result = selectStmt.executeQuery();
			  XYChart.Series<String, Number> series = new XYChart.Series<>();
		      series.setName("No. Of Tasks");
			  
			  while (result.next()) {

				  Date value = result.getDate("deadline");
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				  String date = formatter.format(value);
				  int num = result.getInt("count");
				  series.getData().add(new XYChart.Data<>(date, num));

				    
			     }

		      barChart.setTitle("Number of Due Tasks on Each Date");
		      barChart.getData().add(series);
		      
			}catch(Exception e)
			{
				System.out.println(e);
			}
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(!alertShown) {
					 try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
					  String selectSql = "SELECT count(priority) as count FROM tasks WHERE priority < 100 AND priority > 5";
					  PreparedStatement selectStmt = conn.prepareStatement(selectSql);
					  ResultSet result = selectStmt.executeQuery();
					  
					  
					  while (result.next()) {
						  	int num = result.getInt("count");
						    if (num > 0) {
						    	 Alert alert = new Alert(AlertType.INFORMATION);
							       alert.setTitle("Notification");
							       alert.setHeaderText(num + " tasks are close to the deadline");
							       alert.showAndWait();
							       alertShown = true;
						    }
					     }
					  

				      
						conn.close();
					}catch(Exception e)
					{
						System.out.println(e);
					}
			}
			}
			});
		
		
	}
	
	
//	public int priorityLvl(String selectedOption, LocalDate deadline) {
//	 	String imptLvl = selectedOption;
//	 	LocalDate dueDate = deadline;
//	 	LocalDate currentDate = LocalDate.now();
//	 	int priorityLevel;
//	 	int importance;
//	 	
//	 	if (imptLvl.equals("Not Important")) {
//	 		importance = 1;
//	 	}else if(imptLvl.equals("Important")) {
//	 		importance = 2;
//	 	}else{
//	 		importance = 3;
//	 	}
//	 	int daysUntilDue = dueDate.compareTo(currentDate);
//	 	if	(daysUntilDue < 1) {
//	 		priorityLevel = importance + 100;
//	 	}else if (daysUntilDue <= 3) {
//	         priorityLevel = importance + 5;
//	 	}else if (daysUntilDue <= 7) {
//	         priorityLevel = importance + 2;
//	     } else if (daysUntilDue <= 14) {
//	         priorityLevel = importance + 1;
//	     } else {
//	         priorityLevel = importance;
//	     }
//	     return priorityLevel;
//
//	 	
//	 }
}

