import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import flanagan.complex.Complex;
import flanagan.complex.ComplexMatrix;

public class RozplywPradow{

	static int numerLiniiZwarcia = -1;
	
	public static ComplexMatrix rozplywPradow1(){
		ComplexMatrix rozplywpradow = new ComplexMatrix(roznicaNapiec1().getNrow(), 1);
		ComplexMatrix roznicanapiec = roznicaNapiec1();
		ArrayList<String> rodzaj = SelectLinia("rodzaj");
		ArrayList<String> R1_ = SelectLinia("R1");
		ArrayList<String> X1_ = SelectLinia("X1");
		ArrayList<String> dlugosc_ = SelectLinia("dlugosc");
		ArrayList<String> koniec1 = SelectLinia("koniec1");
		ArrayList<String> koniec2 = SelectLinia("koniec2");
		ArrayList<String> nazwa = SelectLinia("nazwa");
		dlugosc_.add("0");
		R1_.add("0");
		X1_.add("0");
		rodzaj.add("");
		nazwa.add("");
		ArrayList<String> zwarcie = Macierz.SelectZwarcie();
		int a = roznicanapiec.getNrow()-1;
		double Rkoniec = 0, Xkoniec = 0; 
		String rodzajZwarcia = "";
		for(int i = 0; i < rozplywpradow.getNrow(); i++){
			double dlugosc = Double.parseDouble(dlugosc_.get(i));
			double R1 = Double.parseDouble(R1_.get(i))*dlugosc;
			double X1 = Double.parseDouble(X1_.get(i))*dlugosc;
			String stacjaOdniesienia = zwarcie.get(3);
			double odleglosc = Double.parseDouble(zwarcie.get(4));
			Complex Z = null;
			Complex U = null;
			if(i == numerLiniiZwarcia && koniec1.get(i).equals(stacjaOdniesienia)){
				Rkoniec = R1/dlugosc*(dlugosc-odleglosc);
				Xkoniec = X1/dlugosc*(dlugosc-odleglosc);
				R1 = R1/dlugosc*odleglosc;
				X1 = X1/dlugosc*odleglosc;
				rodzajZwarcia = rodzaj.get(i);
			}
			if(i == numerLiniiZwarcia && koniec2.get(i).equals(stacjaOdniesienia)){
				Rkoniec = R1/dlugosc*odleglosc;
				Xkoniec = X1/dlugosc*odleglosc;
				R1 = R1/dlugosc*(dlugosc-odleglosc);
				X1 = X1/dlugosc*(dlugosc-odleglosc);
				rodzajZwarcia = rodzaj.get(i);
			}
			if(i == a){
				R1 = Rkoniec;
				X1 = Xkoniec;
				rodzaj.set(a, rodzajZwarcia);
			}
			if(rodzaj.get(i).equals("jednotorowa")){
				Z = new Complex(R1, X1);
			}
			if(rodzaj.get(i).equals("dwutorowa")){
				Z = new Complex(R1/2, X1/2);
			}
//			System.out.println("stacja odniesienia: " + stacjaOdniesienia +"; numer: " + numerLiniiZwarcia  + "; koniec1: " + koniec1.get(i)+ "; koniec2: " + koniec2.get(i) + "; Rkoniec: " + Rkoniec + "; Xkoniec:" + Xkoniec);
			U = roznicanapiec.getElementReference(i, 0);
			Complex I = Complex.over(U, Z);
			
			rozplywpradow.setElement(i, 0, I.getReal(), I.getImag());
		}
		return rozplywpradow;
	}
		
