import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class OknoPodgladu{

	static Stage window;
	static Scene scene;
	
	public static void okienko(TreeItem<String> nazwa){
		window = new Stage();
		window.setTitle(nazwa.getParent().getValue());
		window.initModality(Modality.APPLICATION_MODAL);
		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(10,10,10,10));
		ArrayList<String> a = null;
		
		if(!nazwa.getParent().getValue().equals("STACJA") || 
		!nazwa.getParent().getValue().equals("PODSYSTEM") || 
		!nazwa.getParent().getValue().equals("LINIA") || 
		!nazwa.getParent().getValue().equals("ZWARCIE")){
			a = select(nazwa.getValue(), nazwa.getParent().getValue());
		}
		
		int k = 0;
		for(int i = 0; i < a.size(); i++){
			Label label = new Label(a.get(i));
			gridPane.add(label, 1, k);
			i++;
			Label label2 = new Label(a.get(i));
			gridPane.add(label2, 2, k);
			k++;
		}
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10,10,10,10));
		hbox.setSpacing(10);

		Button usun = new Button("Usuñ");
		usun.setMinWidth(65);
		usun.setOnAction(e -> {
			Ostrzezenie.okienko("Ugaga", "Czy na pewno chcesz usun¹æ obiekt?");
			if(Ostrzezenie.odpowiedz == true){
				delete(nazwa.getValue(), nazwa.getParent().getValue());
				window.close();
				odswiezenieDrzewa();
			} else{
				Ostrzezenie.window.close();
			}
			
			
		});
		Button ok = new Button("OK");
		ok.setMinWidth(65);
		ok.setOnAction(e -> window.close());
		hbox.getChildren().addAll(ok, usun);
		hbox.centerShapeProperty();
		VBox vbox = new VBox();
		vbox.getChildren().addAll(gridPane, hbox);
		
		scene = new Scene(vbox);
		window.setScene(scene);
		window.show();
	}
	
	public static ArrayList<String> select(String rekord, String nazwaTablicy){
		String sql = "SELECT * FROM " + nazwaTablicy + " WHERE nazwa = '" + rekord + "'";
		ArrayList<String> s = new ArrayList<String>();
		try(Connection conn = Stacja.polaczenie();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){

			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 2; i <= rsmd.getColumnCount(); i++){
				s.add(rsmd.getColumnName(i)+ ": ");
				s.add(rs.getString(i));
			}
			
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return s;
	}
	
	public static void delete(String rekord, String nazwaTablicy){
		String sql = "DELETE FROM " + nazwaTablicy + " WHERE nazwa = '" + rekord + "'";
		try(Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){				
		} catch(SQLException e){
				System.out.println(e.getMessage());
		}
	}
	
	public static void odswiezenieDrzewa(){
		PasekPodgladu.lista.refresh();
		OknoGlowne.borderPane.setRight(null);
		OknoGlowne.borderPane.setRight(PasekPodgladu.podglad());
	}
		
	
}
