import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class PasekPodgladu {
	
	static TreeView<String> lista;
	static StackPane uklad;
	static TreeItem<String> root, stacja, podsystem, transformator, linia, odbior, zwarcie;

	public static StackPane podglad(){
		
		uklad = new StackPane();
		
		//Root
		root = new TreeItem<>();
		root.setExpanded(true);
		
		//stacja
		stacja = dodajGalaz("STACJA", root);
		dodajPodgalaz(Select("STACJA"), stacja);
		
		//podsystem
		podsystem = dodajGalaz("PODSYSTEM", root);
		dodajPodgalaz(Select("PODSYSTEM"), podsystem);
		
		//stacja
//		transformator = dodajGalaz("TRANSFORMATOR", root);
//		dodajPodgalaz(Select("TRANSFORMATOR"), transformator);
				
		//linia
		linia = dodajGalaz("LINIA", root);
		dodajPodgalaz(Select("LINIA"), linia);
				
		//odbior
//		odbior = dodajGalaz("ODBIOR", root);
//		dodajPodgalaz(Select("ODBIOR"), odbior);
		//odbior
		zwarcie = dodajGalaz("ZWARCIE", root);
		dodajPodgalaz(Select("ZWARCIE"), zwarcie);
		
		lista = new TreeView<String>(root);
		lista.setShowRoot(false);
		lista.setOnMousePressed(e -> {
			if(e.getClickCount()==2 && !e.isConsumed()){
				e.consume();
				TreeItem<String> item = lista.getSelectionModel().getSelectedItem();
				OknoPodgladu.okienko(item);
			}
			
		});
		uklad.getChildren().add(lista);
		return uklad;
	}
	
	
	public static ArrayList<String> Select(String typ){
		String sql = "SELECT nazwa FROM " + typ;
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

	public static void dodajPodgalaz(ArrayList<String> tablica, TreeItem<String> rodzic){
		String nazwa;
		for(int i = 0; i < tablica.size(); i++){
			nazwa = tablica.get(i);
			dodajGalaz(nazwa, rodzic);
		}
	}
	
	public static TreeItem<String> dodajGalaz(String nazwa, TreeItem<String> rodzic){
		TreeItem<String> galaz = new TreeItem<String>(nazwa);
		galaz.setExpanded(false);
		rodzic.getChildren().add(galaz);
		return galaz;
	}
	
}
