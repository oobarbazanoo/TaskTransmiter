import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.geometry.*;

public class Starter extends Application 
{
	private Stage window;
	private ArrayList<Task> tasks;
	
	public static void main(String[] args)
	{launch(args);}
	
	@Override
	public void start(Stage window) throws Exception 
	{
		this.tasks = readTasks();
		
		if(tasks == null)
		{tasks = new ArrayList<Task>();}
		
		this.window = window;
		
		window.setOnCloseRequest(e -> 
		{
			e.consume();
			closeProgram();
		});
		
		Button bSolveExistingTasks = new Button();
		bSolveExistingTasks.setText("Solve existing tasks");
		bSolveExistingTasks.setOnAction(e -> 
		{
			if(tasks.size() == 0)
			{NotificationWind.display("There are no tasks added yet.", "#ffff00");}
			else
			{
					TaskWind taskWind = new TaskWind(tasks);
					int points = taskWind.display(window);
			}
		});
		GridPane.setConstraints(bSolveExistingTasks, 0, 0);
		GridPane.setFillWidth(bSolveExistingTasks, true);
		bSolveExistingTasks.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		Button bAddTask = new Button();
		bAddTask.setText("Add a new task");
		bAddTask.setOnAction(e -> 
		{
			AddTaskWind atw = new AddTaskWind(this.tasks);
			boolean added = atw.display(window);
		});
		GridPane.setConstraints(bAddTask, 0, 1);
		GridPane.setFillWidth(bAddTask, true);
		bAddTask.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		Button bChangeExistingTasks = new Button();
		bChangeExistingTasks.setText("Change existing");
		bChangeExistingTasks.setOnAction(e -> 
		{
			if(tasks.size() == 0)
			{NotificationWind.display("There are no tasks added yet.", "#ffff00");}
			else
			{
				ChooseTaskToEditWind cttew = new ChooseTaskToEditWind(tasks);
				cttew.display(window);
			}
		});
		GridPane.setConstraints(bChangeExistingTasks, 0, 2);
		GridPane.setFillWidth(bChangeExistingTasks, true);
		bChangeExistingTasks.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		Button bExit = new Button();
		bExit.setText("Close");
		bExit.setOnAction(e -> closeProgram());
		GridPane.setConstraints(bExit, 0, 3);
		GridPane.setFillWidth(bExit, true);
		bExit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(5, 5, 5, 5));
		layout.setVgap(5);

		layout.getChildren().addAll(bSolveExistingTasks, bAddTask, bChangeExistingTasks, bExit);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 300, 150);
		scene.getStylesheets().add("style.css");
		
		window.setScene(scene);
		window.setTitle("Algebra tasks");
		window.setResizable(false);
		window.show();
	}
	
	private ArrayList<Task> readTasks()
	{
		try
		{
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + Constants.SYSTEM_SEPARATOR + 
								Constants.NAME_OF_DATA_DIR + Constants.SYSTEM_SEPARATOR + Constants.NAME_OF_TASKS_LIST);
		    ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			ois.close();
			fis.close();
			return (ArrayList<Task>)obj;
		}
		catch (Exception e)
		{return null;}
		
	}

	private void closeProgram()
	{
		ConfirmationBox cb = new ConfirmationBox("Exit", "Are you sure to exit?");
		if(cb.display())
		{this.window.close();}
	}
}
