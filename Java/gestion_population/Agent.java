package gestion_population;

import java.util.HashMap;

import outils.Global;
import outils.Meteo;

public class Agent {

	private Coordonnees coordonnees;

	private float motivation;
	private HashMap<String, Float> volonteFinale; // definie la volonte de boire
													// une boisson

	private boolean veutBoissonFroide;
	private boolean veutBoissonSansAlcool;

	private boolean aBueAujourdhui;

	/**
	 * Creer un agent avec comme parametre la meteo
	 * 
	 * @param meteo
	 * @param periodeJournee
	 */
	public Agent(Meteo meteo, Meteo periodeJournee) {

		this.motivation = this.calculerMotivationClient(meteo, periodeJournee);

		this.calculerBoissonFroideClient(meteo, periodeJournee);

		this.calculerEnvieBoissonSansAlcool(meteo, periodeJournee);

		this.aBueAujourdhui = false;
		this.volonteFinale = new HashMap<String, Float>();
		this.coordonnees = new Coordonnees(0f, 0f);
	}

	public Agent(Coordonnees coordonnees, float motivation, boolean veutBoissonFroide, boolean veutBoissonSansAlcool) {
		super();
		this.coordonnees = coordonnees;
		this.motivation = motivation;

		this.veutBoissonFroide = veutBoissonFroide;
		this.veutBoissonSansAlcool = veutBoissonSansAlcool;
	}

	/**
	 * Renvoie la valeur de motivation d'un client avec une base min et max
	 * multiplié par la meteo Si la meteo est une tempete le client ne boit pas
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @return Un float entre le minimun de motivation d'un client et le max.
	 *         Multiplie par l'impact de la meteo.
	 */
	private float calculerMotivationClient(Meteo meteo, Meteo periodeJournee) {

		if (meteo == Meteo.thunderstorm) {
			return 0f;
		} else {
			float motivation = outils.OutilsCalculs.randomFloat(Global.clientMinMotivation, Global.clientMaxMotivation);

			motivation *= outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(meteo);

			return motivation;
		}
	}

	/**
	 * Lance un dés 100, s'il est inferieur a la valeur lie a la meteo
	 * (Déplacement 15%rainny 30%cloudy 75%sunny 100%heatwave 0%thunderstorm
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @return Vrai si le client veut une boisson froide, faux s'il veut une
	 *         boisson chaude
	 */
	private void calculerBoissonFroideClient(Meteo meteo, Meteo periodeJournee) {

		float des = outils.OutilsCalculs.randomFloat(0, 100);

		if (outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(meteo) >= des) {
			this.veutBoissonFroide = true;
		} else {
			this.veutBoissonFroide = false;
		}

	}

	/**
	 * Definie si le client veut une boisson avec ou sans alcool Le matin 25% de
	 * chance, le soir 75% (definit en variable globale)
	 * 
	 * @param meteo
	 * @param periodeJournee
	 */
	private void calculerEnvieBoissonSansAlcool(Meteo meteo, Meteo periodeJournee) {

		float des = outils.OutilsCalculs.randomFloat(0, 100);

		if (meteo == Meteo.matin) {
			if (des > outils.Global.clientAlcoloMatin) {
				this.veutBoissonSansAlcool = true;
			} else {
				this.veutBoissonSansAlcool = false;
			}
		} else {
			if (des > outils.Global.clientAlcoloSoir) {
				this.veutBoissonSansAlcool = true;
			} else {
				this.veutBoissonSansAlcool = false;
			}
		}
	}

	/**
	 * Calcul du bonus de volonte finale d'une pub sur un client
	 * 
	 * @param playerName
	 * @param mapItem
	 */
	public void calculerGainVolonteFinaleParUnePub(String playerName, MapItem mapItem) {
		if (this.volonteFinale.get(playerName) == null) {
			this.volonteFinale.put(playerName, 0f);
		}

		float volonteFinale = this.volonteFinale.get(playerName).floatValue();
		volonteFinale += ((this.calculerInfluancePub(mapItem) * outils.Global.poidInfluencePub)
				- (outils.OutilsCalculs.calculerDistance(this, mapItem)
						* outils.Global.poidDistancePerteVolonteFinale));
		this.volonteFinale.put(playerName, volonteFinale);
	}

	/**
	 * Calcule l'influence d'une pub sur un client
	 * 
	 * @param client
	 * @param mapItem
	 * @return
	 */
	private float calculerInfluancePub(MapItem mapItem) {
		float distance = outils.OutilsCalculs.calculerDistance(this, mapItem);

		return (float) ((Math.pow(mapItem.getInfluence(), 2) * outils.Global.poidInfluencePub)
				/ (Math.pow(distance, 2)));
	}

	public boolean isaBueAujourdhui() {
		return aBueAujourdhui;
	}

	public void setaBueAujourdhui(boolean aBueAujourdhui) {
		this.aBueAujourdhui = aBueAujourdhui;
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

	public boolean getIsVeutBoissonFroide() {
		return veutBoissonFroide;
	}

	public void setVeutBoissonFroide(boolean veutBoissonFroide) {
		this.veutBoissonFroide = veutBoissonFroide;
	}

	public boolean getIsVeutBoissonSansAlcool() {
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
