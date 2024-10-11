package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

public class Task {
	private String id;
	  private String name;
	  private String deadline;
	  private String importance;
	  private String description;
	  private String priority;
	  private CheckBox checkbox;

	  public Task(String id, String name, String deadline,String priority, String importance, String description) {
	    this.id = id;
	    this.name = name;
	    this.deadline = deadline;
	    this.priority = priority;
	    this.importance = importance;
	    this.description = description;
	    this.checkbox = new CheckBox();
	    checkbox.setOnAction(event -> {
		    if (checkbox.isSelected()) {
		        try{
		        	Class.forName("com.mysql.cj.jdbc.Driver");
					  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
					  String Sql = "UPDATE tasks SET checkbox = 1 WHERE id = ?";
					  PreparedStatement Stmt = con.prepareStatement(Sql);
					  int idd = Integer.parseInt(id);
					  Stmt.setInt(1, idd);
					  Stmt.executeUpdate();
					  
		        } catch (Exception e) {
		            e.printStackTrace();
		    } 
		    }else {
		    	 try{
			        	Class.forName("com.mysql.cj.jdbc.Driver");
						  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
						  String Sql = "UPDATE tasks SET checkbox = 0 WHERE id = ?";
						  PreparedStatement Stmt = con.prepareStatement(Sql);
						  int idd = Integer.parseInt(id);
						  Stmt.setInt(1, idd);
						  Stmt.executeUpdate();
						  
			        } catch (Exception e) {
			            e.printStackTrace();
		    }
		    }
		    });
	  }
//	  public Task(String id, String name, String deadline,String priority, String importance, String description, boolean checked) {
//		    this.id = id;
//		    this.name = name;
//		    this.deadline = deadline;
//		    this.priority = priority;
//		    this.importance = importance;
//		    this.description = description;
//		    this.checkbox = new CheckBox();
//		    checkbox.setOnAction(event -> {
//		    if (checkbox.isSelected()) {
//		        try{
//		        	Class.forName("com.mysql.cj.jdbc.Driver");
//					  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
//					  String Sql = "UPDATE tasks SET checkbox = 1 WHERE id = ?";
//					  PreparedStatement Stmt = con.prepareStatement(Sql);
//					  int idd = Integer.parseInt(id);
//					  Stmt.setInt(1, idd);
//					  Stmt.executeUpdate();
//					  
//		        } catch (Exception e) {
//		            e.printStackTrace();
//		    } 
//		    }else {
//		    	 try{
//			        	Class.forName("com.mysql.cj.jdbc.Driver");
//						  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
//						  String Sql = "UPDATE tasks SET checkbox = 0 WHERE id = ?";
//						  PreparedStatement Stmt = con.prepareStatement(Sql);
//						  int idd = Integer.parseInt(id);
//						  Stmt.setInt(1, idd);
//						  Stmt.executeUpdate();
//						  
//			        } catch (Exception e) {
//			            e.printStackTrace();
//		    }
//		    }
//		    });
//	  }
	  
	  public void isChecked() {
		  checkbox.setSelected(true);
	  }
	  public void isnotChecked() {
		  checkbox.setSelected(false);
	  }

	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getDeadline() {
	    return deadline;
	  }

	  public void setDeadline(String deadline) {
	    this.deadline = deadline;
	  }

	  public String getImportance() {
	    return importance;
	  }

	  public void setImportance(String importance) {
	    this.importance = importance;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }
	  
	  public CheckBox getCheckbox() {
	        return checkbox;
	    }
	 
	    public void setCheckBox(CheckBox checkbox) {
	        this.checkbox = checkbox;
	    }
	    
	    public String getPriority() {
		    return priority;
		  }

		  public void setPriority(String priority) {
		    this.priority = priority;
		  }
		  
}
