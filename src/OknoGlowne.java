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
		window.setTitle("Symulator oblicze� zwarciowych");
		window.setOnCloseRequest(e -> {
			e.consume();
			zamknijProgram();
		});
		borderPane = new BorderPane();
		borderPane.setLeft(PasekWyboru.przyciski());
		borderPane.setRight(PasekPodgladu.podglad());
		borderPane.setBottom(PasekPomocy.pasek("Witaj w Symulatorze oblicze� zwarciowych. Co chcesz zrobi�?"));
		borderPane.setCenter(PrzyciskiFunkcyjne.przyciski());
		scene = new Scene(borderPane);
		window.setScene(scene);
		window.show();
		
	}
	
	public void zamknijProgram(){
		Boolean odpowiedz = Ostrzezenie.okienko("Uwaga", "Jeste� pewien, �e chcesz zamkn�� program?");
		if(odpowiedz) window.close();
	}

}