	public static ComplexMatrix roznicaNapiec1(){
		ArrayList<String> linia = Zwarcie.SelectLinia();
		ArrayList<String> koniec1 = SelectLinia("koniec1");
		ArrayList<String> koniec2 = SelectLinia("koniec2");
		ArrayList<String> liniaNazwa = SelectLinia("nazwa");
		ArrayList<String> napiecie = Macierz.SelectPodsystem("napiecie");
		ArrayList<String> kat = Macierz.SelectPodsystem("przesuniecieKatowe");
		ArrayList<String> nazwa = Macierz.SelectPodsystem("nazwa");
		ArrayList<String> stacja = Macierz.SelectStacja("nazwa");
		String liniaZwarcia = Macierz.SelectZwarcie().get(2);
		for(int i = 0; i < linia.size(); i++){
			if(liniaNazwa.get(i).equals(liniaZwarcia)){
				numerLiniiZwarcia = i;
			}
		}
		Wypisanie.wypiszNazwe(kat, "kat");
		Wypisanie.wypiszNazwe(napiecie, "napiecie");
		Wypisanie.wypiszNazwe(nazwa, "nazwa");
		ComplexMatrix napieciePoZwarciu = Macierz.napieciaPoZwarciu1();
		ComplexMatrix wektorRoznicyNapiec = new ComplexMatrix(linia.size()+1, 1);
		ComplexMatrix wektorNapiec1 = new ComplexMatrix(linia.size()+1, 1);
		ComplexMatrix wektorNapiec2 = new ComplexMatrix(linia.size()+1, 1);
		Wypisanie.wypiszNazwe(nazwa, "nazwa");
		Wypisanie.wypiszNazwe(koniec1, "koniec 1");
		Wypisanie.wypiszNazwe(koniec2, "koniec 2");
		Wypisanie.wypiszModul(napieciePoZwarciu, "napiecie po zwarciu");
		for(int i = 0; i < linia.size(); i++){
			int licznikKoniec1 = -1, licznikKoniec2 = -1;
			for(int j = 0; j< stacja.size(); j++){
				if(numerLiniiZwarcia == i){
					wektorNapiec2.setElement(i, 0, napieciePoZwarciu.getElementReference(stacja.size(), 0));
					wektorNapiec2.setElement(linia.size(), 0, napieciePoZwarciu.getElementReference(stacja.size(), 0));
					int numerKoniec1 = -1, numerKoniec2 = -1;
					for(int k = 0; k < stacja.size(); k++){
						if(koniec1.get(i).equals(stacja.get(k))){
							numerKoniec1 = k;
							
						}
						if(koniec2.get(i).equals(stacja.get(k))){
							numerKoniec2 = k;
							
						}
					}
					wektorNapiec1.setElement(i, 0, napieciePoZwarciu.getElementReference(numerKoniec1, 0));
					wektorNapiec1.setElement(linia.size(), 0, napieciePoZwarciu.getElementReference(numerKoniec2, 0));
					break;
				}
				if(stacja.get(j).equals(koniec1.get(i))){
					licznikKoniec1 = j;
				}
				if(stacja.get(j).equals(koniec2.get(i))){
					licznikKoniec2 = j;
				}
				if(licznikKoniec1 == -1 || licznikKoniec2 == -1){
					for(int k = 0; k < nazwa.size(); k++){
						if(koniec1.get(i).equals(nazwa.get(k))){
							System.out.println(koniec1.get(i) + "     " + nazwa.get(k));
							double nap = Double.parseDouble(napiecie.get(k))*Macierz.RMS/Math.sqrt(3);
							double Kat = Double.parseDouble(kat.get(k))*Math.PI/180;
							wektorNapiec1.setElement(i, 0, nap*Math.cos(Kat), nap*Math.sin(Kat));
							System.out.println(koniec1.get(i) + "     " + nazwa.get(k) + "    " + napiecie.get(k) +  "   " + kat.get(k));
						}
						if(koniec2.get(i).equals(nazwa.get(k))){
							double nap = Double.parseDouble(napiecie.get(k))*Macierz.RMS/Math.sqrt(3);
							double Kat = Double.parseDouble(kat.get(k))*Math.PI/180;
							wektorNapiec2.setElement(i, 0, nap*Math.cos(Kat), nap*Math.sin(Kat));
						}
					}
				}
			}
			if(licznikKoniec1 != -1){
				wektorNapiec1.setElement(i, 0, napieciePoZwarciu.getElementCopy(licznikKoniec1, 0));
			}
			if(licznikKoniec2 != -1){
				wektorNapiec2.setElement(i, 0, napieciePoZwarciu.getElementCopy(licznikKoniec2, 0));
			}
			
		}
		Wypisanie.wypiszModul(wektorNapiec1, "napiecie 1 '1'");
		Wypisanie.wypiszModul(wektorNapiec2, "napiecie 2 '1'");
		wektorRoznicyNapiec = wektorNapiec1.minus(wektorNapiec2);
		
		return wektorRoznicyNapiec;
	}
	
