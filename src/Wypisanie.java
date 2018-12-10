import java.util.ArrayList;

import flanagan.complex.ComplexMatrix;

public class Wypisanie {

	public static ComplexMatrix wypisz(ComplexMatrix x, String tekst){
		
		ComplexMatrix macierz = new ComplexMatrix(x.getNrow(), x.getNcol());
		System.out.println("\n" + tekst);
		for(int i = 0; i < x.getNrow(); i++){
			for(int j = 0; j < x.getNcol(); j++){
				System.out.print(x.getElementReference(i, j) + "   ");
			}
			System.out.println("");
		}
		return macierz;
	}
	
	public static ArrayList<String> wypiszNazwe(ArrayList<String> x, String tekst){
		ArrayList<String> macierz = new ArrayList<String>();
		System.out.println("\n" + tekst);
		for(int i = 0; i < x.size(); i++){
				System.out.print(x.get(i) + "\n");
		}
		return macierz;
	}

	public static ComplexMatrix wypiszModul(ComplexMatrix x, String tekst){
		
		ComplexMatrix macierz = new ComplexMatrix(x.getNrow(), x.getNcol());
		System.out.println("\n" + tekst);
		for(int i = 0; i < x.getNrow(); i++){
			for(int j = 0; j < x.getNcol(); j++){
				System.out.print(x.getElementReference(i, j).abs() + "   ");
			}
			System.out.println("");
		}
		return macierz;
	}

	public static ComplexMatrix wypiszKat(ComplexMatrix x, String tekst){
		
		ComplexMatrix macierz = new ComplexMatrix(x.getNrow(), x.getNcol());
		System.out.println("\n" + tekst);
		for(int i = 0; i < x.getNrow(); i++){
			for(int j = 0; j < x.getNcol(); j++){
				System.out.print(x.getElementReference(i, j).arg()*180/Math.PI + "   ");
			}
			System.out.println("");
		}
		return macierz;
	}
}
