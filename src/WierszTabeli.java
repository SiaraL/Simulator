
public class WierszTabeli {

	private String nazwa;
	private double realL1;
	private double realL2;
	private double realL3;
	private double imagL1;
	private double imagL2;
	private double imagL3;
	private double modulL1;
	private double modulL2;
	private double modulL3;
	private double argL1;
	private double argL2;
	private double argL3;


	public WierszTabeli(String nazwa, double realL1, double imagL1, double modulL1, double argL1, double realL2, double imagL2, double modulL2, double argL2, double realL3, double imagL3, double modulL3, double argL3){
		this.nazwa = nazwa;
		this.realL1 = realL1;
		this.imagL1 = imagL1;
		this.modulL1 = modulL1;
		this.argL1 = argL1;
		this.realL2 = realL2;
		this.imagL2 = imagL2;
		this.modulL2 = modulL2;
		this.argL2 = argL2;
		this.realL3 = realL3;
		this.imagL3 = imagL3;
		this.modulL3 = modulL3;
		this.argL3 = argL3;
	}


	public double getRealL1() {
		return realL1;
	}


	public void setRealL1(double realL1) {
		this.realL1 = realL1;
	}


	public double getRealL2() {
		return realL2;
	}


	public void setRealL2(double realL2) {
		this.realL2 = realL2;
	}


	public double getRealL3() {
		return realL3;
	}


	public void setRealL3(double realL3) {
		this.realL3 = realL3;
	}


	public double getImagL1() {
		return imagL1;
	}


	public void setImagL1(double imagL1) {
		this.imagL1 = imagL1;
	}


	public double getImagL2() {
		return imagL2;
	}


	public void setImagL2(double imagL2) {
		this.imagL2 = imagL2;
	}


	public double getImagL3() {
		return imagL3;
	}


	public void setImagL3(double imagL3) {
		this.imagL3 = imagL3;
	}


	public double getModulL1() {
		return modulL1;
	}


	public void setModulL1(double modulL1) {
		this.modulL1 = modulL1;
	}


	public double getModulL2() {
		return modulL2;
	}


	public void setModulL2(double modulL2) {
		this.modulL2 = modulL2;
	}


	public double getModulL3() {
		return modulL3;
	}


	public void setModulL3(double modulL3) {
		this.modulL3 = modulL3;
	}


	public double getArgL1() {
		return argL1;
	}


	public void setArgL1(double argL1) {
		this.argL1 = argL1;
	}


	public double getArgL2() {
		return argL2;
	}


	public void setArgL2(double argL2) {
		this.argL2 = argL2;
	}


	public double getArgL3() {
		return argL3;
	}


	public void setArgL3(double argL3) {
		this.argL3 = argL3;
	}


	public String getNazwa() {
		return nazwa;
	}


	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
}		