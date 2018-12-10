import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PrzyciskiFunkcyjne {

	static GridPane gridLayout;
	static Button obliczeniaButton, dobierzPrzekladniki, dobierzNastawy, wybierzElementy, usunWszystko, fazowe, RMS;
	Button przypiszButton;
	static String rodzaj = "Wielko�ci fazowe", rodzajRMS = "RMS";
	
	public static GridPane przyciski(){
		gridLayout = new GridPane();
		
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		
		//Pzycisk oblicze� 
		wybierzElementy = new Button("Napi�cia przed zwarciem");
		wybierzElementy.setMinWidth(170);
		wybierzElementy.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz napi�cia w w�z�ach SEE przed wystapieniem zwarcia w w�z�ach SEE i w w�le zwarcia")));
		wybierzElementy.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		wybierzElementy.setOnAction(e -> {
			Wypisanie.wypisz(Macierz.macierzAdmitancyjna("1").inverse(), "macierz Z");
			Wypisanie.wypisz(Macierz.macierzSkladowychPradow(), "prad");
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliNapiec("Napi�cia fazowe przed wyst�pieniem zwarcia", "Nazwa stacji", WielkosciFazowe.NapieciaFazoweWezlowPrzedZwarciem());
		});
		
		//Pzycisk oblicze� 
		obliczeniaButton = new Button("Napi�cia w czasie zwarcia");
		obliczeniaButton.setMinWidth(170);
		obliczeniaButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz jakie napi�cia wystapi� w w�z�ach SEE podczas zwarcia")));
		obliczeniaButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		obliczeniaButton.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliNapiec("Napi�cia fazowe w trakcie zwarcia", "Nazwa stacji", WielkosciFazowe.NapieciaFazoweWezlowWTrakcieZwarcia());
		});
		
		//Pzycisk oblicze� 
		dobierzPrzekladniki = new Button("Oblicz pr�d zwarcia");
		dobierzPrzekladniki.setMinWidth(170);
		dobierzPrzekladniki.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz pr�d zwarcia w w�le k")));
		dobierzPrzekladniki.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		dobierzPrzekladniki.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliPradow("Pr�dy podczas zwarcia w w�le k", "Pr�d zwarcia", WielkosciFazowe.PradyFazoweWMiejscuZwarcia());
		});

		//Pzycisk oblicze� 
		dobierzNastawy = new Button("Rozp�yw pr�d�w");
		dobierzNastawy.setMinWidth(170);
		dobierzNastawy.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz rozp�yw pr�d w liniach")));
		dobierzNastawy.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		dobierzNastawy.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliRozplywuPradow("Rozp�yw pr�d�w po wyst�pieniu zwarcia", "Nazwa linii", WielkosciFazowe.PradyFazoweRozplyw());
		});
		
		//Pzycisk oblicze� 
		usunWszystko = new Button("Wyczy�c baz� danych");
		usunWszystko.setMinWidth(170);
		usunWszystko.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Usu� wszystkie elementy z bazy danych")));
		usunWszystko.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		usunWszystko.setOnAction(e -> UsunWszystko.usun());
		

		Label wyswietlanie = new Label("Wy�wietlane wielko�ci:");
		wyswietlanie.setStyle("-fx-background-color: #BBBBBB");
		fazowe = new Button();
		fazowe.setText(rodzaj);
		fazowe.setMinWidth(170);
		fazowe.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Usu� wszystkie elementy z bazy danych")));
		fazowe.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		fazowe.setOnAction(e -> {
			if(Macierz.fazowe == 1){
				Macierz.fazowe = Math.sqrt(3);
				rodzaj = "Wielko�ci mi�dzyfazowe";
				fazowe.setText(rodzaj);
			}
			else {
				Macierz.fazowe = 1;
				rodzaj = "Wielko�ci fazowe";
				fazowe.setText(rodzaj);
			}
		});
		
		RMS = new Button();
		RMS.setText(rodzajRMS);
		RMS.setMinWidth(170);
		RMS.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Usu� wszystkie elementy z bazy danych")));
		RMS.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		RMS.setOnAction(e -> {
			if(Macierz.RMS == 1){
				Macierz.RMS = Math.sqrt(2);
				rodzajRMS = "Warto�ci szytowe";
				RMS.setText(rodzajRMS);
			}
			else {
				Macierz.RMS = 1;
				rodzajRMS = "RMS";
				RMS.setText(rodzajRMS);
			}
		});
		
		
		gridLayout.add(wybierzElementy, 1, 1);
		gridLayout.add(obliczeniaButton, 1, 2);
		gridLayout.add(dobierzPrzekladniki, 1, 3);
		gridLayout.add(dobierzNastawy, 1, 4);
		gridLayout.add(usunWszystko, 1, 6);
		gridLayout.add(fazowe, 1, 9);
		gridLayout.add(RMS, 1, 10);
		gridLayout.add(wyswietlanie, 1, 8);
		gridLayout.setMinWidth(190);
		gridLayout.setStyle("-fx-background-color: #1C4770");
		return gridLayout;
	}
}
