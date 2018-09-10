import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Ostrzezenie {
	
	static Boolean odpowiedz = false;
	static Stage window;

	public static Boolean okienko(String tytulOkna, String wiadomosc){

		window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(tytulOkna);
		window.setMinWidth(250);
		
		Label label = new Label(wiadomosc);
		
		Button takButton = new Button("Tak");
		takButton.setOnAction(e -> {
			odpowiedz = true;
			window.close();
		});
		takButton.setMinWidth(60);
		Button nieButton = new Button("Nie");
		nieButton.setOnAction(e -> {
			odpowiedz = false;
			window.close();
		});
		nieButton.setMinWidth(60);
		
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		vBox.setPadding(new Insets(20,20,20,20));
		hBox.setPadding(new Insets(20,20,0,50));
		hBox.setSpacing(20);
		
		hBox.getChildren().addAll(nieButton, takButton);
		vBox.getChildren().addAll(label, hBox);
		
		Scene scene = new Scene(vBox);
		window.setScene(scene);
		window.showAndWait();
		
		return odpowiedz;
		
	}
}
