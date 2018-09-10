import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import flanagan.complex.Complex;
import flanagan.complex.ComplexMatrix;

public class Macierz {

	static boolean sukces = false;
	static ArrayList<String> licznik = new ArrayList<String>();

	public static ComplexMatrix macierzSkladowychPradow() {
		ComplexMatrix macierzPradowSkladowych = new ComplexMatrix(3, 1);
		ComplexMatrix A = RodzajZwarcia.rodzajZwarciaA();
		ComplexMatrix B = RodzajZwarcia.rodzajZwarciaB();
		ComplexMatrix Z = macierzImpedancjiSkladowych();
		ComplexMatrix U = wektorSkladowychNapiecia();
		ComplexMatrix AZ = A.times(Z);
		ComplexMatrix AU = A.times(U);
		ComplexMatrix AZB = AZ.minus(B);
		
		macierzPradowSkladowych = AZB.inverse().times(AU);

		return macierzPradowSkladowych;
	}

	public static ComplexMatrix napieciaPoZwarciu1() {
		macierzLG();
		ComplexMatrix napieciePrzedZwarciem = wektorNapiecPrzedZwarciem();
		ComplexMatrix napieciePoZwarciu = new ComplexMatrix(napieciePrzedZwarciem.getNrow(), 1);
		ComplexMatrix pradZwarcia = new ComplexMatrix(napieciePrzedZwarciem.getNrow(), 1);
		pradZwarcia.setElement(pradZwarcia.getNrow() - 1, 0, macierzSkladowychPradow().getElementReference(1, 0));
		napieciePoZwarciu = napieciePrzedZwarciem.minus(macierzAdmitancyjna("1").inverse().times(pradZwarcia));
		return napieciePoZwarciu;
	}

	public static ComplexMatrix napieciaPoZwarciu2() {
		macierzLG();
		ComplexMatrix napieciePrzedZwarciem = new ComplexMatrix(napieciaPoZwarciu1().getNrow(), 1);
		ComplexMatrix napieciePoZwarciu = new ComplexMatrix(napieciePrzedZwarciem.getNrow(), 1);
		ComplexMatrix pradZwarcia = new ComplexMatrix(napieciePrzedZwarciem.getNrow(), 1);
		pradZwarcia.setElement(pradZwarcia.getNrow() - 1, 0, macierzSkladowychPradow().getElementReference(2, 0));
		napieciePoZwarciu = napieciePrzedZwarciem.minus(macierzAdmitancyjna("1").inverse().times(pradZwarcia));
		return napieciePoZwarciu;
	}

	public static ComplexMatrix napieciaPoZwarciu0() {
		macierzLG();
		ComplexMatrix napieciePrzedZwarciem = new ComplexMatrix(napieciaPoZwarciu1().getNrow(), 1);
		ComplexMatrix napieciePoZwarciu = new ComplexMatrix(napieciePrzedZwarciem.getNrow(), 1);
		ComplexMatrix pradZwarcia = new ComplexMatrix(napieciePrzedZwarciem.getNrow(), 1);
		pradZwarcia.setElement(pradZwarcia.getNrow() - 1, 0, macierzSkladowychPradow().getElementReference(0, 0));
		napieciePoZwarciu = napieciePrzedZwarciem.minus(macierzAdmitancyjna("0").inverse().times(pradZwarcia));
		return napieciePoZwarciu;
	}

	public static ComplexMatrix wektorSkladowychNapiecia() {
		macierzLG();
		ComplexMatrix napiecie = new ComplexMatrix(3, 1);
		Complex U1 = wektorNapiecPrzedZwarciem().getElementReference(SelectStacja("napiecie").size(), 0);
		napiecie.setElement(1, 0, U1);
		return napiecie;
	}