	public static ComplexMatrix rozplywPradow2(){
		ComplexMatrix rozplywpradow = new ComplexMatrix(roznicaNapiec2().getNrow(), 1);
		ComplexMatrix roznicanapiec = roznicaNapiec2();
		ArrayList<String> rodzaj = SelectLinia("rodzaj");
		ArrayList<String> R1_ = SelectLinia("R1");
		ArrayList<String> X1_ = SelectLinia("X1");
		ArrayList<String> dlugosc_ = SelectLinia("dlugosc");
		ArrayList<String> koniec1 = SelectLinia("koniec1");
		ArrayList<String> koniec2 = SelectLinia("koniec2");
		dlugosc_.add("0");
		R1_.add("0");
		X1_.add("0");
		rodzaj.add("");
		ArrayList<String> zwarcie = Macierz.SelectZwarcie();
		int a = roznicanapiec.getNrow()-1;
		double Rkoniec = 0, Xkoniec = 0; 
		String rodzajZwarcia = "";
		for(int i = 0; i < rozplywpradow.getNrow(); i++){
			double dlugosc = Double.parseDouble(dlugosc_.get(i));
			double R1 = Double.parseDouble(R1_.get(i))*dlugosc;
			double X1 = Double.parseDouble(X1_.get(i))*dlugosc;
			String stacjaOdniesienia = zwarcie.get(3);
			double odleglosc = Double.parseDouble(zwarcie.get(4));
			Complex Z = null;
			Complex U = null;
			if(i == numerLiniiZwarcia && koniec1.get(i).equals(stacjaOdniesienia)){
				Rkoniec = R1/dlugosc*(dlugosc-odleglosc);
				Xkoniec = X1/dlugosc*(dlugosc-odleglosc);
				R1 = R1/dlugosc*odleglosc;
				X1 = X1/dlugosc*odleglosc;
				rodzajZwarcia = rodzaj.get(i);
			}
			if(i == numerLiniiZwarcia && koniec2.get(i).equals(stacjaOdniesienia)){
				Rkoniec = R1/dlugosc*odleglosc;
				Xkoniec = X1/dlugosc*odleglosc;
				R1 = R1/dlugosc*(dlugosc-odleglosc);
				X1 = X1/dlugosc*(dlugosc-odleglosc);
				rodzajZwarcia = rodzaj.get(i);
			}
			if(i == a){
				R1 = Rkoniec;
				X1 = Xkoniec;
				rodzaj.set(a, rodzajZwarcia);
			}
			if(rodzaj.get(i).equals("jednotorowa")){
				Z = new Complex(R1, X1);
			}
			if(rodzaj.get(i).equals("dwutorowa")){
				Z = new Complex(R1/2, X1/2);
			}
			U = roznicanapiec.getElementReference(i, 0);
			Complex I = Complex.over(U, Z);
			rozplywpradow.setElement(i, 0, I.getReal(), I.getImag());
		}
		return rozplywpradow;
	}
		
	public static ComplexMatrix roznicaNapiec2(){
		ArrayList<String> linia = Zwarcie.SelectLinia();
		ArrayList<String> koniec1 = SelectLinia("koniec1");
		ArrayList<String> koniec2 = SelectLinia("koniec2");
		ArrayList<String> liniaNazwa = SelectLinia("nazwa");
		ArrayList<String> nazwa = Macierz.SelectPodsystem("nazwa");
		ArrayList<String> stacja = Macierz.SelectStacja("nazwa");
		String liniaZwarcia = Macierz.SelectZwarcie().get(2);
		for(int i = 0; i < linia.size(); i++){
			if(liniaNazwa.get(i).equals(liniaZwarcia)){
				numerLiniiZwarcia = i;
			}
		}
		ComplexMatrix napieciePoZwarciu = Macierz.napieciaPoZwarciu2();
		ComplexMatrix wektorRoznicyNapiec = new ComplexMatrix(linia.size()+1, 1);
		ComplexMatrix wektorNapiec1 = new ComplexMatrix(linia.size()+1, 1);
		ComplexMatrix wektorNapiec2 = new ComplexMatrix(linia.size()+1, 1);
		for(int i = 0; i < linia.size(); i++){
			int licznikKoniec1 = -1, licznikKoniec2 = -1;
			for(int j = 0; j< stacja.size(); j++){
				if(numerLiniiZwarcia == i){
					wektorNapiec2.setElement(i, 0, napieciePoZwarciu.getElementReference(stacja.size(), 0));
					wektorNapiec2.setElement(linia.size(), 0, napieciePoZwarciu.getElementReference(stacja.size(), 0));
					int numerKoniec1 = -1, numerKoniec2 = -1;
					for(int k = 0; k < stacja.size(); k++){
						if(koniec1.get(i).equals(stacja.get(k))){
							numerKoniec1 = k;
						}
						if(koniec2.get(i).equals(stacja.get(k))){
							numerKoniec2 = k;
						}
					}
					wektorNapiec1.setElement(i, 0, napieciePoZwarciu.getElementReference(numerKoniec1, 0));
					wektorNapiec1.setElement(linia.size(), 0, napieciePoZwarciu.getElementReference(numerKoniec2, 0));
					break;
				}
				if(stacja.get(j).equals(koniec1.get(i))){
					licznikKoniec1 = j;
				}
				if(stacja.get(j).equals(koniec2.get(i))){
					licznikKoniec2 = j;
				}
				if(licznikKoniec1 == -1 || licznikKoniec2 == -1){
					for(int k = 0; k < nazwa.size(); k++){
						if(koniec1.get(i).equals(nazwa.get(k))){
							wektorNapiec1.setElement(i, 0, 0, 0);
						}
						if(koniec2.get(i).equals(nazwa.get(k))){
							wektorNapiec2.setElement(i, 0, 0, 0);
						}
					}
				}
			}
			if(licznikKoniec1 != -1){
				wektorNapiec1.setElement(i, 0, napieciePoZwarciu.getElementCopy(licznikKoniec1, 0));
			}
			if(licznikKoniec2 != -1){
				wektorNapiec2.setElement(i, 0, napieciePoZwarciu.getElementCopy(licznikKoniec2, 0));
			}
			
		}
		Wypisanie.wypisz(wektorNapiec1, "napiecie 1 '2'");
		Wypisanie.wypisz(wektorNapiec2, "napiecie 2 '2'");
		wektorRoznicyNapiec = wektorNapiec1.minus(wektorNapiec2);
		Wypisanie.wypiszKat(wektorRoznicyNapiec, "wektor roznicy napiec");
		return wektorRoznicyNapiec;
	}

