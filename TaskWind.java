import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


import javafx.geometry.*;


public class TaskWind 
{
	private boolean solved;
	private ArrayList<Task> tasks;
	private int gotPoints, c;
	private Task[] taskArray;
	
	/*
	 * Create task window.
	 * @param task to show
	 */
	public TaskWind(ArrayList<Task> tasks)
	{this.tasks = tasks;}
	
	/*
	 * Display task window.
	 * @return pointForSolving if answer guessed right 0 otherwise
	*/
	public int display(Stage parentStage)
	{
		parentStage.hide();
		
		this.taskArray = tasks.toArray(new Task[tasks.size()]);
		
		if(taskArray.length > 0)
		{display(taskArray[c]);}
		else
		{return 0;}
		
		parentStage.show();
		
		return this.gotPoints;
	}

	private void display(Task task)
	{
		Stage window = new Stage();
		window.setOnCloseRequest(e ->
		{
			e.consume();
			closeWindow(window);
		});
		
		Node taskShow = null;
		
		if(task.getTaskText().length() > 1)
		{
			taskShow = new Label(task.getTaskText());
			((Label)taskShow).setWrapText(true);
			((Label)taskShow).setMaxWidth(600);
			((Labeled) taskShow).setTextFill(Color.WHITE);
		}
		else
		{
			InputStream is = null;
			try 
			{is = (task.getTaskImage()==null)?null:new FileInputStream(task.getTaskImage());}
			catch (FileNotFoundException e)
			{e.printStackTrace();}
			
			Image image = (is == null)?null:new Image(is);
			
			taskShow = new ImageView();
			((ImageView) taskShow).setImage(image);
		}
		
		GridPane.setConstraints(taskShow, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER);
		
		Label labAnswer = new Label("Answer: ");
		labAnswer.setTextFill(Color.WHITE);
		GridPane.setConstraints(labAnswer, 0, 1, 1, 1, HPos.RIGHT, null);
		
		TextField fieldAnswer = new TextField();
		GridPane.setConstraints(fieldAnswer, 1, 1);
		
		Button butSubmitAnswer = new Button("Submit");
		GridPane.setConstraints(butSubmitAnswer, 2, 1, 1, 1, HPos.CENTER, null);
		butSubmitAnswer.setOnAction(e ->
		{
			for(String s : task.getAnswers())
			{
				if(s.equals(fieldAnswer.getText()))
				{
					gotPoints += task.getPointsForSolving();
					NotificationWind.display("Right answer! You have got " + task.getPointsForSolving() + " points.", "#00ff00");
					return;
				}
			}
			
			NotificationWind.display("Wrong answer!", "#ff0000");
		});
		
		Button butPrevTask = new Button("Previous");
		GridPane.setConstraints(butPrevTask, 0, 2, 1, 1, HPos.LEFT, null);
	//	GridPane.setFillWidth(butPrevTask, true);
	//	butPrevTask.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		butPrevTask.setOnAction(e ->
		{
			if(this.c != 0)
			{
				changeContentOfWind(taskArray[--c], window);
			}
			else
			{
				NotificationWind.display("This is the first task!", "#ffff00");
			}
		});
		
		int count = c + 1;
		Label taskCounterLab = new Label(count + "\\" + taskArray.length);
		GridPane.setConstraints(taskCounterLab, 1, 2, 1, 1, HPos.CENTER, null);
		
