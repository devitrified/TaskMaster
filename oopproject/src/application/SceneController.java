package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class SceneController {
	private Stage stage;
	public void switchToAdd(ActionEvent event) {
		try {
    		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("New.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene2 = new Scene(root);
    		stage.setScene(scene2);
    		stage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void switchToHome(ActionEvent event) {
		try {
    		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Home.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene2 = new Scene(root);
    		stage.setScene(scene2);
    		stage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void switchToTasks(ActionEvent event) {
		try {
    		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Tasks.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene2 = new Scene(root);
    		stage.setScene(scene2);
    		stage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public void switchToEditTasks(MouseEvent event, Task selectedTask) {
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTasks.fxml"));
    	 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
        	stage.setScene(new Scene(loader.load()));
            EditTaskCtrl controller = loader.getController();
            controller.setTask(selectedTask);
            stage.show();
			 
			 }catch(Exception e) {
        	System.out.println(e);
        }
	}
}
