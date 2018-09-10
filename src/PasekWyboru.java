import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PasekWyboru {
	
	static GridPane gridLayout;
	static Button stacjaButton, podsystemButton, liniaButton, zwarcieButton, trafoButton, odbiorButton;
	static Button obliczeniaButton;
	Button przypiszButton;

	public static GridPane przyciski(){
		gridLayout = new GridPane();
		
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		
		//PRzycisk stacji
		stacjaButton = new Button("Dodaj stacje");
		stacjaButton.setMinWidth(150);
		stacjaButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Dodaj stacj�/w�ze� systemowy")));
		stacjaButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		stacjaButton.setOnAction(e -> { 
			Stacja.okienkoStacja();
			OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Wprowad� parametry stacji"));
		});
		
		//Przycisk podsystemu
		podsystemButton = new Button("Dodaj podsystem");
		podsystemButton.setMinWidth(150);
		podsystemButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Dodaj podsystem")));
		podsystemButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		podsystemButton.setOnAction(e -> Podsystem.okienkoPodsystem());
		
		//Przycisk transformatora
		trafoButton = new Button("Dodaj transformator");
		trafoButton.setMinWidth(150);
		trafoButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Dodaj transformator")));
		trafoButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		trafoButton.setOnAction(e -> Transformator.okienkoTransformator());
		
		//Przycisk linii
		liniaButton = new Button("Dodaj lini�");
		liniaButton.setMinWidth(150);
		liniaButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Dodaj lini�")));
		liniaButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		liniaButton.setOnAction(e -> Linia.okienkoLinia());
		
		//Przycisk zwarcia
		zwarcieButton = new Button("Dodaj zwarcie");
		zwarcieButton.setMinWidth(150);
		zwarcieButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Dodaj zwarcie")));
		zwarcieButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		zwarcieButton.setOnAction(e -> Zwarcie.okienkoZwarcie());

		//Przycisk odbioru
		odbiorButton = new Button("Dodaj odbi�r");
		odbiorButton.setMinWidth(150);
		odbiorButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Dodaj odbi�r")));
		odbiorButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));
		odbiorButton.setOnAction(e -> Odbior.okienkoObdior());
		
//		//Pzycisk oblicze� 
//		obliczeniaButton = new Button("Wykonaj obliczenia");
//		obliczeniaButton.setMinWidth(150);
//		obliczeniaButton.setOnMouseEntered(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Wykonaj obliczenia")));
//		obliczeniaButton.setOnMouseExited(e -> OknoGlowne.borderPane.setBottom(PasekPomocy.pasek("Co chcesz zrobi�?")));

		
		//umieszczenie przycisk�w w 
		gridLayout.add(stacjaButton, 1, 1);
		gridLayout.add(podsystemButton, 1, 2);
		gridLayout.add(liniaButton, 1, 3);
		gridLayout.add(zwarcieButton, 1, 4);
		gridLayout.setMinWidth(170);
		gridLayout.setStyle("-fx-background-color: #1C4770");
		return gridLayout;
	}
	
}
