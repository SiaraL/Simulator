import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Zwarcie {
	
	public String nazwa;
	static Stage window;
	static TextField nazwaZwarcia;
	static TextField rodzajZwarcia, stacjaOdniesienia, odlegloscZwarcia, rezystancjaPrzejscia;
	static String wybranaLinia = "";
	
	public static void okienkoZwarcie(){
		window = new Stage();
		GridPane gridPane = new GridPane();
		BorderPane borderStacja = new BorderPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20,20,20,165));
		hBox.setSpacing(20);
		gridPane.setVgap(20);
		gridPane.setHgap(10);
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		//Nazwa zwarcia
		Label nazwaZwar = new Label("Nazwa zwarcia:");
		GridPane.setConstraints(nazwaZwar, 1, 1);
		nazwaZwar.setMinWidth(100);
		nazwaZwarcia = new TextField();
		nazwaZwarcia.setPromptText("WprowadŸ nazwê zwarcia");
		nazwaZwarcia.setMinWidth(200);
		GridPane.setConstraints(nazwaZwarcia, 2, 1);
		
		//Rodzaj zwarcia
		Label rodzajZwar = new Label("Wybierz rodzaj zwarcia:");
		GridPane.setConstraints(rodzajZwar, 1, 2);
		rodzajZwar.setMinWidth(100);
		
		ChoiceBox<String> rodzajZwarcia = new ChoiceBox<String>();
		rodzajZwarcia.getItems().addAll(
			"Jednofazowe K1: faza A",
			"Jednofazowe K1: faza B",
			"Jednofazowe K1: faza C",
			"Dwufazowe K2: faza A i B",
			"Dwufazowe K2: faza A i C",
			"Dwufazowe K2: faza B i C",
			"Trójfazowe");
		rodzajZwarcia.setMinWidth(200);
		GridPane.setConstraints(rodzajZwarcia, 2, 2);
		
		//Wybierz liniê zwarcia
		Label liniaZwar = new Label("Wybierz liniê dotniêt¹ zwarciem");
		GridPane.setConstraints(liniaZwar, 1, 3);
		liniaZwar.setMinWidth(100);
		
		ChoiceBox<String> liniaZwarcia = new ChoiceBox<String>();
		liniaZwarcia.getItems().addAll(
			SelectLinia()
		);
		liniaZwarcia.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> wybranaLinia = newValue);
		liniaZwarcia.setMinWidth(200);
		GridPane.setConstraints(liniaZwarcia, 2, 3);
		
		//Stacja odniesienia
		Label stacjaOdn = new Label("Wybierz punkt odniesienia");
		GridPane.setConstraints(stacjaOdn, 1, 4);
		stacjaOdn.setMinWidth(100);
		
		ChoiceBox<String> stacjaOdniesienia = new ChoiceBox<String>();
		stacjaOdniesienia.setOnMousePressed(e -> {
			stacjaOdniesienia.getItems().clear();
			stacjaOdniesienia.getItems().addAll(SelectPunkty());
		});
		stacjaOdniesienia.setMinWidth(200);
		GridPane.setConstraints(stacjaOdniesienia, 2, 4);
		
		//Miejsce linii
		Label odlegloscZwar = new Label("WprowadŸ odleg³oœæ od stacji odniesienia[km]:");
		GridPane.setConstraints(odlegloscZwar, 1, 5);
		odlegloscZwar.setMinWidth(100);
		odlegloscZwarcia = new TextField();
		odlegloscZwarcia.setPromptText("WprowadŸ odleg³oœæ [km]");
		odlegloscZwarcia.setMinWidth(200);
		GridPane.setConstraints(odlegloscZwarcia, 2, 5);
		
		Label rezPrzejscia = new Label("Rezystancja przejscia (Ohm)");
		GridPane.setConstraints(rezPrzejscia, 1, 6);
		rezPrzejscia.setMinWidth(100);
		rezystancjaPrzejscia = new TextField();
		rezystancjaPrzejscia.setPromptText("WprowadŸ wartoœæ rezystancji [Ohm]");
		rezystancjaPrzejscia.setMinWidth(200);
		GridPane.setConstraints(rezystancjaPrzejscia, 2, 6);
		
		//Przycisk OK
		Button okButton = new Button("OK");
		okButton.setMinWidth(65);
		okButton.setOnAction(e -> {
			Insert(nazwaZwarcia.getText(), rodzajZwarcia.getValue(),liniaZwarcia.getValue(),stacjaOdniesienia.getValue(),odlegloscZwarcia.getText(), rezystancjaPrzejscia.getText());
			window.close();
			OknoPodgladu.odswiezenieDrzewa();	
		});
		
		//Przycisk anuluj
		Button anulujButton = new Button("Anuluj");
		anulujButton.setOnAction(e -> zamknijZwarcie());
		anulujButton.setMinWidth(65);
		
		hBox.getChildren().addAll(okButton, anulujButton);
		
		GridPane.setConstraints(anulujButton, 4, 4);
		
		gridPane.getChildren().setAll(nazwaZwar, nazwaZwarcia, rodzajZwar, rodzajZwarcia, liniaZwar, 
				liniaZwarcia, stacjaOdn, stacjaOdniesienia, odlegloscZwar, odlegloscZwarcia, rezPrzejscia, rezystancjaPrzejscia);
		
		borderStacja.setTop(gridPane);
		borderStacja.setCenter(hBox);
		
		
		Scene scene = new Scene(borderStacja, 480, 335);
		window.setScene(scene);
		window.show();
	}
	
	public static void zamknijZwarcie(){
		window.close();
	}

	
	public static void Insert(String nazwa, String rodzaj, String linia, String stacjaOdniesienia, String odlegloscOdStacjiOdniesienia, String rezystancjaPrzejscia){
		String sql = "INSERT INTO ZWARCIE(nazwa, rodzaj, linia, stacjaOdniesienia, odlegloscOdStacjiOdniesienia, rezystancjaPrzejscia) VALUES(?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = Stacja.polaczenie();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, nazwa);
				pstmt.setString(2, rodzaj);
				pstmt.setString(3, linia);
				pstmt.setString(4, stacjaOdniesienia);
				pstmt.setString(5, odlegloscOdStacjiOdniesienia);
				pstmt.setString(6, rezystancjaPrzejscia);
				pstmt.execute();		
				OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Zwarcie zosta³o pomyœlnie "
						+ "dodane do bazy danych."));
		} catch(SQLException e){
			System.out.println(e.getMessage());
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Zwarcie nie zosta³o dodane. "
					+ "Musisz wprowadziæ wartoœci wszystkich parametrów."));
		}
	}
	
	public static ArrayList<String> SelectLinia(){
		String sql = "SELECT nazwa FROM LINIA";
		ArrayList<String> s = new ArrayList<String>();
		try(Connection conn = Stacja.polaczenie();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
				while (rs.next()){
					s.add(rs.getString("nazwa"));
				}
			
			} catch(SQLException e){
				System.out.println(e.getMessage());
			}
		return s;
	}
	
	public static ArrayList<String> SelectPunkty(){
		String sql = "SELECT koniec1, koniec2 FROM LINIA WHERE nazwa = '" + wybranaLinia +"'";
		ArrayList<String> s = new ArrayList<String>();
		try(Connection conn = Stacja.polaczenie();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
				while (rs.next()){
					s.add(rs.getString("koniec1"));
					s.add(rs.getString("koniec2"));
				}
			
			} catch(SQLException e){
				System.out.println(e.getMessage());
			}
		
		return s;
	}
	
	

}
