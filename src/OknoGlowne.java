import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OknoGlowne extends Application{

	static public Stage window;
	static BorderPane borderPane;
	Scene scene;	
	Canvas canvas;
	Group grupa;
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		window.setTitle("Symulator obliczeñ zwarciowych");
		window.setOnCloseRequest(e -> {
			e.consume();
			zamknijProgram();
		});
		borderPane = new BorderPane();
		borderPane.setLeft(PasekWyboru.przyciski());
		borderPane.setRight(PasekPodgladu.podglad());
		borderPane.setBottom(PasekPomocy.pasek("Witaj w Symulatorze obliczeñ zwarciowych. Co chcesz zrobiæ?"));
		borderPane.setCenter(PrzyciskiFunkcyjne.przyciski());
		scene = new Scene(borderPane);
		window.setScene(scene);
		window.show();
		
	}
	
	public void zamknijProgram(){
		Boolean odpowiedz = Ostrzezenie.okienko("Uwaga", "Jesteœ pewien, ¿e chcesz zamkn¹æ program?");
		if(odpowiedz) window.close();
	}

}