	public static ComplexMatrix macierzLG() {
		ComplexMatrix macierzLG = new ComplexMatrix(macierzAdmitancyjna("1").getNrow(),
				macierzAdmitancyjna("1").getNcol());
		ArrayList<String> stacja = SelectStacja("nazwa");
		ArrayList<String> podsystem = SelectPodsystem("nazwa");
		for (int i = 0; i < stacja.size(); i++) {
			for (int j = 0; j < podsystem.size() * 11; j += 11) {
				ArrayList<String> poprzeczne = liniePoprzeczne(j/11);
				if (stacja.get(i).equals(poprzeczne.get(7)) || stacja.get(i).equals(poprzeczne.get(8))) {
					if(poprzeczne.get(9).equals("jednotorowa")){
						double dlugosc = Double.parseDouble(poprzeczne.get(0));
						double R1 = Double.parseDouble(poprzeczne.get(1))*dlugosc;
						double X1 = Double.parseDouble(poprzeczne.get(2))*dlugosc;
						double real = -R1 / (R1 * R1 + X1 * X1);
						double imag = X1 / (R1 * R1 + X1 * X1);
						macierzLG.setElement(i, i, real, imag);
						licznik.add(poprzeczne.get(7));
						licznik.add(poprzeczne.get(8));
						licznik.add(String.valueOf(i));
					}if(poprzeczne.get(9).equals("dwutorowa")){
						double dlugosc = Double.parseDouble(poprzeczne.get(0));
						double R1 = Double.parseDouble(poprzeczne.get(1))*dlugosc;
						double X1 = Double.parseDouble(poprzeczne.get(2))*dlugosc;
						double real = R1 * 2 / (R1 * R1 + X1 * X1);
						double imag = -X1 * 2 / (R1 * R1 + X1 * X1);
						macierzLG.setElement(i, i, real, imag);
						licznik.add(stacja.get(i));
					}
				}

			}
		}
		return macierzLG;
	}
	
	public static ComplexMatrix wektorNapiecPodsystemow(){
		ComplexMatrix wektorGG = new ComplexMatrix(macierzAdmitancyjna("1").getNrow(), 1);
		ArrayList<String> podsystem = SelectPodsystem("nazwa");
		ArrayList<String> napiecie = SelectPodsystem("napiecie");
		ArrayList<String> kat = SelectPodsystem("przesuniecieKatowe");
		for(int i = 0; i < licznik.size(); i+=3){
			for(int j = 0; j < podsystem.size(); j++){
				if(licznik.get(i).equals(podsystem.get(j)) || licznik.get(i+1).equals(podsystem.get(j))){
					double Kat = Double.parseDouble(kat.get(j))*Math.PI/180;
					double nap = Double.parseDouble(napiecie.get(j));
					double real = nap*Math.cos(Kat);
					double imag = nap*Math.sin(Kat);
					wektorGG.setElement(Integer.parseInt(licznik.get(i+2)), 0, real, imag);
				}
			}
		}
		return wektorGG; 
	}

	public static ComplexMatrix wektorNapiecPrzedZwarciem() {
		ComplexMatrix Z = macierzAdmitancyjna("1").inverse();
		ComplexMatrix G = wektorNapiecPodsystemow();
		ComplexMatrix Y = macierzLG();
		ComplexMatrix macierzZer = new ComplexMatrix(G.getNrow(), G.getNcol());
		ComplexMatrix U10 = ComplexMatrix.minus(macierzZer, (Z.times(Y)).times(G));
		return U10;
	}

	public static ComplexMatrix macierzImpedancjiSkladowych() {
		ArrayList<String> s = SelectStacja("nazwa");
		ComplexMatrix Z012 = new ComplexMatrix(3, 3);
		Complex Z0 = macierzAdmitancyjna("0").inverse().getElementReference(s.size(), s.size());
		Complex Z1 = macierzAdmitancyjna("1").inverse().getElementCopy(s.size(), s.size());
		Z012.setElement(0, 0, Z0);
		Z012.setElement(1, 1, Z1);
		Z012.setElement(2, 2, Z1);
		return Z012;
	}

