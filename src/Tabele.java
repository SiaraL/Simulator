import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import flanagan.complex.ComplexMatrix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tabele {

	static Stage window;
	static TableView<WierszTabeli> tabela;
	
	public static void wyswietlanieTabeliNapiec(String tytulOkna, String nazwaObiektu, ComplexMatrix x){
		window = new Stage();
		window.setTitle(tytulOkna);
		
		TableColumn<WierszTabeli, String> nazwa = new TableColumn<>(nazwaObiektu);
		nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
		TableColumn<WierszTabeli, Double> L1real = new TableColumn<>("   UL1\nRe[kV]");
		L1real.setCellValueFactory(new PropertyValueFactory<>("realL1"));
		TableColumn<WierszTabeli, Double> L1imag = new TableColumn<>("   UL1\nIm[kV]");
		L1imag.setCellValueFactory(new PropertyValueFactory<>("imagL1"));
		TableColumn<WierszTabeli, Double> L1modul = new TableColumn<>("|UL1|\n [kV]");
		L1modul.setCellValueFactory(new PropertyValueFactory<>("modulL1"));
		TableColumn<WierszTabeli, Double> L1arg = new TableColumn<>("arg(UL1)\n  [deg]");
		L1arg.setCellValueFactory(new PropertyValueFactory<>("argL1"));
		TableColumn<WierszTabeli, Double> L2real = new TableColumn<>("   UL2\nRe[kV]");
		L2real.setCellValueFactory(new PropertyValueFactory<>("realL2"));
		TableColumn<WierszTabeli, Double> L2imag = new TableColumn<>("   UL2\nIm[kV]");
		L2imag.setCellValueFactory(new PropertyValueFactory<>("imagL2"));
		TableColumn<WierszTabeli, Double> L2modul = new TableColumn<>("|UL2|\n [kV]");
		L2modul.setCellValueFactory(new PropertyValueFactory<>("modulL2"));
		TableColumn<WierszTabeli, Double> L2arg = new TableColumn<>("arg(UL2)\n  [deg]");
		L2arg.setCellValueFactory(new PropertyValueFactory<>("argL2"));
		TableColumn<WierszTabeli, Double> L3real = new TableColumn<>("   UL3\nRe[kV]");
		L3real.setCellValueFactory(new PropertyValueFactory<>("realL3"));
		TableColumn<WierszTabeli, Double> L3imag = new TableColumn<>("   UL3\nIm[kV]");
		L3imag.setCellValueFactory(new PropertyValueFactory<>("imagL3"));
		TableColumn<WierszTabeli, Double> L3modul = new TableColumn<>("|UL3|\n [kV]");
		L3modul.setCellValueFactory(new PropertyValueFactory<>("modulL3"));
		TableColumn<WierszTabeli, Double> L3arg = new TableColumn<>("arg(UL3)\n  [deg]");
		L3arg.setCellValueFactory(new PropertyValueFactory<>("argL3"));
		
		tabela = new TableView<>();
		tabela.setItems(tabelkaNapiecia(x));
		tabela.getColumns().add(nazwa);
		tabela.getColumns().add(L1real);
		tabela.getColumns().add(L1imag);
		tabela.getColumns().add(L1modul);
		tabela.getColumns().add(L1arg);
		tabela.getColumns().add(L2real);
		tabela.getColumns().add(L2imag);
		tabela.getColumns().add(L2modul);
		tabela.getColumns().add(L2arg);
		tabela.getColumns().add(L3real);
		tabela.getColumns().add(L3imag);
		tabela.getColumns().add(L3modul);
		tabela.getColumns().add(L3arg);
		
		VBox V = new VBox();
		V.getChildren().add(tabela);
		
		Scene scene = new Scene(V);
		window.setScene(scene);
		window.show();
		
		
	}
	
	public static ObservableList<WierszTabeli> tabelkaNapiecia(ComplexMatrix x){
		ObservableList<WierszTabeli> wiersz = FXCollections.observableArrayList();
		for(int i = 0; i < x.getNrow()-1; i++){
			wiersz.add(new WierszTabeli(Macierz.SelectStacja("nazwa").get(i),
					x(x.getElementReference(i, 0).getReal()*Macierz.fazowe),
					x(x.getElementReference(i, 0).getImag()*Macierz.fazowe),
					x(x.getElementReference(i, 0).abs()*Macierz.fazowe),
					x(x.getElementReference(i, 0).arg()*180/Math.PI),
					x(x.getElementReference(i, 1).getReal()*Macierz.fazowe),
					x(x.getElementReference(i, 1).getImag()*Macierz.fazowe),
					x(x.getElementReference(i, 1).abs()*Macierz.fazowe),
					x(x.getElementReference(i, 1).arg()*180/Math.PI),
					x(x.getElementReference(i, 2).getReal()*Macierz.fazowe),
					x(x.getElementReference(i, 2).getImag()*Macierz.fazowe),
					x(x.getElementReference(i, 2).abs()*Macierz.fazowe),
					x(x.getElementReference(i, 2).arg()*180/Math.PI)));
		}
		wiersz.add(new WierszTabeli("w. zwarcia k",
				x(x.getElementReference(x.getNrow()-1, 0).getReal()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 0).getImag()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 0).abs()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 0).arg()*180/Math.PI),
				x(x.getElementReference(x.getNrow()-1, 1).getReal()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 1).getImag()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 1).abs()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 1).arg()*180/Math.PI),
				x(x.getElementReference(x.getNrow()-1, 2).getReal()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 2).getImag()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 2).abs()*Macierz.fazowe),
				x(x.getElementReference(x.getNrow()-1, 2).arg()*180/Math.PI)));
		
		return wiersz;
	}
	
	public static void wyswietlanieTabeliPradow(String tytulOkna, String nazwaObiektu, ComplexMatrix x){
		window = new Stage();
		window.setTitle(tytulOkna);
		
		TableColumn<WierszTabeli, String> nazwa = new TableColumn<>(nazwaObiektu);
		nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
		TableColumn<WierszTabeli, Double> L1real = new TableColumn<>("   IL1\nRe[kA]");
		L1real.setCellValueFactory(new PropertyValueFactory<>("realL1"));
		TableColumn<WierszTabeli, Double> L1imag = new TableColumn<>("   IL1\nIm[kA]");
		L1imag.setCellValueFactory(new PropertyValueFactory<>("imagL1"));
		TableColumn<WierszTabeli, Double> L1modul = new TableColumn<>("|IL1|\n[kA]");
		L1modul.setCellValueFactory(new PropertyValueFactory<>("modulL1"));
		TableColumn<WierszTabeli, Double> L1arg = new TableColumn<>("arg(I L1)\n  [deg]");
		L1arg.setCellValueFactory(new PropertyValueFactory<>("argL1"));
		TableColumn<WierszTabeli, Double> L2real = new TableColumn<>("   IL2\nRe[kA]");
		L2real.setCellValueFactory(new PropertyValueFactory<>("realL2"));
		TableColumn<WierszTabeli, Double> L2imag = new TableColumn<>("   IL2\nIm[kA]");
		L2imag.setCellValueFactory(new PropertyValueFactory<>("imagL2"));
		TableColumn<WierszTabeli, Double> L2modul = new TableColumn<>("|IL2|\n[kA]");
		L2modul.setCellValueFactory(new PropertyValueFactory<>("modulL2"));
		TableColumn<WierszTabeli, Double> L2arg = new TableColumn<>("arg(IL2)\n  [deg]");
		L2arg.setCellValueFactory(new PropertyValueFactory<>("argL2"));
		TableColumn<WierszTabeli, Double> L3real = new TableColumn<>("   IL3\nRe[kA]");
		L3real.setCellValueFactory(new PropertyValueFactory<>("realL3"));
		TableColumn<WierszTabeli, Double> L3imag = new TableColumn<>("   IL3\nIm[kA]");
		L3imag.setCellValueFactory(new PropertyValueFactory<>("imagL3"));
		TableColumn<WierszTabeli, Double> L3modul = new TableColumn<>("|IL3|\n[kA]");
		L3modul.setCellValueFactory(new PropertyValueFactory<>("modulL3"));
		TableColumn<WierszTabeli, Double> L3arg = new TableColumn<>("arg(IL3)\n  [deg]");
		L3arg.setCellValueFactory(new PropertyValueFactory<>("argL3"));
		
		tabela = new TableView<>();
		tabela.setItems(tabelkaPrady(x));
		tabela.getColumns().add(nazwa);
		tabela.getColumns().add(L1real);
		tabela.getColumns().add(L1imag);
		tabela.getColumns().add(L1modul);
		tabela.getColumns().add(L1arg);
		tabela.getColumns().add(L2real);
		tabela.getColumns().add(L2imag);
		tabela.getColumns().add(L2modul);
		tabela.getColumns().add(L2arg);
		tabela.getColumns().add(L3real);
		tabela.getColumns().add(L3imag);
		tabela.getColumns().add(L3modul);
		tabela.getColumns().add(L3arg);
		
		VBox V = new VBox();
		V.getChildren().add(tabela);
		
		Scene scene = new Scene(V);
		window.setScene(scene);
		window.show();
		
		
	}
	
	public static ObservableList<WierszTabeli> tabelkaPrady(ComplexMatrix x){
		ObservableList<WierszTabeli> wiersz = FXCollections.observableArrayList();
			wiersz.add(new WierszTabeli("Pr¹d zwarcia",
					x(x.getElementReference(0, 0).getReal()),
					x(x.getElementReference(0, 0).getImag()),
					x(x.getElementReference(0, 0).abs()),
					x(x.getElementReference(0, 0).arg()*180/Math.PI),
					x(x.getElementReference(1, 0).getReal()),
					x(x.getElementReference(1, 0).getImag()),
					x(x.getElementReference(1, 0).abs()),
					x(x.getElementReference(1, 0).arg()*180/Math.PI),
					x(x.getElementReference(2, 0).getReal()),
					x(x.getElementReference(2, 0).getImag()),
					x(x.getElementReference(2, 0).abs()),
					x(x.getElementReference(2, 0).arg()*180/Math.PI)));
	
		return wiersz;
	}
	
	public static void wyswietlanieTabeliRozplywuPradow(String tytulOkna, String nazwaObiektu, ComplexMatrix x){
		window = new Stage();
		window.setTitle(tytulOkna);
		
		TableColumn<WierszTabeli, String> nazwa = new TableColumn<>(nazwaObiektu);
		nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
		TableColumn<WierszTabeli, Double> L1real = new TableColumn<>("   IL1\nRe[kA]");
		L1real.setCellValueFactory(new PropertyValueFactory<>("realL1"));
		TableColumn<WierszTabeli, Double> L1imag = new TableColumn<>("   IL1\nIm[kA]");
		L1imag.setCellValueFactory(new PropertyValueFactory<>("imagL1"));
		TableColumn<WierszTabeli, Double> L1modul = new TableColumn<>("|IL1|\n[kA]");
		L1modul.setCellValueFactory(new PropertyValueFactory<>("modulL1"));
		TableColumn<WierszTabeli, Double> L1arg = new TableColumn<>("arg(IL1)\n  [deg]");
		L1arg.setCellValueFactory(new PropertyValueFactory<>("argL1"));
		TableColumn<WierszTabeli, Double> L2real = new TableColumn<>("   IL2\nRe[kA]");
		L2real.setCellValueFactory(new PropertyValueFactory<>("realL2"));
		TableColumn<WierszTabeli, Double> L2imag = new TableColumn<>("   IL2\nIm[kA]");
		L2imag.setCellValueFactory(new PropertyValueFactory<>("imagL2"));
		TableColumn<WierszTabeli, Double> L2modul = new TableColumn<>("|IL2|\n[kA]");
		L2modul.setCellValueFactory(new PropertyValueFactory<>("modulL2"));
		TableColumn<WierszTabeli, Double> L2arg = new TableColumn<>("arg(IL2)\n  [deg]");
		L2arg.setCellValueFactory(new PropertyValueFactory<>("argL2"));
		TableColumn<WierszTabeli, Double> L3real = new TableColumn<>("   IL3\nRe[kA]");
		L3real.setCellValueFactory(new PropertyValueFactory<>("realL3"));
		TableColumn<WierszTabeli, Double> L3imag = new TableColumn<>("   IL3\nIm[kA]");
		L3imag.setCellValueFactory(new PropertyValueFactory<>("imagL3"));
		TableColumn<WierszTabeli, Double> L3modul = new TableColumn<>("|IL3|\n[kA]");
		L3modul.setCellValueFactory(new PropertyValueFactory<>("modulL3"));
		TableColumn<WierszTabeli, Double> L3arg = new TableColumn<>("arg(IL3)\n  [deg]");
		L3arg.setCellValueFactory(new PropertyValueFactory<>("argL3"));
		
		tabela = new TableView<>();
		tabela.setItems(tabelkaRozplywPradow(x));
		tabela.getColumns().add(nazwa);
		tabela.getColumns().add(L1real);
		tabela.getColumns().add(L1imag);
		tabela.getColumns().add(L1modul);
		tabela.getColumns().add(L1arg);
		tabela.getColumns().add(L2real);
		tabela.getColumns().add(L2imag);
		tabela.getColumns().add(L2modul);
		tabela.getColumns().add(L2arg);
		tabela.getColumns().add(L3real);
		tabela.getColumns().add(L3imag);
		tabela.getColumns().add(L3modul);
		tabela.getColumns().add(L3arg);
		
		VBox V = new VBox();
		V.getChildren().add(tabela);
		
		Scene scene = new Scene(V);
		window.setScene(scene);
		window.show();
		
		
	}
	
	public static ObservableList<WierszTabeli> tabelkaRozplywPradow(ComplexMatrix x){
		ObservableList<WierszTabeli> wiersz = FXCollections.observableArrayList();
		ArrayList<String> nazwaLinii = RozplywPradow.SelectLinia("nazwa");
		for(int i = 0; i < x.getNrow()-1; i++){
			String nazwaL = nazwaLinii.get(i);
			if(i == RozplywPradow.numerLiniiZwarcia){
				nazwaL = "linia zwarcia cz.1";
			}
			wiersz.add(new WierszTabeli(nazwaL,
					x(x.getElementReference(i, 0).getReal()),
					x(x.getElementReference(i, 0).getImag()),
					x(x.getElementReference(i, 0).abs()),
					x(x.getElementReference(i, 0).arg()*180/Math.PI),
					x(x.getElementReference(i, 1).getReal()),
					x(x.getElementReference(i, 1).getImag()),
					x(x.getElementReference(i, 1).abs()),
					x(x.getElementReference(i, 1).arg()*180/Math.PI),
					x(x.getElementReference(i, 2).getReal()),
					x(x.getElementReference(i, 2).getImag()),
					x(x.getElementReference(i, 2).abs()),
					x(x.getElementReference(i, 2).arg()*180/Math.PI)));
		}
		wiersz.add(new WierszTabeli("linia zwarcia cz.2",
				x(x.getElementReference(x.getNrow()-1, 0).getReal()),
				x(x.getElementReference(x.getNrow()-1, 0).getImag()),
				x(x.getElementReference(x.getNrow()-1, 0).abs()),
				x(x.getElementReference(x.getNrow()-1, 0).arg()*180/Math.PI),
				x(x.getElementReference(x.getNrow()-1, 1).getReal()),
				x(x.getElementReference(x.getNrow()-1, 1).getImag()),
				x(x.getElementReference(x.getNrow()-1, 1).abs()),
				x(x.getElementReference(x.getNrow()-1, 1).arg()*180/Math.PI),
				x(x.getElementReference(x.getNrow()-1, 2).getReal()),
				x(x.getElementReference(x.getNrow()-1, 2).getImag()),
				x(x.getElementReference(x.getNrow()-1, 2).abs()),
				x(x.getElementReference(x.getNrow()-1, 2).arg()*180/Math.PI)));
		return wiersz;
	}
	
	public static double x(double y) {
		DecimalFormatSymbols kropka = new DecimalFormatSymbols();
		kropka.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("#.####", kropka);
		String f = (df.format(y));
		double c = Double.parseDouble(f);
		return c;
	}
	
}