	public static ComplexMatrix rozplywPradow0(){
		ComplexMatrix rozplywpradow = new ComplexMatrix(roznicaNapiec0().getNrow(), 1);
		ComplexMatrix roznicanapiec = roznicaNapiec0();
		ArrayList<String> rodzaj = SelectLinia("rodzaj");
		ArrayList<String> R0_ = SelectLinia("R0");
		ArrayList<String> X0_ = SelectLinia("X0");
		ArrayList<String> R0m_ = SelectLinia("R0m");
		ArrayList<String> X0m_ = SelectLinia("X0m");
		ArrayList<String> dlugosc_ = SelectLinia("dlugosc");
		ArrayList<String> koniec1 = SelectLinia("koniec1");
		ArrayList<String> koniec2 = SelectLinia("koniec2");
		dlugosc_.add("0");
		R0_.add("0");
		X0_.add("0");
		rodzaj.add("");
		R0m_.add("0");
		X0m_.add("0");
		ArrayList<String> zwarcie = Macierz.SelectZwarcie();
		int a = roznicanapiec.getNrow()-1;
		double Rkoniec = 0, Xkoniec = 0; 
		String rodzajZwarcia = "";
		for(int i = 0; i < rozplywpradow.getNrow(); i++){
			double dlugosc = Double.parseDouble(dlugosc_.get(i));
			double R0 = Double.parseDouble(R0_.get(i))*dlugosc;
			double X0 = Double.parseDouble(X0_.get(i))*dlugosc;
			double X0m = 0, R0m = 0;
			if(!R0m_.get(i).equals("-")){
				R0m = Double.parseDouble(R0m_.get(i))*dlugosc;
				X0m = Double.parseDouble(X0m_.get(i))*dlugosc;
			}
			String stacjaOdniesienia = zwarcie.get(3);
			double odleglosc = Double.parseDouble(zwarcie.get(4));
			Complex Z = null;
			Complex U = null;
			if(i == numerLiniiZwarcia && koniec1.get(i).equals(stacjaOdniesienia)){
				Rkoniec = R0/dlugosc*(dlugosc-odleglosc);
				Xkoniec = X0/dlugosc*(dlugosc-odleglosc);
				R0 = R0/dlugosc*odleglosc;
				X0 = X0/dlugosc*odleglosc;
				rodzajZwarcia = rodzaj.get(i);
			}
			if(i == numerLiniiZwarcia && koniec2.get(i).equals(stacjaOdniesienia)){
				Rkoniec = R0/dlugosc*odleglosc;
				Xkoniec = X0/dlugosc*odleglosc;
				R0 = R0/dlugosc*(dlugosc-odleglosc);
				X0 = X0/dlugosc*(dlugosc-odleglosc);
				rodzajZwarcia = rodzaj.get(i);
			}
			if(i == a){
				R0 = Rkoniec;
				X0 = Xkoniec;
				rodzaj.set(a, rodzajZwarcia);
			}
			if(rodzaj.get(i).equals("jednotorowa")){
				Z = new Complex(R0, X0);
			}
			if(rodzaj.get(i).equals("dwutorowa")){
				Z = new Complex((R0*R0-X0*X0-R0m*R0m+X0m*X0m), 2*R0*X0-2*R0m*X0m);
			}
			U = roznicanapiec.getElementReference(i, 0);
			Complex I = Complex.over(U, Z);
			rozplywpradow.setElement(i, 0, I.getReal(), I.getImag());
		}
		return rozplywpradow;
	}
		
