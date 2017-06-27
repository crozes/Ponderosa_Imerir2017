package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import outils.Global;
import outils.Meteo;

public class Agent {

	private Coordonnees coordonnees;

	private float motivation;
	private HashMap<String, Stand> listeDesStandNonVisite ; // definie la volonte de boire une boisson
	private HashMap<String, Float> listeDeLaVolontePourStand; //
	private ArrayList<String> listeDesStandTrie;

	private boolean veutBoissonFroide;
	private boolean veutBoissonSansAlcool;

	private boolean aBueAujourdhui;

	/**
	 * Creer un agent avec comme parametre la meteo
	 * 
	 * @param meteo
	 * @param periodeJournee
	 */
	public Agent(Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {

		this.motivation = this.calculerMotivationClient(meteo, periodeJournee);

		this.calculerBoissonFroideClient(meteo, periodeJournee);

		this.calculerEnvieBoissonSansAlcool(meteo, periodeJournee);

		this.aBueAujourdhui = false;
		this.listeDeLaVolontePourStand =  new HashMap<String, Float>();
		this.listeDesStandNonVisite = new HashMap<String, Stand>(listeDesStand);
	

		this.listeDesStandTrie = new ArrayList<>();
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
		
		//petite verification que la valeur existe
		if (this.listeDeLaVolontePourStand.get(playerName) == null) {
			this.listeDeLaVolontePourStand.put(playerName, 1f);
		}

		float volonteFinale = this.listeDeLaVolontePourStand.get(playerName).floatValue();
		volonteFinale += ((this.calculerInfluancePub(mapItem) * outils.Global.poidInfluencePub)
				- (outils.OutilsCalculs.calculerDistance(this, mapItem)
						* outils.Global.poidDistancePerteVolonteFinale));
		this.listeDeLaVolontePourStand.put(playerName, volonteFinale);
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
		float influancePub = (float) ((Math.pow(mapItem.getInfluence(), 2) * outils.Global.poidInfluencePub)
				/ (Math.pow(distance, 2)));
		
		outils.ToString.toString("Valeur infuancePub : "+influancePub+" class.Agent method clacluler InfluancePub");
		
		return influancePub*outils.Global.poidCalculInfluence ;
	}
	
	
	/**
	 * Trouve le meilleur Stand
	 * @return
	 */
	public String trierStandSelonVolonteFinale(){
		
		
		String standMeilleur = null;
//		for(String cle : this.listeDeLaVolontePourStand.keySet()){
//			if (standMeilleur ==null){
//				standMeilleur = cle;
//			}
//			
//			if(this.listeDeLaVolontePourStand.get(standMeilleur)<this.listeDeLaVolontePourStand.get(cle)){
//				if(this.listeDesStand.get(cle) >
//				standMeilleur = cle;
//			}
//			
//		}
		return standMeilleur;
	}
	
	private float coutDuDeplacementVers(Coordonnees coordonneesStand){
		float distanceMaximun = outils.OutilsCalculs.calculerDistance(this.coordonnees, coordonneesStand);
		distanceMaximun *=outils.Global.poidDistancePerteVolonteFinale;
		
		return distanceMaximun;
	}
	
	/**
	 * Calcule s'il restera sufisament de volonte finale pour boire un verre
	 * dans le stand choisie
	 * 
	 * @param stand
	 * @return vrai s'il serait peut etre possible de boire dans le stand
	 *         choisie, faux sinon
	 */
	public boolean peutSeDeplacerVersCeBar(TheGame quaranteDeux,String debitDeBoisson){
		Stand stand = this.listeDesStandNonVisite.get(debitDeBoisson);
		float coutDistance = coutDuDeplacementVers(stand.getCoordonnees());
		
		outils.ToString.toString("On est dans peutSeDeplacerVersCeBar dans class.Agent");
		outils.ToString.toString("liste des stand non visite" + this.listeDesStandNonVisite);
		outils.ToString.toString("cout en distance "+ coutDistance + " volonte : " + this.listeDeLaVolontePourStand.get(stand.getOwner()) + " pour le bar de " + stand.getOwner());
		
		if((this.listeDeLaVolontePourStand.get(stand.getOwner())-coutDistance)>outils.Global.volonteMinPourAllerVersUnStand){
			outils.ToString.toString("Le bar :" + debitDeBoisson + " est choisie car Volonte : "
					+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " cout de la distance : " + coutDistance);
			return true;
		}
		else{
			outils.ToString.toString("Le bar :" + debitDeBoisson + " est trop loin car Volonte : "
					+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " cout de la distance : " + coutDistance);
			return false;
		}
	}
	
	/**
	 * tente de boire une boisson dans le stand d'un joueur
	 * @param player
	 * @return
	 */
	public boolean commanderUneBoisson(TheGame leMonde, String debitDeBoisson) {
		float coutBoissonVF;
		int i_drinks = 0;
		boolean aBue = false;
		ArrayList<DrinkInfo> boissonPropose = new ArrayList<>(
				leMonde.getListePlayerInfo().get(debitDeBoisson).getDrinksOffered());
		
		while (this.aBueAujourdhui == false && i_drinks < boissonPropose.size()) {
			coutBoissonVF = boissonPropose.get(i_drinks).getCoutEnVolonteFinalePourBoire();

			if (this.listeDeLaVolontePourStand.get(debitDeBoisson) > coutBoissonVF) {
				if (this.veutBoissonFroide == boissonPropose.get(i_drinks).getIsCold()
						&& this.veutBoissonSansAlcool == boissonPropose.get(i_drinks).getIsHasAlcohol()) {

					aBue = boissonPropose.get(i_drinks).demandeDeBoire(debitDeBoisson, 1);
					if (aBue == true) {
						this.aBueAujourdhui = true;
						outils.ToString.toString("!!!Client a bue "+  boissonPropose.get(i_drinks).toString() + " chez " + debitDeBoisson);
						return true;
					}
				}
			}
			i_drinks++;
		}

		this.listeDesStandNonVisite.remove(debitDeBoisson);
		return false;

	}
	

	
	/**
	 * 
	 * @param listeItemByPlayer
	 */
	public void generationDeLaVolonteFinale(HashMap<String, ArrayList<MapItem>> listeItemByPlayer){
		this.listeDeLaVolontePourStand = new HashMap<String, Float>();
		float volonte =0f;
		float influancePub = 0f;
		for(String playerName : this.listeDesStandNonVisite.keySet()){
			this.listeDeLaVolontePourStand.put(playerName, 0f);
			volonte =0f;
			for(MapItem mapItem : listeItemByPlayer.get(playerName)){
				 influancePub =this.calculerInfluancePub(mapItem);
				 
				 volonte += influancePub;
				 
				 outils.ToString.toString("influance pub : " + influancePub + " volonte resultante : " + volonte);
				this.listeDeLaVolontePourStand.put(playerName, volonte);
			}
		}
		
		//on recupere la liste des stand trie
		
		outils.ToString.toString("Voila la volonteFinale :" + this.listeDeLaVolontePourStand);
		//je suis un idiot j'ai perdue 2 h pour ca, mais quel cretin
		//this.listeDesStandTrie = trierCleHasmapStringFloatDescendant(this.listeDeLaVolontePourStand);
		
		HashMap<String, Float> pourTrie = new HashMap<String, Float>(this.listeDeLaVolontePourStand);
		this.listeDesStandTrie = trierCleHasmapStringFloatDescendant(pourTrie);
		
	}
	
	/**
	 * Renvoie une arraylist avec les cle de la HasMap, de la meilleure valeur, à la plus mauvaise.
	 * @param aTrier
	 * @return
	 */
	public ArrayList<String> trierCleHasmapStringFloatDescendant(HashMap<String, Float> aTrier){
		ArrayList<String> listeTrie = new ArrayList<String>();
		String cleMeilleureValeur = "";
		float meilleureValeur;
		while(!aTrier.isEmpty()){
			meilleureValeur=0f;
			for(String cle : aTrier.keySet()){
				if(aTrier.get(cle)>meilleureValeur){
					meilleureValeur = aTrier.get(cle);
					cleMeilleureValeur = cle;
				}
			}
			listeTrie.add(cleMeilleureValeur);
			aTrier.remove(cleMeilleureValeur);
		}
		
		outils.ToString.toString("et voila la version trie : " + listeTrie);
		return listeTrie;
	}

	
	

	
	
	public HashMap<String, Stand> getListeDesStandNonVisite() {
		return listeDesStandNonVisite;
	}

	public void setListeDesStandNonVisite(HashMap<String, Stand> listeDesStand) {
		this.listeDesStandNonVisite = listeDesStand;
	}

	public HashMap<String, Float> getListeDeLaVolontePourStand() {
		return listeDeLaVolontePourStand;
	}

	public void setListeDeLaVolontePourStand(HashMap<String, Float> listeDeLaVolontePourStand) {
		this.listeDeLaVolontePourStand = listeDeLaVolontePourStand;
	}

	public boolean getIsaBueAujourdhui() {
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

	public ArrayList<String> getListeDesStandTrie() {
		return listeDesStandTrie;
	}

	public void setListeDesStandTrie(ArrayList<String> listeDesStandTrie) {
		this.listeDesStandTrie = listeDesStandTrie;
	}
	
	

}
