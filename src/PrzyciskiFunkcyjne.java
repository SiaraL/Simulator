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
		
		//Pzycisk obliczeñ 
		wybierzElementy = new Button("Napiêcia przed zwarciem");
		wybierzElementy.setMinWidth(150);
		wybierzElementy.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz napiêcia w wêz³ach SEE przed wystapieniem zwarcia w wêz³ach SEE i w wêŸle zwarcia")));
		wybierzElementy.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobiæ?")));
		wybierzElementy.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliNapiec("Napiêcia fazowe przed wyst¹pieniem zwarcia", "Nazwa stacji", WielkosciFazowe.NapieciaFazoweWezlowPrzedZwarciem());
		});
		
		//Pzycisk obliczeñ 
		obliczeniaButton = new Button("Napiêcia w czasie zwarcia");
		obliczeniaButton.setMinWidth(150);
		obliczeniaButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz jakie napiêcia wystapi¹ w wêz³ach SEE podczas zwarcia")));
		obliczeniaButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobiæ?")));
		obliczeniaButton.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliNapiec("Napiêcia fazowe w trakcie zwarcia", "Nazwa stacji", WielkosciFazowe.NapieciaFazoweWezlowWTrakcieZwarcia());
		});
		
		//Pzycisk obliczeñ 
		dobierzPrzekladniki = new Button("Oblicz pr¹d zwarcia");
		dobierzPrzekladniki.setMinWidth(150);
		dobierzPrzekladniki.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz pr¹d zwarcia w wêŸle k")));
		dobierzPrzekladniki.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobiæ?")));
		dobierzPrzekladniki.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliPradow("Pr¹dy podczas zwarcia w wêŸle k", "Pr¹d zwarcia", WielkosciFazowe.PradyFazoweWMiejscuZwarcia());
		});

		//Pzycisk obliczeñ 
		dobierzNastawy = new Button("Rozp³yw pr¹dów");
		dobierzNastawy.setMinWidth(150);
		dobierzNastawy.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Oblicz rozp³yw pr¹d w liniach")));
		dobierzNastawy.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobiæ?")));
		dobierzNastawy.setOnAction(e -> {
			Macierz.macierzLG();
			Tabele.wyswietlanieTabeliRozplywuPradow("Rozp³yw pr¹dów po wyst¹pieniu zwarcia", "Nazwa linii", WielkosciFazowe.PradyFazoweRozplyw());
		});
		
		//Pzycisk obliczeñ 
		usunWszystko = new Button("Wyczyœc bazê danych");
		usunWszystko.setMinWidth(150);
		usunWszystko.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Usuñ wszystkie elementy z bazy danych")));
		usunWszystko.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobiæ?")));
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
