import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Linia {

	public String nazwa;
	static Stage window;
	static TextField nazwaLinia;
	static TextField napiecieLinia, iloscTorow, dlugoscLinia;
	static String getStacja1 = "", getStacja2 = "", wyborNapiecia = "", rezystywnosc = "", liczbaTorow = "", tor = "",
			typPrzewodowOdgromowe = "";

	public static void okienkoLinia() {
		window = new Stage();
		GridPane gridPane = new GridPane();
		GridPane gridPane2 = new GridPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20, 20, 20, 152));
		hBox.setSpacing(20);
		gridPane.setVgap(20);
		gridPane.setHgap(10);
		gridPane2.setVgap(20);
		gridPane2.setHgap(10);

		window.initModality(Modality.APPLICATION_MODAL);

		// Nazwa Linia
		Label nazwaLin = new Label("Nazwa Linii:");
		GridPane.setConstraints(nazwaLin, 1, 1);
		nazwaLin.setMinWidth(100);
		nazwaLinia = new TextField();
		nazwaLinia.setPromptText("WprowadŸ nazwê Linii");
		nazwaLinia.setMinWidth(200);
		GridPane.setConstraints(nazwaLinia, 2, 1);

		// Napiêcie Linii
		Label napiecieLin = new Label("Wartoœæ napiêcia [kV]:");
		GridPane.setConstraints(napiecieLin, 1, 2);
		napiecieLin.setMinWidth(100);

		ChoiceBox<String> wyborNapieciaLinii = new ChoiceBox<String>();
		wyborNapieciaLinii.getItems().addAll("110", "220", "400");
		wyborNapieciaLinii.setMinWidth(200);
		GridPane.setConstraints(wyborNapieciaLinii, 2, 2);
		wyborNapieciaLinii.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> wyborNapiecia = newValue);

		// Napiêcie Linii
		Label iloscTorow = new Label("Rodzaj linii");
		GridPane.setConstraints(iloscTorow, 1, 3);
		iloscTorow.setMinWidth(200);

		ChoiceBox<String> iloscTorowLinii = new ChoiceBox<String>();
		iloscTorowLinii.getItems().addAll("jednotorowa", "dwutorowa");
		iloscTorowLinii.setMinWidth(200);
		GridPane.setConstraints(iloscTorowLinii, 2, 3);
		iloscTorowLinii.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			tor = newValue;
			if (tor == "jednotorowa") {
				liczbaTorow = "1";
			}
			if (tor == "dwutorowa") {
				liczbaTorow = "2";
			}
		});

		// Napiêcie Linii
		Label typPrzewodowOdgromowych = new Label("Typ przewodów odgromowych:");
		GridPane.setConstraints(typPrzewodowOdgromowych, 1, 5);
		typPrzewodowOdgromowych.setMinWidth(100);
		ChoiceBox<String> wyborOdgromowe = new ChoiceBox<String>();
		wyborOdgromowe.setOnMousePressed(e -> {
			wyborOdgromowe.getItems().clear();
			wyborOdgromowe.getItems().addAll(SelectTypPrzewodow());
		});
		wyborOdgromowe.setMinWidth(200);
		GridPane.setConstraints(wyborOdgromowe, 2, 5);
		wyborOdgromowe.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> typPrzewodowOdgromowe = newValue);

		// Napiêcie Linii
		Label rezystywnoscGruntu = new Label("Rezystywnoœæ gruntu (Ohm):");
		GridPane.setConstraints(rezystywnoscGruntu, 1, 4);
		rezystywnoscGruntu.setMinWidth(100);
		ChoiceBox<String> rezystywnoscGrunt = new ChoiceBox<String>();
		rezystywnoscGrunt.getItems().addAll("100", "1000");
		rezystywnoscGrunt.setMinWidth(200);
		GridPane.setConstraints(rezystywnoscGrunt, 2, 4);
		rezystywnoscGrunt.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> rezystywnosc = newValue);

		// D³ugoœæ linii
		Label dlugoscLin = new Label("D³ugoœc linii [km]:");
		GridPane.setConstraints(dlugoscLin, 1, 6);
		dlugoscLin.setMinWidth(100);
		dlugoscLinia = new TextField();
		dlugoscLinia.setPromptText("WprowadŸ d³ugoœc linii [km]");
		dlugoscLinia.setMinWidth(200);
		GridPane.setConstraints(dlugoscLinia, 2, 6);

		// Przyporzadkuj stacje do linii
		Label wyborStacji = new Label("Przyporz¹dkuj stacje, transformator lub podsystem bêd¹ce koñcami linii:");
		wyborStacji.setMinWidth(100);
		wyborStacji.setLineSpacing(20);
		HBox wyborSt = new HBox();
		wyborSt.setPadding(new Insets(20, 20, 0, 10));
		wyborSt.getChildren().add(wyborStacji);

		// stacja1
		Label stacja1 = new Label("Wybierz pierwsz¹ stacjê:");
		GridPane.setConstraints(stacja1, 1, 1);
		stacja1.setMinWidth(100);
		ChoiceBox<String> stac1 = new ChoiceBox<String>();
		stac1.setOnMousePressed(e -> {
			stac1.getItems().clear();
			stac1.getItems().addAll(SelectStacja());
			stac1.getItems().addAll(SelectPodsystem());
			stac1.getItems().addAll(SelectTrafo());
			SelectWartosciLinii();
		});
		stac1.setMinWidth(200);
		GridPane.setConstraints(stac1, 2, 1);
		stac1.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> getStacja1 = newValue);

		// stacja2
		Label stacja2 = new Label("Wybierz drug¹ stacjê:");
		GridPane.setConstraints(stacja2, 1, 2);
		stacja2.setMinWidth(200);
		ChoiceBox<String> stac2 = new ChoiceBox<String>();
		stac2.setOnMousePressed(e -> {
			stac2.getItems().clear();
			stac2.getItems().addAll(SelectStacja());
			stac2.getItems().addAll(SelectPodsystem());
			stac2.getItems().addAll(SelectTrafo());
		});
		stac2.setMinWidth(200);
		GridPane.setConstraints(stac2, 2, 2);
		stac2.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> getStacja2 = newValue);

		// Przycisk OK
		Button okButton = new Button("OK");
		okButton.setMinWidth(65);
		okButton.setOnAction(e -> {
			Insert(nazwaLinia.getText(), wyborNapieciaLinii.getValue(), iloscTorowLinii.getValue(),
					dlugoscLinia.getText(), stac1.getValue(), stac2.getValue(), rezystywnoscGrunt.getValue(),
					wyborOdgromowe.getValue());
			window.close();
			getStacja1 = "";
			getStacja2 = "";
			OknoPodgladu.odswiezenieDrzewa();
		});

		// Przycisk anuluj
		Button anulujButton = new Button("Anuluj");
		anulujButton.setOnAction(e -> {
			zamknijLinia();
			getStacja1 = "";
			getStacja2 = "";
		});
		anulujButton.setMinWidth(65);

		hBox.getChildren().addAll(okButton, anulujButton);

		gridPane.getChildren().addAll(nazwaLin, nazwaLinia, napiecieLin, wyborNapieciaLinii, iloscTorow,
				iloscTorowLinii, rezystywnoscGruntu, rezystywnoscGrunt, typPrzewodowOdgromowych, wyborOdgromowe,
				dlugoscLin, dlugoscLinia);
		gridPane2.getChildren().addAll(stacja1, stac1, stacja2, stac2);

		VBox uklad = new VBox();
		uklad.getChildren().addAll(gridPane, wyborSt, gridPane2, hBox);

		Scene scene = new Scene(uklad, 435, 455);
		window.setScene(scene);
		window.show();

	}

	public static void zamknijLinia() {
		window.close();
	}

	public static void Insert(String nazwa, String napiecie, String rodzaj, String dlugosc, String koniec1,
			String koniec2, String RezystywnoscGruntu, String TypPrzewodowOdgromowych) {
		String sql = "INSERT INTO LINIA(nazwa, napiecie, rodzaj, dlugosc, koniec1, koniec2, RezystywnoscGruntu, TypPrzewodowOdgromowych, R1, X1, R0, X0, R0m, X0m, B1, B0, B0m) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = Stacja.polaczenie();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nazwa);
			pstmt.setString(2, napiecie);
			pstmt.setString(3, rodzaj);
			pstmt.setString(4, dlugosc);
			pstmt.setString(5, koniec1);
			pstmt.setString(6, koniec2);
			pstmt.setString(7, RezystywnoscGruntu);
			pstmt.setString(8, TypPrzewodowOdgromowych);
			for (int i = 0; i < SelectWartosciLinii().size(); i++) {
				pstmt.setString(i + 9, SelectWartosciLinii().get(i));
			}
			pstmt.execute();
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Linia zosta³a pomyœlnie " + "dodana do bazy danych."));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			OknoGlowne.borderPane.setBottom(PasekPomocy
					.pasek("Linia nie zosta³a dodana. " + "Musisz wprowadziæ wartoœci wszystkich parametrów."));
		}
	}

	public static ArrayList<String> SelectStacja() {
		ArrayList<String> napiecie = Macierz.SelectStacja("napiecie");
		ArrayList<String> s = new ArrayList<String>();
		if(wyborNapiecia == "110"){
			for(int i = 0; i < napiecie.size(); i++){
				if(Double.parseDouble(napiecie.get(i)) > 80 && Double.parseDouble(napiecie.get(i)) < 130){
					String sql = "SELECT nazwa FROM STACJA WHERE napiecie = '" + napiecie.get(i) + "'";
					try (Connection conn = Stacja.polaczenie();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql)) {
						while (rs.next()) {
							if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
								s.add(rs.getString("nazwa"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		if(wyborNapiecia == "220"){
			for(int i = 0; i < napiecie.size(); i++){
				if(Double.parseDouble(napiecie.get(i)) > 180 && Double.parseDouble(napiecie.get(i)) < 250){
					String sql = "SELECT nazwa FROM STACJA WHERE napiecie = '" + napiecie.get(i) + "'";
					try (Connection conn = Stacja.polaczenie();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql)) {
						while (rs.next()) {
							if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
								s.add(rs.getString("nazwa"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		if(wyborNapiecia == "400"){
			for(int i = 0; i < napiecie.size(); i++){
				if(Double.parseDouble(napiecie.get(i)) > 350 && Double.parseDouble(napiecie.get(i)) < 450){
					String sql = "SELECT nazwa FROM STACJA WHERE napiecie = '" + napiecie.get(i) + "'";
					try (Connection conn = Stacja.polaczenie();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql)) {
						while (rs.next()) {
							if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
								s.add(rs.getString("nazwa"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}

		return s;
	}

	public static ArrayList<String> SelectPodsystem() {
		ArrayList<String> napiecie = Macierz.SelectPodsystem("napiecie");
		ArrayList<String> p = new ArrayList<String>();
		if(wyborNapiecia == "110"){
			for(int i = 0; i < napiecie.size(); i++){
				if(Double.parseDouble(napiecie.get(i)) > 80 && Double.parseDouble(napiecie.get(i)) < 130){
					String sql = "SELECT nazwa FROM PODSYSTEM WHERE napiecie = '" + napiecie.get(i) + "'";
					try (Connection conn = Stacja.polaczenie();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql)) {
						while (rs.next()) {
							if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
								p.add(rs.getString("nazwa"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		if(wyborNapiecia == "220"){
			for(int i = 0; i < napiecie.size(); i++){
				if(Double.parseDouble(napiecie.get(i)) > 180 && Double.parseDouble(napiecie.get(i)) < 250){
					String sql = "SELECT nazwa FROM PODSYSTEM WHERE napiecie = '" + napiecie.get(i) + "'";
					try (Connection conn = Stacja.polaczenie();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql)) {
						while (rs.next()) {
							if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
								p.add(rs.getString("nazwa"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		if(wyborNapiecia == "400"){
			for(int i = 0; i < napiecie.size(); i++){
				if(Double.parseDouble(napiecie.get(i)) > 350 && Double.parseDouble(napiecie.get(i)) < 450){
					String sql = "SELECT nazwa FROM PODSYSTEM WHERE napiecie = '" + napiecie.get(i) + "'";
					try (Connection conn = Stacja.polaczenie();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(sql)) {
						while (rs.next()) {
							if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
								p.add(rs.getString("nazwa"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}

		return p;
	}

	public static ArrayList<String> SelectTrafo() {
		String sql = "SELECT nazwa FROM TRANSFORMATOR WHERE napiecieStronaGorna = '" + wyborNapiecia
				+ "' OR napiecieStronaDolna = '" + wyborNapiecia + "'";
		ArrayList<String> s = new ArrayList<String>();
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				if (!rs.getString("nazwa").equals(getStacja1) && !rs.getString("nazwa").equals(getStacja2))
					s.add(rs.getString("nazwa"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}

	public static ArrayList<String> SelectTypPrzewodow() {
		String sql = "SELECT typPrzewodowOdgromowych FROM WartosciLinii WHERE TypSlupa = '" + wyborNapiecia
				+ "' AND RezystywnoscGruntu = '" + rezystywnosc + "' AND LiczbaTorow = '" + liczbaTorow + "'";
		ArrayList<String> s = new ArrayList<String>();
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				s.add(rs.getString("typPrzewodowOdgromowych"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}

	public static ArrayList<String> SelectWartosciLinii() {
		String sql = "SELECT R1, X1, R0, X0, R0m, X0m, B1, B0, B0m FROM WartosciLinii WHERE TypSlupa = '"
				+ wyborNapiecia + "' AND RezystywnoscGruntu = '" + rezystywnosc + "' AND TypPrzewodowOdgromowych = '"
				+ typPrzewodowOdgromowe + "' AND LiczbaTorow = '" + liczbaTorow + "'";
		ArrayList<String> s = new ArrayList<String>();
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			for (int i = 0; i < 9; i++) {
				s.add(rs.getString(i + 1));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}

}
