import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;

public class ChooseTaskToEditWind 
{
	private ArrayList<Task> tasks;
	private Stage wind, parent;
	private GridPane layout;
	
	public ChooseTaskToEditWind(ArrayList<Task> tasks)
	{this.tasks = tasks;}
	
	public void display(Stage parent)
	{
		parent.hide();
		this.parent = parent;
		
		wind = new Stage();
		wind.setTitle("Choose task to edit");
		wind.setOnCloseRequest(e ->
		{
			e.consume();
			closeWindow();
		});
		
		Label cbPrompt = new Label("Task to edit: ");
		GridPane.setConstraints(cbPrompt, 0, 0);
		GridPane.setMargin(cbPrompt, new Insets(5,5,5,5));
		
		ChoiceBox cb = new ChoiceBox();
		cb.setMinWidth(180);
		cb.setMaxWidth(180);
		cb.getItems().addAll(tasks);
		GridPane.setConstraints(cb, 1, 0);
		GridPane.setMargin(cb, new Insets(5,5,5,5));
		
		Button goBackBut = new Button("Go back");
		GridPane.setConstraints(goBackBut, 0, 1);
		GridPane.setFillHeight(goBackBut, true);
		GridPane.setMargin(goBackBut, new Insets(5,5,5,5));
		goBackBut.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		goBackBut.setOnAction(e ->
		{
			closeWindow();
		});
		
		Button editBut = new Button("Edit");
		GridPane.setConstraints(editBut, 1, 1, 1, 1, HPos.RIGHT, null);
		GridPane.setMargin(editBut, new Insets(5,5,5,5));
		GridPane.setFillHeight(editBut, true);
		editBut.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		editBut.setOnAction(e ->
		{
			Task t = (Task) cb.getSelectionModel().getSelectedItem();
			if(t != null)
			{
				ChangeTaskWind ctw = new ChangeTaskWind(tasks, t, this);
				ctw.display(this.wind);
			}
			else
			{NotificationWind.display("Choose task first!", "#ff0000");}
		});
		
		this.layout = new GridPane();
		layout.getChildren().addAll(cbPrompt, cb, goBackBut, editBut);
		
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		
		wind.setResizable(false);
		wind.setScene(scene);
		wind.show();
	}
	
	public void refreshCB()
	{
		ChoiceBox cb = new ChoiceBox();
		cb.setMinWidth(180);
		cb.setMaxWidth(180);
		cb.getItems().addAll(tasks);
		GridPane.setConstraints(cb, 1, 0);
		GridPane.setMargin(cb, new Insets(5,5,5,5));
		Button editBut = new Button("Edit");
		GridPane.setConstraints(editBut, 1, 1, 1, 1, HPos.RIGHT, null);
		GridPane.setMargin(editBut, new Insets(5,5,5,5));
		GridPane.setFillHeight(editBut, true);
		editBut.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		editBut.setOnAction(e ->
		{
			Task t = (Task) cb.getSelectionModel().getSelectedItem();
			if(t != null)
			{
				ChangeTaskWind ctw = new ChangeTaskWind(tasks, t, this);
				ctw.display(this.wind);
			}
			else
			{NotificationWind.display("Choose task first!", "#ff0000");}
		});
		this.layout.getChildren().addAll(cb, editBut);
	}
	
	private void closeWindow()
	{
		ConfirmationBox cb = new ConfirmationBox("Go back", "Are you sure to go back?");
		if(cb.display())
		{
			wind.close();
			parent.show();
		}
	}
}












