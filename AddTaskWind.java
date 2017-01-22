import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import java.io.File;
import java.io.FileOutputStream;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class AddTaskWind 
{
	private boolean added;
	private Stage wind;
	private File image;
	private ArrayList<Task> tasks;
	
	/*
	 * Create window for adding task.
	 */
	public AddTaskWind(ArrayList<Task> tasks)
	{this.tasks = tasks;}
	
	/*
	 * Display window for adding task.
	 * @return {@code true} if task was added {@code false} otherwise
	*/
	public boolean display(Stage parent)
	{
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
		GridPane.setConstraints(areaWithName, 1, 0);
		
		//text property for title and name area
		wind.titleProperty().bind((new SimpleStringProperty("Add a new task ( ")).concat(areaWithName.textProperty()).concat(" )"));
		
		Label labForTask = new Label();
		labForTask.setText("Task by text(if there is no task by image): ");
		labForTask.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForTask, 0, 1, 1, 1, null, VPos.TOP);
		
		TextArea areaWithTask = new TextArea();
		areaWithTask.setWrapText(true);
		GridPane.setConstraints(areaWithTask, 1, 1);
		
		Label labForTaskByImage = new Label();
		labForTaskByImage.setText("Task by image(if there is no task by text): ");
		labForTaskByImage.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForTaskByImage, 0, 2, 1, 1, HPos.RIGHT, null);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll
		(
                new FileChooser.ExtensionFilter("PNG", "*.png", "GIF", "*.gif", "JPG", "*.jpg", "JPEG", "*.jpeg")
        );
		Button buttonAddTaskByImage = new Button("Find image on disk");
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
		GridPane.setConstraints(areaWithAnswers, 1, 3);
		
		Label labForPoints = new Label("Points for solving: ");
		labForPoints.setTextFill(Color.WHITE);
		GridPane.setConstraints(labForPoints, 0, 4, 1, 1, HPos.RIGHT, null);
		TextField fieldWithPoints = new TextField();
		GridPane.setConstraints(fieldWithPoints, 1, 4);
		
		Button confirmButton = new Button("Add task"),
			   closeButton = new Button("Go back");
		GridPane.setConstraints(closeButton, 0, 5, 1, 1, HPos.CENTER, null);
		GridPane.setConstraints(confirmButton, 1, 5, 1, 1, HPos.CENTER, null);
		
		confirmButton.setOnAction(e -> 
		{
			while(true)
			{
				if(saveTask(areaWithName.getText(), areaWithTask.getText(), areaWithAnswers.getText(), fieldWithPoints.getText()))
				{
					areaWithName.setText("");
					areaWithTask.setText("");
					areaWithAnswers.setText("");
					fieldWithPoints.setText("");
					buttonAddTaskByImage.setText("Find image on disk");
					this.image = null;
					this.added = true;
					NotificationWind.display("Task was successfully added!", "#00ff00");
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
		
		layout.getChildren().addAll(labForTaskName, areaWithName,
									labForTask, areaWithTask, labForTaskByImage, buttonAddTaskByImage, labForAnswers,
									areaWithAnswers, labForPoints, fieldWithPoints, closeButton, confirmButton);
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		
		wind.setScene(scene);
		wind.setResizable(false);
		wind.showAndWait();
		
		parent.show();
		return added;
	}
	
	private boolean saveTask(String taskName, String taskText, String answersText, String pointsForSolving)
	{
		if(checkForValidTaskName(taskName))
		{}
		else
		{
			NotificationWind.display("Name is invalid.",  "#ff0000");
			return false;
		}
		
		if(answersText.length() == 0)
		{
			NotificationWind.display("Answers weren`t entered at all.",  "#ff0000");
			return false;
		}
		
		String[] answers = answersText.split(",");
		
		int points;
		try
		{points = Integer.parseInt(pointsForSolving);}
		catch(Exception e)
		{
			NotificationWind.display("Points were enetered wrong.",  "#ff0000");
			return false;
		}
		
		File dir = FileWorker.createDirectoryNearRunnableJar(Constants.NAME_OF_DATA_DIR);
		if(this.image != null)
		{FileWorker.copyFile(this.image, new File(dir.getAbsolutePath() + "\\" + taskName + ".jpg"));}
		else
		{}
		
		Task task = new Task(taskName, taskText, new File(dir.getAbsolutePath() + "\\" + taskName + ".jpg"), answers, points);
		
		tasks.add(task);
		
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
		
		return true;
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
			if(t.getTaskName().equals(taskName))
			{return false;}
		}
		
		return true;
	}

	private void findImageOnDisk(FileChooser fileChooser) 
	{
		File file = fileChooser.showOpenDialog(wind);
		this.image = (file == null)?this.image:file;
	}

	private void closeWindow()
	{
		ConfirmationBox cb = new ConfirmationBox("Go back", "Are you sure to go back?");
		if(cb.display())
		{wind.close();}
	}
}
