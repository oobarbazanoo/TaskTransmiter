import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class ChangeTaskWind 
{
	private File image;
	private Stage parent, wind;
	private ArrayList<Task> tasks;
	private Task task;
	private ChooseTaskToEditWind cttew;
	
	public ChangeTaskWind(ArrayList<Task> tasks, Task task, ChooseTaskToEditWind cttew)
	{
		this.tasks = tasks;
		this.task = task;
		this.cttew = cttew;
	}
	
	public void display(Stage parent)
	{
		this.parent = parent;
		parent.hide();
		
		wind = new Stage();
		wind.setOnCloseRequest(e ->
		{
			e.consume();
			closeWindow();
		});
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(5, 5, 5, 5));
		layout.setVgap(5);
		
		Label labForTaskName = new Label();
		labForTaskName.setText("Name of the task(must be unique): ");
		labForTaskName.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForTaskName, 0, 0, 1, 1, HPos.RIGHT, null);
		
		RestrictedTextField areaWithName = new RestrictedTextField();
		areaWithName.setMaxLength(Constants.LENGTH_OF_POSSIBLE_NAME);
		areaWithName.setText(task.getTaskName());
		GridPane.setConstraints(areaWithName, 1, 0, 2, 1);
		
		//text property for title and name area
		wind.titleProperty().bind((new SimpleStringProperty("Change the task ( ")).concat(areaWithName.textProperty()).concat(" )"));
		
		Label labForTask = new Label();
		labForTask.setText("Task by text(if there is no task by image): ");
		labForTask.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForTask, 0, 1, 1, 1, null, VPos.TOP);
		
		TextArea areaWithTask = new TextArea();
		areaWithTask.setWrapText(true);
		areaWithTask.setText(task.getTaskText());
		GridPane.setConstraints(areaWithTask, 1, 1, 2, 1);
		
		Label labForTaskByImage = new Label();
		labForTaskByImage.setText("Task by image(if there is no task by text): ");
		labForTaskByImage.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForTaskByImage, 0, 2, 1, 1, HPos.RIGHT, null);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll
		(
                new FileChooser.ExtensionFilter("PNG", "*.png", "GIF", "*.gif", "JPG", "*.jpg", "JPEG", "*.jpeg")
        );
		Button buttonAddTaskByImage = new Button((task.getTaskImage() == null)?"Find image on disk":task.getTaskImage().getAbsolutePath());
		this.image = task.getTaskImage();
		buttonAddTaskByImage.setOnAction(e -> 
		{
			findImageOnDisk(fileChooser);
			if(this.image != null)
			{buttonAddTaskByImage.setText(image.getAbsolutePath());}
		});
		GridPane.setConstraints(buttonAddTaskByImage, 1, 2);
		
		Label labForAnswers = new Label();
		labForAnswers.setText("Possible answers(separated by comma): ");
		labForAnswers.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForAnswers, 0, 3, 1, 1, HPos.RIGHT, null);
		
		TextField areaWithAnswers = new TextField();
		String answers = "";
		for(int i = 0; i < task.getAnswers().length; i++)
		{
			if(i == task.getAnswers().length-1)
			{answers+= task.getAnswers()[i];}
			else
			{answers+= task.getAnswers()[i] + ",";}
		}
		areaWithAnswers.setText(answers);
		GridPane.setConstraints(areaWithAnswers, 1, 3, 2, 1);
		
		Label labForPoints = new Label("Points for solving: ");
		labForPoints.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForPoints, 0, 4, 1, 1, HPos.RIGHT, null);
		TextField fieldWithPoints = new TextField();
		fieldWithPoints.setText(task.getPointsForSolving() + "");
		GridPane.setConstraints(fieldWithPoints, 1, 4, 2, 1);
		
		Button confirmButton = new Button("Save changed task"),
			   closeButton = new Button("Go back"),
			   deleteBut = new Button("Delete");
		GridPane.setConstraints(closeButton, 0, 5, 1, 1, HPos.CENTER, null);
		GridPane.setConstraints(deleteBut, 1, 5, 1, 1, HPos.CENTER, null);
		GridPane.setConstraints(confirmButton, 2, 5, 1, 1, HPos.CENTER, null);
		
		confirmButton.setOnAction(e -> 
		{
			while(true)
			{
				if(saveTask(areaWithName.getText(), areaWithTask.getText(), areaWithAnswers.getText(), fieldWithPoints.getText()))
				{
					NotificationWind.display("Task was successfully changed!", "#00ff00");
					break;
				}
				else
				{break;}
			}
		});
		
		closeButton.setOnAction(e ->
		{
			closeWindow();
		});
		
		deleteBut.setOnAction(e ->
		{
			ConfirmationBox cb = new ConfirmationBox("Delete", "Are you sure to delete the task?");
			if(cb.display())
			{
				if(removeImage(this.task.getTaskImage()) && this.tasks.remove(this.task))
				{
					saveTasks();
					NotificationWind.display("Removed successfully!", "#00ff00");
					close();
				}
				else
				{NotificationWind.display("Not removed!", "#ff0000");}
			}
			else
			{}
		});
		
		layout.getChildren().addAll(labForTaskName, areaWithName,
									labForTask, areaWithTask, labForTaskByImage, buttonAddTaskByImage, labForAnswers,
									areaWithAnswers, labForPoints, fieldWithPoints, closeButton, confirmButton,
									deleteBut);
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		
		wind.setScene(scene);
		wind.setResizable(false);
		wind.showAndWait();
		
		parent.show();
	}
	
	private boolean removeImage(File taskImage)
	{
		if(taskImage != null)
		{taskImage.delete();}
		return true;
	}

	private void findImageOnDisk(FileChooser fileChooser) 
	{
		File file = fileChooser.showOpenDialog(wind);
		this.image = (file == null)?this.image:file;
	}
	
	private boolean saveTask(String taskName, String taskText, String answersText, String pointsForSolving)
	{
		
		if(checkForValidTaskName(taskName))
		{}
		else
		{
			NotificationWind.display("Name is invalid!",  "#ff0000");
			return false;
		}
		
		if(answersText.length() == 0)
		{
			NotificationWind.display("Answers weren`t entered at all!",  "#ff0000");
			return false;
		}
		
		String[] answers = answersText.split(",");
		
		int points;
		try
		{points = Integer.parseInt(pointsForSolving);}
		catch(Exception e)
		{
			NotificationWind.display("Points were enetered wrong!",  "#ff0000");
			return false;
		}
		
		File dir = FileWorker.createDirectoryNearRunnableJar(Constants.NAME_OF_DATA_DIR);
		if(this.image != null)
		{FileWorker.copyFile(this.image, new File(dir.getAbsolutePath() + "\\" + taskName + ".jpg"));}
		else
		{}
		
		this.task.setTaskName(taskName);
		this.task.setTaskText(taskText);
		this.task.setTaskImage(new File(dir.getAbsolutePath() + "\\" + taskName + ".jpg"));
		this.task.setAnswers(answers);
		this.task.setPointsForSolving(points);
		
		saveTasks();
		
		return true;
	}

	private void saveTasks()
	{
		File dir = FileWorker.createDirectoryNearRunnableJar(Constants.NAME_OF_DATA_DIR);
		try 
		{
			FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + "\\" + Constants.NAME_OF_TASKS_LIST);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tasks);
			oos.close();
			fos.close();
		}
		catch (IOException e) 
		{e.printStackTrace();}
	}
	

	//@return {true} if valid {false} otherwise
	private boolean checkForValidTaskName(String taskName) 
	{
		if(taskName.length() == 0)
		{return false;}
		
		for(int i = 0; i < taskName.length(); i++)
		{
			if(taskName.charAt(i) != ' ')
			{break;}
			
			if(i == taskName.length()-1)
			{return false;}
		}
		
		for(Task t : tasks)
		{
			if(t == this.task)
			{continue;}
			if(t.getTaskName().equals(taskName))
			{return false;}
		}
		
		return true;
	}
	
	private void closeWindow()
	{
		ConfirmationBox cb = new ConfirmationBox("Go back", "Are you sure to go back?");
		if(cb.display())
		{
			close();
		}
	}
	
	private void close()
	{
		wind.close();
		cttew.refreshCB();
		parent.show();
	}
}
