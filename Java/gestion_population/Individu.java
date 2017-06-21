package gestion_population;

import outils.Meteo;

public class Individu {

	Coordonnees coordonnees;

	float motivation;
	float distanceMax;

	boolean veutBoissonFroide;
	boolean veutBoissonSansAlcool;

	public Individu(Coordonnees coordonnees, float motivation, float distanceMax, boolean veutBoissonFroide,
			boolean veutBoissonSansAlcool) {
		super();
		this.coordonnees = coordonnees;
		this.motivation = motivation;
		this.distanceMax = distanceMax;
		this.veutBoissonFroide = veutBoissonFroide;
		this.veutBoissonSansAlcool = veutBoissonSansAlcool;
	}
	
	
	
	
	public float calculerMotivation(Meteo meteo){
		motivation = 0;
		float poidsMotivation = outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(meteo);
		//gestion 
		if(this.veutBoissonFroide){
			poidsMotivation -= 1;
		}
		motivation = outils.OutilsCalculs.randomInt(outils.Global.minMotivation, outils.Global.maxMotivation);
		
		
		
		
		return motivation;
	}
	
	
	
	
	

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public float getMotivation() {
		return motivation;
	}

	public void setMotivation(float motivation) {
		this.motivation = motivation;
	}

	public boolean isVeutBoissonFroide() {
		return veutBoissonFroide;
	}

	public void setVeutBoissonFroide(boolean veutBoissonFroide) {
		this.veutBoissonFroide = veutBoissonFroide;
	}

	public boolean isVeutBoissonSansAlcool() {
		return veutBoissonSansAlcool;
	}

	public void setVeutBoissonSansAlcool(boolean veutBoissonSansAlcool) {
		this.veutBoissonSansAlcool = veutBoissonSansAlcool;
	}

	public void setCoordonnees(int x, int y) {
		this.coordonnees = new Coordonnees(x, y);
	}

	public int getX() {
		return this.coordonnees.getX();
	}

	public void setX(int x) {
		this.coordonnees.setX(x);
	}

	public int getY() {
		return this.coordonnees.getY();
	}

	public void getY(int y) {
		this.coordonnees.setY(y);
	}

}
