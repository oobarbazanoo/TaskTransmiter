import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;

import javafx.scene.paint.Color;

import javafx.geometry.*;

public class ConfirmationBox
{
	private String title, question;
	private boolean res;
	
	public ConfirmationBox(String title, String message)
	{
		this.title = title;
		this.question = message;
	}
	
	public boolean display()
	{
		Stage window = new Stage();
		window.setTitle(this.title);
		window.setMinWidth(250);
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		Label label = new Label();
		label.setText(this.question);
		label.setTextFill(Color.WHITE);
		Button bYes = new Button("Yes"),
			   bNo = new Button("No");

		bYes.setOnAction(e -> 
		{
			res = true;
			window.close();
		});
		
		bNo.setOnAction(e -> 
		{
			res = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, bYes, bNo);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: gray");
		
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("style.css");
		
		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();

		return res;
	}
}