	public static ComplexMatrix macierzAdmitancyjna(String skladowa) {
		ArrayList<String> s = SelectStacja("nazwa");
		ArrayList<String> z = SelectZwarcie();
		ComplexMatrix macierz = new ComplexMatrix(5, 5);
		if (skladowa == "1") {
			for (int i = 0; i < s.size(); i++) {
				for (int j = 0; j < s.size(); j++) {
					if (i == j) {
						double real = 0, imag = 0;
						ArrayList<String> wlasne = wartosciWlasne(i);
						for (int k = 0; k < wlasne.size(); k += 11) {
							double dlugosc = Double.parseDouble(wlasne.get(k));
							double R1 = Double.parseDouble(wlasne.get(k + 1));
							double X1 = Double.parseDouble(wlasne.get(k + 2));
							double odlegloscZwarcia = Double.parseDouble(z.get(4));
							if (wlasne.get(k + 9).equals("jednotorowa")) {
								if (z.get(2).equals(wlasne.get(k + 10))) {
									if (z.get(3).equals(s.get(i))) {
										R1 = R1 * odlegloscZwarcia;
										X1 = X1 * odlegloscZwarcia;
										real += R1 / (R1 * R1 + X1 * X1);
										imag += -X1 / (R1 * R1 + X1 * X1);
										Complex complex = new Complex(R1 / (R1 * R1 + X1 * X1),
												-X1 / (R1 * R1 + X1 * X1));
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
									} else {
										R1 = R1 * (dlugosc - odlegloscZwarcia);
										X1 = X1 * (dlugosc - odlegloscZwarcia);
										real += R1 / (R1 * R1 + X1 * X1);
										imag += -X1 / (R1 * R1 + X1 * X1);
										Complex complex = new Complex(R1 / (R1 * R1 + X1 * X1),
												-X1 / (R1 * R1 + X1 * X1));
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
									}
								} else {
									R1 = R1 * dlugosc;
									X1 = X1 * dlugosc;
									real += R1 / (R1 * R1 + X1 * X1);
									imag += -X1 / (R1 * R1 + X1 * X1);
								}
								macierz.setElement(i, j, real, imag);
							}
							if (wlasne.get(k + 9).equals("dwutorowa")) {
								if (z.get(2).equals(wlasne.get(k + 10))) {
									if (z.get(3).equals(s.get(i))) {
										R1 = R1 * odlegloscZwarcia;
										X1 = X1 * odlegloscZwarcia;
										real += R1 * 2 / (R1 * R1 + X1 * X1);
										imag += -X1 * 2 / (R1 * R1 + X1 * X1);
										Complex complex = new Complex(2 * R1 / (R1 * R1 + X1 * X1),
												-X1 * 2 / (R1 * R1 + X1 * X1));
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
									} else {
										R1 = R1 * (dlugosc - odlegloscZwarcia);
										X1 = X1 * (dlugosc - odlegloscZwarcia);
										real += R1 * 2 / (R1 * R1 + X1 * X1);
										imag += -X1 * 2 / (R1 * R1 + X1 * X1);
										Complex complex = new Complex(R1 * 2 / (R1 * R1 + X1 * X1),
												-X1 * 2 / (R1 * R1 + X1 * X1));
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
									}
								} else {
									R1 = R1 * dlugosc;
									X1 = X1 * dlugosc;
									real += R1 * 2 / (R1 * R1 + X1 * X1);
									imag += -X1 * 2 / (R1 * R1 + X1 * X1);
								}
								macierz.setElement(i, j, real, imag);
							}
						}
					} else {
						ArrayList<String> wzajemne = wartosciWzajemne(i, j);
						if (!sukces) {
							macierz.setElement(i, j, 0, 0);
						} else {
							double dlugosc = Double.parseDouble(wzajemne.get(0));
							double R1 = Double.parseDouble(wzajemne.get(1));
							double X1 = Double.parseDouble(wzajemne.get(2));
							double odlegloscZwarcia = Double.parseDouble(z.get(4));
							if (wzajemne.get(9).equals("jednotorowa")) {
								double real = 0, imag = 0;
								if (z.get(2).equals(wzajemne.get(10))) {
									if (z.get(3).equals(s.get(i))) {
										real = 0;
										imag = 0;
										R1 = R1 * odlegloscZwarcia;
										X1 = X1 * odlegloscZwarcia;
										macierz.setElement(s.size(), i, -R1 / (R1 * R1 + X1 * X1),
												X1 / (R1 * R1 + X1 * X1));
										macierz.setElement(i, s.size(), -R1 / (R1 * R1 + X1 * X1),
												X1 / (R1 * R1 + X1 * X1));
									} else {
										real = 0;
										imag = 0;
										R1 = R1 * (dlugosc - odlegloscZwarcia);
										X1 = X1 * (dlugosc - odlegloscZwarcia);
										macierz.setElement(s.size(), i, -R1 / (R1 * R1 + X1 * X1),
												X1 / (R1 * R1 + X1 * X1));
										macierz.setElement(i, s.size(), -R1 / (R1 * R1 + X1 * X1),
												X1 / (R1 * R1 + X1 * X1));
									}

								} else {
									R1 = R1 * dlugosc;
									X1 = X1 * dlugosc;
									real = -R1 / (R1 * R1 + X1 * X1);
									imag = X1 / (R1 * R1 + X1 * X1);
								}

								macierz.setElement(i, j, real, imag);
							}
							if (wzajemne.get(9).equals("dwutorowa")) {
								double real = 0, imag = 0;
								if (z.get(2).equals(wzajemne.get(10))) {
									if (z.get(3).equals(s.get(i))) {
										real = 0;
										imag = 0;
										R1 = R1 * odlegloscZwarcia;
										X1 = X1 * odlegloscZwarcia;
										macierz.setElement(s.size(), i, -R1 * 2 / (R1 * R1 + X1 * X1),
												X1 * 2 / (R1 * R1 + X1 * X1));
										macierz.setElement(i, s.size(), -R1 * 2 / (R1 * R1 + X1 * X1),
												X1 * 2 / (R1 * R1 + X1 * X1));
									} else {
										real = 0;
										imag = 0;
										R1 = R1 * (dlugosc - odlegloscZwarcia);
										X1 = X1 * (dlugosc - odlegloscZwarcia);
										macierz.setElement(s.size(), i, -R1 * 2 / (R1 * R1 + X1 * X1),
												X1 * 2 / (R1 * R1 + X1 * X1));
										macierz.setElement(i, s.size(), -R1 * 2 / (R1 * R1 + X1 * X1),
												X1 * 2 / (R1 * R1 + X1 * X1));
									}

								} else {
									R1 = R1 * dlugosc;
									X1 = X1 * dlugosc;
									real = -R1 * 2 / (R1 * R1 + X1 * X1);
									imag = X1 * 2 / (R1 * R1 + X1 * X1);
								}

								macierz.setElement(i, j, real, imag);
							}
						}
					}
				}
			}
		}
		if (skladowa == "0") {
			for (int i = 0; i < s.size(); i++) {
				for (int j = 0; j < s.size(); j++) {
					if (i == j) {
						ArrayList<String> wlasne = wartosciWlasne(i);
						double real = 0, imag = 0;
						for (int k = 0; k < wlasne.size(); k += 11) {
							double dlugosc = Double.parseDouble(wlasne.get(k + 0));
							double R0 = Double.parseDouble(wlasne.get(k + 3));
							double X0 = Double.parseDouble(wlasne.get(k + 4));
							double odlegloscZwarcia = Double.parseDouble(z.get(4));
							if (wlasne.get(k + 9).equals("jednotorowa")) {
								if (z.get(2).equals(wlasne.get(k + 10))) {
									if (z.get(3).equals(s.get(i))) {
										R0 = R0 * odlegloscZwarcia;
										X0 = X0 * odlegloscZwarcia;
										real += R0 / (R0 * R0 + X0 * X0);
										imag += -X0 / (R0 * R0 + X0 * X0);
										Complex complex = new Complex(R0 / (R0 * R0 + X0 * X0),
												-X0 / (R0 * R0 + X0 * X0));
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
										;
									} else {
										R0 = R0 * (dlugosc - odlegloscZwarcia);
										X0 = X0 * (dlugosc - odlegloscZwarcia);
										real += R0 / (R0 * R0 + X0 * X0);
										imag += -X0 / (R0 * R0 + X0 * X0);
										Complex complex = new Complex(R0 / (R0 * R0 + X0 * X0),
												-X0 / (R0 * R0 + X0 * X0));
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
										;
									}
								} else {
									R0 = R0 * dlugosc;
									X0 = X0 * dlugosc;
									real += R0 / (R0 * R0 + X0 * X0);
									imag += -X0 / (R0 * R0 + X0 * X0);
								}
								macierz.setElement(i, j, real, imag);
							}
							if (wlasne.get(9).equals("dwutorowa")) {
								double R0m = Double.parseDouble(wlasne.get(5));
								double X0m = Double.parseDouble(wlasne.get(6));
								if (z.get(2).equals(wlasne.get(k + 10))) {
									if (z.get(3).equals(s.get(i))) {
										R0 = R0 * odlegloscZwarcia;
										X0 = X0 * odlegloscZwarcia;
										double mianownik, k0, Re, Im;
										mianownik = Math.pow(R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m, 2)
												+ Math.pow(2 * R0m * X0m - 2 * X0 * R0, 2);
										k0 = 2 * (Math.pow(R0 - R0m, 2) + Math.pow(X0 - X0m, 2));
										Re = R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m;
										Im = 2 * R0m * X0m - 2 * X0 * R0;
										real += k0 * Re / mianownik;
										imag += k0 * Im / mianownik;
										Complex complex = new Complex(k0 * Re / mianownik, k0 * Im / mianownik);
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
									} else {
										R0 = R0 * (dlugosc - odlegloscZwarcia);
										X0 = X0 * (dlugosc - odlegloscZwarcia);
										double mianownik, k0, Re, Im;
										mianownik = Math.pow(R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m, 2)
												+ Math.pow(2 * R0m * X0m - 2 * X0 * R0, 2);
										k0 = 2 * (Math.pow(R0 - R0m, 2) + Math.pow(X0 - X0m, 2));
										Re = R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m;
										Im = 2 * R0m * X0m - 2 * X0 * R0;
										real += k0 * Re / mianownik;
										imag += k0 * Im / mianownik;
										Complex complex = new Complex(k0 * Re / mianownik, k0 * Im / mianownik);
										macierz.setElement(s.size(), s.size(),
												macierz.getElementReference(s.size(), s.size()).plus(complex));
									}
								} else {
									R0 = R0 * dlugosc;
									X0 = X0 * dlugosc;
									double mianownik, k0, Re, Im;
									mianownik = Math.pow(R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m, 2)
											+ Math.pow(2 * R0m * X0m - 2 * X0 * R0, 2);
									k0 = 2 * (Math.pow(R0 - R0m, 2) + Math.pow(X0 - X0m, 2));
									Re = R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m;
									Im = 2 * R0m * X0m - 2 * X0 * R0;
									real += k0 * Re / mianownik;
									imag += k0 * Im / mianownik;
								}
								macierz.setElement(i, j, real, imag);
							}
						}
					} else {
						ArrayList<String> wzajemne = wartosciWzajemne(i, j);
						if (!sukces) {
							macierz.setElement(i, j, 0, 0);
						} else {
							double dlugosc = Double.parseDouble(wzajemne.get(0));
							double R0 = Double.parseDouble(wzajemne.get(3));
							double X0 = Double.parseDouble(wzajemne.get(4));
							double odlegloscZwarcia = Double.parseDouble(z.get(4));
							if (wzajemne.get(9).equals("jednotorowa")) {
								double real = 0, imag = 0;
								if (z.get(2).equals(wzajemne.get(10))) {
									if (z.get(3).equals(s.get(i))) {
										real = 0;
										imag = 0;
										R0 = R0 * odlegloscZwarcia;
										X0 = X0 * odlegloscZwarcia;
										macierz.setElement(s.size(), i, -R0 / (R0 * R0 + X0 * X0),
												X0 / (R0 * R0 + X0 * X0));
										macierz.setElement(i, s.size(), -R0 / (R0 * R0 + X0 * X0),
												X0 / (R0 * R0 + X0 * X0));
									} else {
										real = 0;
										imag = 0;
										R0 = R0 * (dlugosc - odlegloscZwarcia);
										X0 = X0 * (dlugosc - odlegloscZwarcia);
										macierz.setElement(s.size(), i, -R0 / (R0 * R0 + X0 * X0),
												X0 / (R0 * R0 + X0 * X0));
										macierz.setElement(i, s.size(), -R0 / (R0 * R0 + X0 * X0),
												X0 / (R0 * R0 + X0 * X0));
									}

								} else {
									R0 = R0 * dlugosc;
									X0 = X0 * dlugosc;
									real = -R0 / (R0 * R0 + X0 * X0);
									imag = X0 / (R0 * R0 + X0 * X0);
								}
								macierz.setElement(i, j, real, imag);
							}
							if (wzajemne.get(9).equals("dwutorowa")) {
								double R0m = Double.parseDouble(wzajemne.get(5));
								double X0m = Double.parseDouble(wzajemne.get(6));
								double real = 0, imag = 0;
								if (z.get(2).equals(wzajemne.get(10))) {
									if (z.get(3).equals(s.get(i))) {
										real = 0;
										imag = 0;
										R0 = R0 * odlegloscZwarcia;
										X0 = X0 * odlegloscZwarcia;
										double mianownik, k0, Re, Im;
										mianownik = Math.pow(R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m, 2)
												+ Math.pow(2 * R0m * X0m - 2 * X0 * R0, 2);
										k0 = 2 * (Math.pow(R0 - R0m, 2) + Math.pow(X0 - X0m, 2));
										Re = R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m;
										Im = 2 * R0m * X0m - 2 * X0 * R0;
										macierz.setElement(s.size(), i, -k0 * Re / mianownik, k0 * Im / mianownik);
										macierz.setElement(i, s.size(), -k0 * Re / mianownik, k0 * Im / mianownik);
									} else {
										real = 0;
										imag = 0;
										R0 = R0 * (dlugosc - odlegloscZwarcia);
										X0 = X0 * (dlugosc - odlegloscZwarcia);
										double mianownik, k0, Re, Im;
										mianownik = Math.pow(R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m, 2)
												+ Math.pow(2 * R0m * X0m - 2 * X0 * R0, 2);
										k0 = 2 * (Math.pow(R0 - R0m, 2) + Math.pow(X0 - X0m, 2));
										Re = R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m;
										Im = 2 * R0m * X0m - 2 * X0 * R0;
										macierz.setElement(s.size(), i, -k0 * Re / mianownik, k0 * Im / mianownik);
										macierz.setElement(i, s.size(), -k0 * Re / mianownik, k0 * Im / mianownik);
									}

								} else {
									R0 = R0 * dlugosc;
									X0 = X0 * dlugosc;
									double mianownik, k0, Re, Im;
									mianownik = Math.pow(R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m, 2)
											+ Math.pow(2 * R0m * X0m - 2 * X0 * R0, 2);
									k0 = 2 * (Math.pow(R0 - R0m, 2) + Math.pow(X0 - X0m, 2));
									Re = R0 * R0 - X0 * X0 - R0m * R0m + X0m * X0m;
									Im = 2 * R0m * X0m - 2 * X0 * R0;
									real = -k0 * Re / mianownik;
									imag = k0 * Im / mianownik;
								}
								macierz.setElement(i, j, real, imag);
							}
						}
					}
				}
			}
		}
		return macierz;
	}

