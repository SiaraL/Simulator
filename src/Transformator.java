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

public class Transformator {

	public String nazwa;
	static Stage window;
	static TextField nazwaTransformator, przekladnia;
	static TextField napiecieStronyGornejTransformator, napiecieStronyDolnejTransformator;
	

	public static void okienkoTransformator() {
		window = new Stage();
		GridPane gridPane = new GridPane();
		BorderPane borderTransformator = new BorderPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20,20,20,117));
		hBox.setSpacing(20);
		gridPane.setVgap(20);
		gridPane.setHgap(10);
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		//Nazwa Transformatoru
		Label nazwaTrafo = new Label("Nazwa Transformatora:");
		GridPane.setConstraints(nazwaTrafo, 1, 1);
		nazwaTrafo.setMinWidth(100);
		nazwaTransformator = new TextField();
		nazwaTransformator.setPromptText("WprowadŸ nazwê Transformatora");
		nazwaTransformator.setMinWidth(200);
		GridPane.setConstraints(nazwaTransformator, 2, 1);
		
		//Napiêcie strony gornej Transformatora
		Label napiecieStronyGornejTrafo = new Label("Napiêcie strony górnej[kV]:");
		GridPane.setConstraints(napiecieStronyGornejTrafo, 1, 2);
		napiecieStronyGornejTrafo.setMinWidth(100);
		napiecieStronyGornejTransformator = new TextField();
		napiecieStronyGornejTransformator.setPromptText("WprowadŸ wartoœæ napiêcia [kV]");
		napiecieStronyGornejTransformator.setMinWidth(200);
		GridPane.setConstraints(napiecieStronyGornejTransformator, 2, 2);
		
		//napiecieStronyDolnej Transformatora
		Label napiecieStronyDolnejTrafo = new Label("Napiêcie strony dolnej [kV]:");
		GridPane.setConstraints(napiecieStronyDolnejTrafo, 1, 3);
		napiecieStronyDolnejTrafo.setMinWidth(100);
		napiecieStronyDolnejTransformator = new TextField();
		napiecieStronyDolnejTransformator.setPromptText("WprowadŸ wartoœæ napiêcia [kV]");
		napiecieStronyDolnejTransformator.setMinWidth(200);
		GridPane.setConstraints(napiecieStronyDolnejTransformator, 2, 3);
		
		//napiecieStronyDolnej zwarciowa Transformatoru
		Label przekladn = new Label("Przek³adnia");
		GridPane.setConstraints(przekladn, 1, 4);
		przekladn.setMinWidth(100);
		przekladnia = new TextField();
		przekladnia.setPromptText("Podaj wartoœæ przek³adni");
		przekladnia.setMinWidth(200);
		GridPane.setConstraints(przekladnia, 2, 4);
		
		//Przycisk OK
		Button okButton = new Button("OK");
		okButton.setMinWidth(65);
		okButton.setOnAction(e -> {
			Insert(nazwaTransformator.getText(), napiecieStronyGornejTransformator.getText(), napiecieStronyDolnejTransformator.getText(), przekladnia.getText());
			window.close();
			OknoPodgladu.odswiezenieDrzewa();	
		});
		
		//Przycisk anuluj
		Button anulujButton = new Button("Anuluj");
		anulujButton.setOnAction(e -> zamknijTransformator());
		anulujButton.setMinWidth(65);
		
		hBox.getChildren().addAll(okButton, anulujButton);
		
		GridPane.setConstraints(anulujButton, 4, 4);
		
		gridPane.getChildren().setAll(nazwaTrafo, nazwaTransformator, napiecieStronyGornejTrafo, napiecieStronyGornejTransformator,
				napiecieStronyDolnejTrafo, napiecieStronyDolnejTransformator, przekladn, przekladnia);
		
		borderTransformator.setTop(gridPane);
		borderTransformator.setCenter(hBox);
		
		
		Scene scene = new Scene(borderTransformator, 380, 250);
		window.setScene(scene);
		window.show();
		
	}
	
	public static void zamknijTransformator(){
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
	
	public static void Insert(String nazwa, String napiecieStronaGorna, String napiecieStronaDolna, String przekladnia){
		String sql = "INSERT INTO TRANSFORMATOR(nazwa, napiecieStronaGorna, napiecieStronaDolna, przekladnia) VALUES(?, ?, ?, ?)";
		
		try(Connection conn = polaczenie();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, nazwa);
				pstmt.setString(2, napiecieStronaGorna);
				pstmt.setString(3, napiecieStronaDolna);
				pstmt.setString(4, przekladnia);
				pstmt.execute();	
				OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Transformator zosta³ "
						+ "pomyœlnie dodany do bazy danych."));		
		} catch(SQLException e){
			System.out.println(e.getMessage());
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Transformator nie zosta³ dodany. "
					+ "Musisz wprowadziæ wartoœci wszystkich parametrów."));
		}
	}

}
