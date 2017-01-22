import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.stage.*;



public class NotificationWind 
{
	public static void display(String message, String colorOfBackground)
	{
		Stage wind = new Stage();
		wind.setTitle("Notification!");
		wind.setMinWidth(250);
		wind.initModality(Modality.APPLICATION_MODAL);
		wind.setResizable(false);
		
		Label label = new Label(message);
		
		Button button = new Button("Ok");
		button.setOnAction(e ->
		{
			wind.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: " + colorOfBackground);
		
		Scene scene = new Scene(layout);
		wind.setScene(scene);
		wind.showAndWait();
	}
}