		Button butNextTask = new Button("Next");
		GridPane.setConstraints(butNextTask, 2, 2, 1, 1, HPos.RIGHT, null);
	//	GridPane.setFillWidth(butNextTask, true);
	//	butNextTask.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		butNextTask.setOnAction(e ->
		{
			if(this.c != tasks.toArray().length - 1)
			{
				changeContentOfWind(taskArray[++c], window);
			}
			else
			{
				NotificationWind.display("This is the last task!", "#ffff00");
			}
		});
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(5, 5, 5, 5));
		layout.setVgap(5);
		
		layout.getChildren().addAll(taskShow, labAnswer, fieldAnswer, butSubmitAnswer, butPrevTask, taskCounterLab, butNextTask);

		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		
		window.setResizable(false);
		window.setTitle(task.getTaskName());
		window.setScene(scene);
		window.showAndWait();
	}
	
	private void changeContentOfWind(Task task, Stage window)
	{
		Node taskShow = null;
		
		if(task.getTaskText().length() > 1)
		{
			taskShow = new Label(task.getTaskText());
			((Label)taskShow).setWrapText(true);
			((Label)taskShow).setMaxWidth(600);
			((Labeled) taskShow).setTextFill(Color.WHITE);
		}
		else
		{
			InputStream is = null;
			try 
			{is = (task.getTaskImage()==null)?null:new FileInputStream(task.getTaskImage());}
			catch (FileNotFoundException e)
			{e.printStackTrace();}
			
			Image image = (is == null)?null:new Image(is);
			
			taskShow = new ImageView();
			((ImageView) taskShow).setImage(image);
		}
		
		GridPane.setConstraints(taskShow, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER);
		
		Label labAnswer = new Label("Answer: ");
		labAnswer.setTextFill(Color.WHITE);
		GridPane.setConstraints(labAnswer, 0, 1, 1, 1, HPos.RIGHT, null);
		
		TextField fieldAnswer = new TextField();
		GridPane.setConstraints(fieldAnswer, 1, 1);
		
		Button butSubmitAnswer = new Button("Submit");
		GridPane.setConstraints(butSubmitAnswer, 2, 1, 1, 1, HPos.CENTER, null);
		butSubmitAnswer.setOnAction(e ->
		{
			for(String s : task.getAnswers())
			{
				if(s.equals(fieldAnswer.getText()))
				{
					gotPoints += task.getPointsForSolving();
					NotificationWind.display("Right answer! You have got " + task.getPointsForSolving() + " points.", "#00ff00");
					return;
				}
			}
			
			NotificationWind.display("Wrong answer!", "#ff0000");
		});
		
		Button butPrevTask = new Button("Previous");
		GridPane.setConstraints(butPrevTask, 0, 2, 1, 1, HPos.LEFT, null);
	//	GridPane.setFillWidth(butPrevTask, true);
	//	butPrevTask.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		butPrevTask.setOnAction(e ->
		{
			if(this.c != 0)
			{
				changeContentOfWind(taskArray[--c], window);
			}
			else
			{
				NotificationWind.display("This is the first task!", "#ffff00");
			}
		});
		
		int count = c + 1;
		Label taskCounterLab = new Label(count + "\\" + taskArray.length);
		GridPane.setConstraints(taskCounterLab, 1, 2, 1, 1, HPos.CENTER, null);
		
		Button butNextTask = new Button("Next");
		GridPane.setConstraints(butNextTask, 2, 2, 1, 1, HPos.RIGHT, null);
	//	GridPane.setFillWidth(butNextTask, true);
	//	butNextTask.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		butNextTask.setOnAction(e ->
		{
			if(this.c != tasks.toArray().length - 1)
			{
				changeContentOfWind(taskArray[++c], window);
			}
			else
			{
				NotificationWind.display("This is the last task!", "#ffff00");
			}
		});
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(5, 5, 5, 5));
		layout.setVgap(5);
		
		layout.getChildren().addAll(taskShow, labAnswer, fieldAnswer, butSubmitAnswer, butPrevTask, taskCounterLab, butNextTask);

		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		
		window.setResizable(false);
		window.setTitle(task.getTaskName());
		window.setScene(scene);
		window.show();
	}
	
	private void closeWindow(Stage window)
	{
		ConfirmationBox cb = new ConfirmationBox("Go back", "Are you sure to go back?");
		if(cb.display())
		{window.close();}
	}
}
