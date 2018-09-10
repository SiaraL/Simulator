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

public class Stacja {
	
	static Stage window;
	static TextField nazwaStacji;
	static TextField napiecieStacji, przesuniecieKatowe;
	
	public static void okienkoStacja(){
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
		Label nazwaStac = new Label("Nazwa stacji:");
		GridPane.setConstraints(nazwaStac, 1, 1);
		nazwaStac.setMinWidth(100);
		nazwaStacji = new TextField();
		nazwaStacji.setPromptText("WprowadŸ nazwê stacji");
		nazwaStacji.setMinWidth(200);
		GridPane.setConstraints(nazwaStacji, 2, 1);
		GridPane.setConstraints(przesuniecieKatowe, 2, 3);
		
		//Przycisk OK
		Button okButton = new Button("OK");
		okButton.setMinWidth(65);
		okButton.setOnAction(e ->{ 
			Insert(nazwaStacji.getText());
			window.close();
			OknoPodgladu.odswiezenieDrzewa();			
		});
		//Przycisk anuluj
		Button anulujButton = new Button("Anuluj");
		anulujButton.setOnAction(e -> zamknijStacja());
		anulujButton.setMinWidth(65);
		
		hBox.getChildren().addAll(okButton, anulujButton);
		
		GridPane.setConstraints(anulujButton, 4, 4);
		
		gridPane.getChildren().setAll(nazwaStac, nazwaStacji);
		
		borderStacja.setTop(gridPane);
		borderStacja.setCenter(hBox);
		
		
		Scene scene = new Scene(borderStacja, 350, 110);
		window.setScene(scene);
		window.show();
	}
	
	public static void zamknijStacja(){
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
	
	public static void Insert(String nazwa){
		String sql = "INSERT INTO STACJA (nazwa, napiecie, przesuniecieKatowe) VALUES (?, ?, ?)";
		
		try(Connection conn = polaczenie();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, nazwa);
				pstmt.execute();	
				OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Stacja zosta³a pomyœlnie "
						+ "dodana do bazy danych."));
		} catch(SQLException e){
			System.out.println(e.getMessage());
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Stacja nie zosta³a dodana. "
					+ "Musisz wprowadziæ wartoœci wszystkich parametrów."));
		}
	}
	
	
}