	public static ArrayList<String> SelectZwarcie() {
		String sql = "SELECT nazwa, rodzaj, linia, stacjaOdniesienia, odlegloscOdStacjiOdniesienia, rezystancjaPrzejscia FROM ZWARCIE";
		ArrayList<String> zwarcie = new ArrayList<String>();
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				for (int i = 1; i <= 6; i++)
					zwarcie.add(rs.getString(i));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return zwarcie;
	}

	public static ArrayList<String> stacjeZwarcia() {
		ArrayList<String> l = new ArrayList<String>();
		String sql = "SELECT koniec1, koniec2, dlugosc FROM LINIA WHERE nazwa = '" + Macierz.SelectZwarcie().get(2)
				+ "'";
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			l.add(rs.getString(1));
			l.add(rs.getString(2));
			l.add(rs.getString(3));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return l;
	}

	public static ArrayList<String> wartosciWzajemne(int i, int j) {
		ArrayList<String> s = SelectStacja("nazwa");
		ArrayList<String> l = new ArrayList<String>();
		String sql = "SELECT dlugosc, R1, X1, R0, X0, R0m, X0m, B1, B0m, rodzaj, nazwa FROM LINIA WHERE (koniec1 = '"
				+ s.get(i) + "' AND koniec2 = '" + s.get(j) + "') OR (koniec1 = '" + s.get(j) + "' AND koniec2 = '"
				+ s.get(i) + "')";
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			for (int k = 1; k < 12; k++) {
				l.add(rs.getString(k));
			}
			sukces = true;
		} catch (SQLException e) {
			// System.out.println(e.getMessage());
			sukces = false;
		}
		return l;
	}

	public static ArrayList<String> wartosciWlasne(int i) {
		ArrayList<String> s = SelectStacja("nazwa");
		ArrayList<String> l = new ArrayList<String>();
		String sql = "SELECT dlugosc, R1, X1, R0, X0, R0m, X0m, B1, B0m, rodzaj, nazwa FROM LINIA WHERE koniec1 = '"
				+ s.get(i) + "' OR koniec2 = '" + s.get(i) + "'";
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				for (int j = 0; j < 11; j++) {
					l.add(rs.getString(j + 1));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return l;
	}

	public static ArrayList<String> wartosciWlasneLG() {
		ArrayList<String> l = new ArrayList<String>();
		String sql = "SELECT dlugosc, R1, X1, R0, X0, R0m, X0m, koniec1, koniec2, rodzaj, nazwa FROM LINIA";
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				for (int j = 0; j < 11; j++) {
					l.add(rs.getString(j + 1));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return l;
	}

	public static ArrayList<String> liniePoprzeczne(int i) {
		ArrayList<String> p = SelectPodsystem("nazwa");
		ArrayList<String> l = new ArrayList<String>();
		String sql = "SELECT dlugosc, R1, X1, R0, X0, R0m, X0m, koniec1, koniec2, rodzaj, nazwa FROM LINIA WHERE koniec1 = '"
				+ p.get(i) + "' OR koniec2 = '" + p.get(i) + "'";
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				for (int j = 0; j < 11; j++) {
					l.add(rs.getString(j + 1));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return l;
	}

	public static ArrayList<String> SelectPodsystem(String kolumna) {
		String sql = "SELECT " + kolumna + " FROM PODSYSTEM";
		ArrayList<String> p = new ArrayList<String>();
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				p.add(rs.getString(kolumna));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	public static ArrayList<String> SelectStacja(String kolumna) {
		String sql = "SELECT " + kolumna + " FROM STACJA";
		ArrayList<String> s = new ArrayList<String>();
		try (Connection conn = Stacja.polaczenie();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				s.add(rs.getString(kolumna));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}

	public static double modul(double r, double x) {
		return Math.hypot(r, x);
	}

	public static double faza(double r, double x) {
		return Math.atan(r / x);
	}



}
