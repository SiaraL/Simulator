import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsunWszystko {

	public static void usun(){
		Ostrzezenie.okienko("Uwaga", "Czy na pewno chcesz usun¹æ wszystkie elementy z bazy danych?");
		if(Ostrzezenie.odpowiedz == true){
			Ostrzezenie.okienko("Uwaga", "Tego procesu nie da siê odwróciæ, czy na pewno chcesz usun¹æ wszystkie elementy z bazy danych?");
			if(Ostrzezenie.odpowiedz == true){
				delete();
				OknoPodgladu.odswiezenieDrzewa();
			}
		}
	}
	
	public static void delete(){
		ArrayList<String> tablica = new ArrayList<String>();
		tablica.add("STACJA");
		tablica.add("PODSYSTEM");
		tablica.add("TRANSFORMATOR");
		tablica.add("LINIA");
		tablica.add("ODBIOR");
		tablica.add("ZWARCIE");
		for(int i = 0; i < tablica.size(); i++){
			String sql = "DELETE FROM " + tablica.get(i);
			try(Connection conn = Stacja.polaczenie();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql)){				
			} catch(SQLException e){
					System.out.println(e.getMessage());
			}
		}
	}

}
