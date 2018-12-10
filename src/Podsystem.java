import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Podsystem {
	
	public String nazwa;
	static Stage window;
	static TextField nazwaPodsystem;
	static TextField napieciePodsystem, mocPodsystem, przesuniecieKatowe;

	public static void okienkoPodsystem() {
		window = new Stage();
		GridPane gridPane = new GridPane();
		BorderPane borderPodsystem = new BorderPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20,20,20,125));
		hBox.setSpacing(20);
		gridPane.setVgap(20);
		gridPane.setHgap(10);
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		//Nazwa Podsystemu
		Label nazwaPodsyst = new Label("Nazwa podsystemu:");
		GridPane.setConstraints(nazwaPodsyst, 1, 1);
		nazwaPodsyst.setMinWidth(100);
		nazwaPodsystem = new TextField();
		nazwaPodsystem.setPromptText("WprowadŸ nazwê podsystemu");
		nazwaPodsystem.setMinWidth(200);
		GridPane.setConstraints(nazwaPodsystem, 2, 1);
		
		//Napiêcie Podsystemu
		Label napieciePodsyst = new Label("Wartoœæ napiêcia [kV]:\n(wartoœcie miêdzyfazowe)");
		GridPane.setConstraints(napieciePodsyst, 1, 2);
		napieciePodsyst.setMinWidth(100);
		napieciePodsystem = new TextField();
		napieciePodsystem.setPromptText("WprowadŸ wartoœæ napiêcia [kV]");
		napieciePodsystem.setMinWidth(200);
		GridPane.setConstraints(napieciePodsystem, 2, 2);
		
		//Przesuniêcie k¹towe napiecia
		Label przesuniecieNap = new Label("Przesuniêcie k¹towe");
		GridPane.setConstraints(przesuniecieNap, 1, 3);
		przesuniecieNap.setMinWidth(100);
		przesuniecieKatowe = new TextField();
		przesuniecieKatowe.setPromptText("Wartoœæ przesuniêcia fazowego");
		przesuniecieKatowe.setMinWidth(200);
		GridPane.setConstraints(przesuniecieKatowe, 2, 3);
		
		//Moc zwarciowa Podsystemu
		Label mocPodsyst = new Label("Wartoœæ mocy zwarciowej [MVA]:");
		GridPane.setConstraints(mocPodsyst, 1, 4);
		mocPodsyst.setMinWidth(100);
		mocPodsystem = new TextField();
		mocPodsystem.setPromptText("Moc zwarciowa podsystemu [MVA]");
		mocPodsystem.setMinWidth(200);
		GridPane.setConstraints(mocPodsystem, 2, 4);
	
		//Przycisk OK
		Button okButton = new Button("OK");
		okButton.setMinWidth(65);
		okButton.setOnAction(e -> { 
			Insert(nazwaPodsystem.getText(), napieciePodsystem.getText(), przesuniecieKatowe.getText(), mocPodsystem.getText());
			window.close();
			OknoPodgladu.odswiezenieDrzewa();	
		});
		
		//Przycisk anuluj
		Button anulujButton = new Button("Anuluj");
		anulujButton.setOnAction(e -> zamknijPodsystem());
		anulujButton.setMinWidth(65);
		
		hBox.getChildren().addAll(okButton, anulujButton);
		
		GridPane.setConstraints(anulujButton, 4, 4);
		
		gridPane.getChildren().setAll(nazwaPodsyst, nazwaPodsystem, napieciePodsyst, napieciePodsystem, przesuniecieNap, przesuniecieKatowe);
		
		borderPodsystem.setTop(gridPane);
		borderPodsystem.setCenter(hBox);
		
		
		Scene scene = new Scene(borderPodsystem, 405, 200);
		window.setScene(scene);
		window.show();
		
	}
	
	public static void zamknijPodsystem(){
		window.close();
	}
		
	
	public static void Insert(String nazwa, String napiecie, String przesuniecieKatowe, String mocZwarciowa){
		String sql = "INSERT INTO PODSYSTEM(nazwa, napiecie, przesuniecieKatowe) VALUES(?, ?, ?)";
		
		try(Connection conn = Stacja.polaczenie();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, nazwa);
				pstmt.setString(2, napiecie);
				pstmt.setString(3, przesuniecieKatowe);
				pstmt.execute();	
				OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Podsystem zosta³ pomyœlnie "
						+ "dodany do bazy danych."));
		} catch(SQLException e){
			System.out.println(e.getMessage());
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Podsystem nie zosta³ dodany. "
					+ "Musisz wprowadziæ wartoœci wszystkich parametrów."));
		}
	}

}
