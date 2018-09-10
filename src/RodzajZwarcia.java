import java.util.ArrayList;
import flanagan.complex.ComplexMatrix;

public class RodzajZwarcia {

	public static ComplexMatrix rodzajZwarciaA(){
		ArrayList<String> z = Macierz.SelectZwarcie();
		ComplexMatrix A = new ComplexMatrix(3, 3);
		if(z.get(1).equals("Jednofazowe K1: faza A")){
			A.setElement(0, 0, 1, 0);
			A.setElement(0, 1, 1, 0);
			A.setElement(0, 2, 1, 0);
		}
		if(z.get(1).equals("Jednofazowe K1: faza B")){
			A.setElement(1, 0, 1, 0);
			A.setElement(1, 1, 1, 0);
			A.setElement(1, 2, 1, 0);
		}
		if(z.get(1).equals("Jednofazowe K1: faza C")){
			A.setElement(2, 0, 1, 0);
			A.setElement(2, 1, 1, 0);
			A.setElement(2, 2, 1, 0);
		}
		if(z.get(1).equals("Dwufazowe K2: faza A i B")){
			A.setElement(2, 1, 1, 0);
			A.setElement(2, 2, -1, -0);
		}
		if(z.get(1).equals("Dwufazowe K2: faza A i C")){
			A.setElement(1, 1, 1, 0);
			A.setElement(1, 2, -1, -0);
		}
		if(z.get(1).equals("Dwufazowe K2: faza B i C")){
			A.setElement(0, 1, 1, 0);
			A.setElement(0, 2, -1, -0);
		}
		if(z.get(1).equals("Trójfazowe")){
			A.setElement(0, 0, 1, 0);
			A.setElement(1, 1, 1, 0);
			A.setElement(2, 2, 1, 0);
		}
		
		return A;
	}
	
	public static ComplexMatrix rodzajZwarciaB(){
		ComplexMatrix B = new ComplexMatrix(3, 3);
		ArrayList<String> z = Macierz.SelectZwarcie();
		double rezystancjaprzejscia = Double.parseDouble(z.get(5));
		if(rezystancjaprzejscia < 0.01){
			rezystancjaprzejscia = 0.01;
		}
		if(z.get(1).equals("Jednofazowe K1: faza A")){
			B.setElement(0, 0, -3*rezystancjaprzejscia, 0);
			B.setElement(1, 0, 1, 0);
			B.setElement(1, 1, -1, 0);
			B.setElement(2, 0, 1, 0);
			B.setElement(2, 2, -1, 0);
		}
		if(z.get(1).equals("Jednofazowe K1: faza B")){
			B.setElement(0, 0, 1, 0);
			B.setElement(0, 2, -1, -0);
			B.setElement(1, 0, -3*rezystancjaprzejscia, 0);
			B.setElement(2, 0, 1, 0);
			B.setElement(2, 1, -1, 0);
		}
		if(z.get(1).equals("Jednofazowe K1: faza C")){
			B.setElement(0, 0, 1, 0);
			B.setElement(0, 1, -1, 0);
			B.setElement(1, 0, 1, 0);
			B.setElement(1, 2, -1, 0);
			B.setElement(2, 0, -3*rezystancjaprzejscia, 0);
		}
		if(z.get(1).equals("Dwufazowe K2: faza A i B")){
			B.setElement(0, 1, 1, 0);
			B.setElement(0, 2, 1, 0);
			B.setElement(1, 0, 1, 0);
			B.setElement(2, 1, -rezystancjaprzejscia, 0);
		}
		if(z.get(1).equals("Dwufazowe K2: faza A i C")){
			B.setElement(0, 0, 1, 0);
			B.setElement(1, 1, -rezystancjaprzejscia, 0);
			B.setElement(2, 1, 1, 0);
			B.setElement(2, 2, 1, 0);
		}
		if(z.get(1).equals("Dwufazowe K2: faza B i C")){
			B.setElement(0, 1, -rezystancjaprzejscia, 0);
			B.setElement(1, 1, 1, 0);
			B.setElement(1, 2, 1, 0);
			B.setElement(2, 0, 1, 0);
		}
		if(z.get(1).equals("Trójfazowe")){
			B.setElement(1, 1, -rezystancjaprzejscia, 0);
		}
		
		return B;
	}

}
