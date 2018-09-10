import flanagan.complex.ComplexMatrix;

public class WielkosciFazowe {

	static ComplexMatrix Uprzed = Macierz.wektorNapiecPrzedZwarciem();
	static ComplexMatrix U0 = Macierz.napieciaPoZwarciu0();
	static ComplexMatrix U1 = Macierz.napieciaPoZwarciu1();
	static ComplexMatrix U2 = Macierz.napieciaPoZwarciu2();
	static ComplexMatrix Ik = Macierz.macierzSkladowychPradow();
	
	public static ComplexMatrix S(){
		ComplexMatrix S = new ComplexMatrix(3, 3);
		String rodzajZwarcia = Macierz.SelectZwarcie().get(1);
		if(rodzajZwarcia.equals("Jednofazowe K1: faza A") || rodzajZwarcia.equals("Dwufazowe K2: faza B i C") || rodzajZwarcia.equals("Trójfazowe")){
			S.setElement(0, 0, 1, 0);
			S.setElement(0, 1, 1, 0);
			S.setElement(0, 2, 1, 0);
			S.setElement(1, 0, 1, 0);
			S.setElement(1, 1, -0.5, -Math.sqrt(3)/2);
			S.setElement(1, 2, -0.5, Math.sqrt(3)/2);
			S.setElement(2, 0, 1, 0);
			S.setElement(2, 1, -0.5, Math.sqrt(3)/2);
			S.setElement(2, 2, -0.5, -Math.sqrt(3)/2);
		}
		if(rodzajZwarcia.equals("Jednofazowe K1: faza B") || rodzajZwarcia.equals("Dwufazowe K2: faza A i C")){
			S.setElement(1, 0, 1, 0);
			S.setElement(1, 1, 1, 0);
			S.setElement(1, 2, 1, 0);
			S.setElement(2, 0, 1, 0);
			S.setElement(2, 1, -0.5, -Math.sqrt(3)/2);
			S.setElement(2, 2, -0.5, Math.sqrt(3)/2);
			S.setElement(0, 0, 1, 0);
			S.setElement(0, 1, -0.5, Math.sqrt(3)/2);
			S.setElement(0, 2, -0.5, -Math.sqrt(3)/2);
		}
		if(rodzajZwarcia.equals("Jednofazowe K1: faza C") || rodzajZwarcia.equals("Dwufazowe K2: faza A i B")){
			S.setElement(2, 0, 1, 0);
			S.setElement(2, 1, 1, 0);
			S.setElement(2, 2, 1, 0);
			S.setElement(0, 0, 1, 0);
			S.setElement(0, 1, -0.5, -Math.sqrt(3)/2);
			S.setElement(0, 2, -0.5, Math.sqrt(3)/2);
			S.setElement(1, 0, 1, 0);
			S.setElement(1, 1, -0.5, Math.sqrt(3)/2);
			S.setElement(1, 2, -0.5, -Math.sqrt(3)/2);
		}
		return S;
	}
	
	public static ComplexMatrix NapieciaFazoweWezlowPrzedZwarciem(){
		ComplexMatrix NapieciaFazoweWezlow = new ComplexMatrix(Macierz.wektorNapiecPrzedZwarciem().getNrow(), 3);
		ComplexMatrix WektorPomocniczyNapiec = new ComplexMatrix(3, 1);
		for(int i = 0; i < NapieciaFazoweWezlow.getNrow(); i++){
			WektorPomocniczyNapiec.setElement(0, 0, 0, 0);
			WektorPomocniczyNapiec.setElement(1, 0, Uprzed.getElementReference(i, 0));
			WektorPomocniczyNapiec.setElement(2, 0, 0, 0);
			WektorPomocniczyNapiec = S().times(WektorPomocniczyNapiec);
			NapieciaFazoweWezlow.setElement(i, 0, WektorPomocniczyNapiec.getElementReference(0, 0));
			NapieciaFazoweWezlow.setElement(i, 1, WektorPomocniczyNapiec.getElementReference(1, 0));
			NapieciaFazoweWezlow.setElement(i, 2, WektorPomocniczyNapiec.getElementReference(2, 0));
		}
		return NapieciaFazoweWezlow;
	}
	
	public static ComplexMatrix NapieciaFazoweWezlowWTrakcieZwarcia(){
		ComplexMatrix NapieciaFazoweWezlow = new ComplexMatrix(Macierz.wektorNapiecPrzedZwarciem().getNrow(), 3);
		ComplexMatrix WektorPomocniczyNapiec = new ComplexMatrix(3, 1);
		for(int i = 0; i < NapieciaFazoweWezlow.getNrow(); i++){
			WektorPomocniczyNapiec.setElement(0, 0, U0.getElementReference(i, 0));
			WektorPomocniczyNapiec.setElement(1, 0, U1.getElementReference(i, 0));
			WektorPomocniczyNapiec.setElement(2, 0, U2.getElementReference(i, 0));
			WektorPomocniczyNapiec = S().times(WektorPomocniczyNapiec);
			NapieciaFazoweWezlow.setElement(i, 0, WektorPomocniczyNapiec.getElementReference(0, 0));
			NapieciaFazoweWezlow.setElement(i, 1, WektorPomocniczyNapiec.getElementReference(1, 0));
			NapieciaFazoweWezlow.setElement(i, 2, WektorPomocniczyNapiec.getElementReference(2, 0));
		}
		return NapieciaFazoweWezlow;
	}
	
	public static ComplexMatrix PradyFazoweWMiejscuZwarcia(){
		ComplexMatrix PradyFazoweZwarcia = new ComplexMatrix(3, 1);
		PradyFazoweZwarcia = S().times(Ik);
		return PradyFazoweZwarcia;
	}
	
	public static ComplexMatrix PradyFazoweRozplyw(){
		ComplexMatrix PradyFazoweRozplyw = new ComplexMatrix(RozplywPradow.rozplywPradow1().getNrow(), 3);
		ComplexMatrix I1 = RozplywPradow.rozplywPradow1();
		ComplexMatrix I0 = RozplywPradow.rozplywPradow0();
		ComplexMatrix I2 = RozplywPradow.rozplywPradow2();
		ComplexMatrix WektorPomocniczyPradow = new ComplexMatrix(3, 1);
		for(int i = 0; i < PradyFazoweRozplyw.getNrow(); i++){
			
			WektorPomocniczyPradow.setElement(0, 0, I0.getElementReference(i, 0));
			WektorPomocniczyPradow.setElement(1, 0, I1.getElementReference(i, 0));
			WektorPomocniczyPradow.setElement(2, 0, I2.getElementReference(i, 0));
			WektorPomocniczyPradow = S().times(WektorPomocniczyPradow);
			PradyFazoweRozplyw.setElement(i, 0, WektorPomocniczyPradow.getElementReference(0, 0));
			PradyFazoweRozplyw.setElement(i, 1, WektorPomocniczyPradow.getElementReference(1, 0));
			PradyFazoweRozplyw.setElement(i, 2, WektorPomocniczyPradow.getElementReference(2, 0));
		}
		return PradyFazoweRozplyw;
	}
	
	
}
