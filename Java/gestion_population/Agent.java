package gestion_population;

import outils.Global;
import outils.Meteo;

public class Agent {

	Coordonnees coordonnees;

	float motivation;

	boolean veutBoissonFroide;
	boolean veutBoissonSansAlcool;
	
	
	
	
	/**
	 * Creer un agent avec comme parametre la meteo
	 * @param meteo
	 * @param periodeJournee
	 */
	public Agent(Meteo meteo, Meteo periodeJournee){
		
		this.motivation = this.calculerMotivationClient(meteo, periodeJournee);
		
		this.calculerBoissonFroideClient(meteo, periodeJournee);
		
		this.calculerEnvieBoissonSansAlcool(meteo, periodeJournee);
		
		this.coordonnees=new Coordonnees(0f, 0f);
	}

	public Agent(Coordonnees coordonnees, float motivation, boolean veutBoissonFroide,
			boolean veutBoissonSansAlcool) {
		super();
		this.coordonnees = coordonnees;
		this.motivation = motivation;

		this.veutBoissonFroide = veutBoissonFroide;
		this.veutBoissonSansAlcool = veutBoissonSansAlcool;
	}
	
//	public Agent(Coordonnees coordonnees, float motivation, boolean veutBoissonFroide,
//			boolean veutBoissonSansAlcool, Meteo meteo, Meteo periodeJournee){
//		
//	}
	
	

	
	/**
	 * Renvoie la valeur de motivation d'un client avec une base min et max multiplié par la meteo
	 * Si la meteo est une tempete le client ne boit pas
	 * @param meteo
	 * @param periodeJournee
	 * @return Un float entre le minimun de motivation d'un client et le max. Multiplie par l'impact de la meteo.
	 */
	private float calculerMotivationClient(Meteo meteo, Meteo periodeJournee){
		
		if(meteo == Meteo.thunderstorm){
			return 0f;
		}
		else{
			float motivation = outils.OutilsCalculs.randomFloat(Global.clientMinMotivation, Global.clientMaxMotivation);
			
			motivation *= outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(meteo);
			
			return motivation;
		}
	}
	
	/**
	 * Lance un dés 100, s'il est inferieur a la valeur lie a la meteo (Déplacement	15%rainny 30%cloudy 75%sunny 100%heatwave 0%thunderstorm
	 * @param meteo
	 * @param periodeJournee
	 * @return Vrai si le client veut une boisson froide, faux s'il veut une boisson chaude
	 */
	private void calculerBoissonFroideClient(Meteo meteo, Meteo periodeJournee){
		
		float des = outils.OutilsCalculs.randomFloat(0, 100);
		
		if(outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(meteo)>=des){
			this.veutBoissonFroide = true;
		}
		else{
			this.veutBoissonFroide = false;
		}
		
		
	}
	
	
	private void calculerEnvieBoissonSansAlcool(Meteo meteo, Meteo periodeJournee){
		
		float des = outils.OutilsCalculs.randomFloat(0, 100);
		
		
		if(meteo == Meteo.matin){
			if(des> outils.Global.clientAlcoloMatin){
				this.veutBoissonSansAlcool=true;
			}
			else{
				this.veutBoissonSansAlcool=false;
			}
		}
		else{
			if(des> outils.Global.clientAlcoloSoir){
				this.veutBoissonSansAlcool=true;
			}
			else{
				this.veutBoissonSansAlcool=false;
			}
		}
		
		
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

	public void setCoordonnees(float latitude, float longitude) {
		this.coordonnees = new Coordonnees(latitude, longitude);
	}

	public float getLatitude() {
		return this.coordonnees.getLatitude();
	}

	public void setLatitude(float latitude) {
		this.coordonnees.setLatitude(latitude);
	}

	public float getLongitude() {
		return this.coordonnees.getLongitude();
	}

	public void setLongitude(float longitude) {
		this.coordonnees.setLongitude(longitude);
	}

}
