import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PrzyciskiFunkcyjne {

	static GridPane gridLayout;
	static Button obliczeniaButton, dobierzPrzekladniki, dobierzNastawy, wybierzElementy, usunWszystko;
	Button przypiszButton;

	public static GridPane przyciski(){
		gridLayout = new GridPane();
		
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		
		//Pzycisk oblicze� 
		wybierzElementy = new Button("Napi�cia przed zwarciem");
		wybierzElementy.setMinWidth(150);
		wybierzElementy.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz napi�cia w w�z�ach SEE przed wystapieniem zwarcia w w�z�ach SEE i w w�le zwarcia")));
		wybierzElementy.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		wybierzElementy.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliNapiec("Napi�cia fazowe przed wyst�pieniem zwarcia", "Nazwa stacji", WielkosciFazowe.NapieciaFazoweWezlowPrzedZwarciem());
		});
		
		//Pzycisk oblicze� 
		obliczeniaButton = new Button("Napi�cia w czasie zwarcia");
		obliczeniaButton.setMinWidth(150);
		obliczeniaButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz jakie napi�cia wystapi� w w�z�ach SEE podczas zwarcia")));
		obliczeniaButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		obliczeniaButton.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliNapiec("Napi�cia fazowe w trakcie zwarcia", "Nazwa stacji", WielkosciFazowe.NapieciaFazoweWezlowWTrakcieZwarcia());
		});
		
		//Pzycisk oblicze� 
		dobierzPrzekladniki = new Button("Oblicz pr�d zwarcia");
		dobierzPrzekladniki.setMinWidth(150);
		dobierzPrzekladniki.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz pr�d zwarcia w w�le k")));
		dobierzPrzekladniki.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		dobierzPrzekladniki.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliPradow("Pr�dy podczas zwarcia w w�le k", "Pr�d zwarcia", WielkosciFazowe.PradyFazoweWMiejscuZwarcia());
		});

		//Pzycisk oblicze� 
		dobierzNastawy = new Button("Rozp�yw pr�d�w");
		dobierzNastawy.setMinWidth(150);
		dobierzNastawy.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz rozp�yw pr�d w liniach")));
		dobierzNastawy.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		dobierzNastawy.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliRozplywuPradow("Rozp�yw pr�d�w po wyst�pieniu zwarcia", "Nazwa linii", WielkosciFazowe.PradyFazoweRozplyw());
		});
		
		//Pzycisk oblicze� 
		usunWszystko = new Button("Wyczy�c baz� danych");
		usunWszystko.setMinWidth(150);
		usunWszystko.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Usu� wszystkie elementy z bazy danych")));
		usunWszystko.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		usunWszystko.setOnAction(e -> UsunWszystko.usun());
		
		gridLayout.add(wybierzElementy, 1, 1);
		gridLayout.add(obliczeniaButton, 1, 2);
		gridLayout.add(dobierzPrzekladniki, 1, 3);
		gridLayout.add(dobierzNastawy, 1, 4);
		gridLayout.add(usunWszystko, 1, 6);
		gridLayout.setMinWidth(170);
		gridLayout.setStyle("-fx-background-color: #1C4770");
		return gridLayout;
	}
}
