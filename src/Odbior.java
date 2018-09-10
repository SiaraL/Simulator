import java.sql.Connection;
import java.sql.DriverManager;
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

public class Odbior {
	
	static Stage window;
	static TextField nazwaOdbioru, mocOdbioru;

	public static void okienkoObdior(){
		window = new Stage();
		GridPane gridPane = new GridPane();
		BorderPane borderStacja = new BorderPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20,20,20,100));
		hBox.setSpacing(20);
		gridPane.setVgap(20);
		gridPane.setHgap(10);
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		//Nazwa stacji
		Label nazwaOdb = new Label("Nazwa odbioru:");
		GridPane.setConstraints(nazwaOdb, 1, 1);
		nazwaOdb.setMinWidth(100);
		nazwaOdbioru = new TextField();
		nazwaOdbioru.setPromptText("WprowadŸ nazwê odioru");
		nazwaOdbioru.setMinWidth(200);
		GridPane.setConstraints(nazwaOdbioru, 2, 1);
		
		//Moc odbioru
		Label mocOdb = new Label("Moc odbioru [MVA]:");
		GridPane.setConstraints(mocOdb, 1, 2);
		mocOdb.setMinWidth(115);
		mocOdbioru = new TextField();
		mocOdbioru.setPromptText("WprowadŸ moc odbioru [MVA]");
		mocOdbioru.setMinWidth(200);
		GridPane.setConstraints(mocOdbioru, 2, 2);
		
		//Przycisk OK
		Button okButton = new Button("OK");
		okButton.setMinWidth(65);
		okButton.setOnAction(e ->{ 
			Insert(nazwaOdbioru.getText(), mocOdbioru.getText());
			window.close();
			OknoPodgladu.odswiezenieDrzewa();	
		});
		//Przycisk anuluj
		Button anulujButton = new Button("Anuluj");
		anulujButton.setOnAction(e -> zamknijOdbior());
		anulujButton.setMinWidth(65);
		
		hBox.getChildren().addAll(okButton, anulujButton);
		
		GridPane.setConstraints(anulujButton, 4, 4);
		
		gridPane.getChildren().setAll(nazwaOdb, nazwaOdbioru, mocOdb, mocOdbioru);
		
		borderStacja.setTop(gridPane);
		borderStacja.setCenter(hBox);
		
		
		Scene scene = new Scene(borderStacja, 350, 155);
		window.setScene(scene);
		window.show();
	}
	
	public static void zamknijOdbior(){
		window.close();
	}
	
	public static Connection polaczenie(){
		String url = "jdbc:sqlite:BazaDanych.db";
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url);
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public static void Insert(String nazwa, String moc){
		String sql = "INSERT INTO ODBIOR (nazwa, moc) VALUES(?, ?)";
		
		try(Connection conn = polaczenie();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, nazwa);
				pstmt.setString(2, moc);
				pstmt.execute();	
				OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Odbior zosta³ pomyœlnie "
						+ "dodany do bazy danych."));
		} catch(SQLException e){
			System.out.println(e.getMessage());
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Odbiór nie zosta³ dodany. "
					+ "Musisz wprowadziæ wartoœci wszystkich parametrów."));
		}
	}
}