	public static ComplexMatrix roznicaNapiec0(){
		ArrayList<String> linia = Zwarcie.SelectLinia();
		ArrayList<String> koniec1 = SelectLinia("koniec1");
		ArrayList<String> koniec2 = SelectLinia("koniec2");
		ArrayList<String> liniaNazwa = SelectLinia("nazwa");
		ArrayList<String> nazwa = Macierz.SelectPodsystem("nazwa");
		ArrayList<String> stacja = Macierz.SelectStacja("nazwa");
		String liniaZwarcia = Macierz.SelectZwarcie().get(2);
		for(int i = 0; i < linia.size(); i++){
			if(liniaNazwa.get(i).equals(liniaZwarcia)){
				numerLiniiZwarcia = i;
			}
		}
		ComplexMatrix napieciePoZwarciu0 = Macierz.napieciaPoZwarciu0();
		ComplexMatrix wektorRoznicyNapiec = new ComplexMatrix(linia.size()+1, 1);
		ComplexMatrix wektorNapiec1 = new ComplexMatrix(linia.size()+1, 1);
		ComplexMatrix wektorNapiec2 = new ComplexMatrix(linia.size()+1, 1);
		for(int i = 0; i < linia.size(); i++){
			int licznikKoniec1 = -1, licznikKoniec2 = -1;
			for(int j = 0; j< stacja.size(); j++){
				if(numerLiniiZwarcia == i){
					wektorNapiec2.setElement(i, 0, napieciePoZwarciu0.getElementReference(stacja.size(), 0));
					wektorNapiec2.setElement(linia.size(), 0, napieciePoZwarciu0.getElementReference(stacja.size(), 0));
					int numerKoniec1 = -1, numerKoniec2 = -1;
					for(int k = 0; k < stacja.size(); k++){
						if(koniec1.get(i).equals(stacja.get(k))){
							numerKoniec1 = k;
						}
						if(koniec2.get(i).equals(stacja.get(k))){
							numerKoniec2 = k;
						}
					}
					wektorNapiec1.setElement(i, 0, napieciePoZwarciu0.getElementReference(numerKoniec1, 0));
					wektorNapiec1.setElement(linia.size(), 0, napieciePoZwarciu0.getElementReference(numerKoniec2, 0));
					break;
				}
				if(stacja.get(j).equals(koniec1.get(i))){
					licznikKoniec1 = j;
				}
				if(stacja.get(j).equals(koniec2.get(i))){
					licznikKoniec2 = j;
				}
				if(licznikKoniec1 == -1 || licznikKoniec2 == -1){
					for(int k = 0; k < nazwa.size(); k++){
						if(koniec1.get(i).equals(nazwa.get(k))){
							wektorNapiec1.setElement(i, 0, 0, 0);
						}
						if(koniec2.get(i).equals(nazwa.get(k))){
							wektorNapiec2.setElement(i, 0, 0, 0);
						}
					}
				}
			}
			if(licznikKoniec1 != -1){
				wektorNapiec1.setElement(i, 0, napieciePoZwarciu0.getElementCopy(licznikKoniec1, 0));
			}
			if(licznikKoniec2 != -1){
				wektorNapiec2.setElement(i, 0, napieciePoZwarciu0.getElementCopy(licznikKoniec2, 0));
			}
			
		}
		wektorRoznicyNapiec = wektorNapiec1.minus(wektorNapiec2);
		
		return wektorRoznicyNapiec;
	}

	
	public static ArrayList<String> SelectLinia(String kolumna){
		String sql = "SELECT " + kolumna + " FROM LINIA";
		ArrayList<String> s = new ArrayList<String>();
		try(Connection conn = Stacja.polaczenie();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
				while (rs.next()){
					s.add(rs.getString(kolumna));
				}
			
			} catch(SQLException e){
				System.out.println(e.getMessage());
				
				
			}
		return s;
	}
	
}
