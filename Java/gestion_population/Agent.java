package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import outils.Global;
import outils.Meteo;



/**
 * Voila la classe qui simule les clients
 * @author atila
 *
 */
public class Agent {

	private Coordonnees coordonnees;

	private float motivation;
    // definie la volonte de boire une boisson
	private HashMap<String, Stand> listeDesStandNonVisite;
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
		this.listeDeLaVolontePourStand = new HashMap<String, Float>();
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

			motivation *= outils.OutilsCalculs.bonusDeMotivationSelonLaMeteo(meteo);
			
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

		if (outils.OutilsCalculs.pourcentagePopulationVoulantUneBoissonSelonLaMeteo(meteo) >= des) {
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

//	/**
//	 * Calcul du bonus de volonte finale d'une pub sur un client
//	 * avec prise en compte de la distance en fonction de la meteo
//	 * et de la motivation du client
//	 * 
//	 * @param playerName
//	 * @param mapItem
//	 */
//	public void calculerGainVolonteFinaleParUnePub(String playerName, MapItem mapItem, Meteo meteo) {
//
//		// petite verification que la valeur existe
//		if (this.listeDeLaVolontePourStand.get(playerName) == null) {
//			this.listeDeLaVolontePourStand.put(playerName, 1f);
//		}
//
//		float volonteFinale = this.listeDeLaVolontePourStand.get(playerName).floatValue();
//		volonteFinale += ((this.calculerInfluancePub(mapItem) * outils.Global.poidInfluencePub)
//				- (outils.OutilsCalculs.calculerDistance(this, mapItem)
//						* outils.Global.poidDistancePerteVolonteFinale));
//		this.listeDeLaVolontePourStand.put(playerName, volonteFinale);
//	}

	/**
	 * Calcule l'influence d'une pub sur un client
	 * 
	 * @param client
	 * @param mapItem
	 * @return
	 */
	private float calculerInfluancePub(MapItem mapItem) {
		outils.ToString.toStringVousEtesIci(" class.Agent method clacluler InfluancePub");
		float distance = outils.OutilsCalculs.calculerDistance(this, mapItem);
		float influancePub = (float) ((mapItem.getInfluence() * outils.Global.poidInfluencePub)
				/ (Math.pow((distance+0.01), 2)));

		
		outils.ToString.ecrireUneTrace("influance pub : " + influancePub);
		outils.ToString.toStringMath("Valeur infuancePub : " + influancePub);

		return influancePub * outils.Global.poidCalculInfluence;
	}
	
	
	/**
	 * Calcule l'influence d'une pub sur un client
	 * 
	 * @param client
	 * @param mapItem
	 * @return
	 */
	private float calculerInfluancePub2(MapItem mapItem) {
		outils.ToString.toStringVousEtesIci(" class.Agent method clacluler InfluancePub");
		float distance = outils.OutilsCalculs.calculerDistance(this, mapItem);

		float influancePub = (float) ((mapItem.getInfluence() * outils.Global.poidInfluencePub)
		/(distance+0.01));
		
		outils.ToString.ecrireUneTrace("influance pub : " + influancePub);
		outils.ToString.toStringMath("Valeur infuancePub : " + influancePub);

		return influancePub;
	}


	/**
	 * Done le cout en volonteFInale pour alle au stand donne pour ce client.
	 * on multiplie cette valeur par un poid
	 * @param coordonneesStand
	 * @return
	 */
	private float coutDuDeplacementVers(Coordonnees coordonneesStand) {
		float coutEnVolontePourAllerAuStand = outils.OutilsCalculs.calculerDistance(this.coordonnees, coordonneesStand);
		coutEnVolontePourAllerAuStand *= outils.Global.poidDistancePerteVolonteFinale;
		outils.ToString.ecrireUneTrace("Cout en volonte pour aller au stand : " + coutEnVolontePourAllerAuStand);
		return coutEnVolontePourAllerAuStand;
	}
	
	/**
	 * Done le cout en volonteFInale pour alle au stand donne pour ce client.
	 * on multiplie cette valeur par un poid
	 * @param coordonneesStand
	 * @return
	 */
	private float coutDuDeplacementVers2(Coordonnees coordonneesStand) {
		float coutEnVolontePourAllerAuStand = outils.OutilsCalculs.calculerDistance(this.coordonnees, coordonneesStand);
		coutEnVolontePourAllerAuStand = (float) Math.pow(coutEnVolontePourAllerAuStand, outils.Global.poidCoutDeplacementPuissanceFormule2);
		outils.ToString.ecrireUneTrace("Cout en volonte pour aller au stand : " + coutEnVolontePourAllerAuStand);
		return coutEnVolontePourAllerAuStand;
	}

	/**
	 * Calcule s'il restera sufisament de volonte finale pour boire un verre
	 * dans le stand choisie
	 * 
	 * @param stand
	 * @return vrai s'il serait peut etre possible de boire dans le stand
	 *         choisie, faux sinon
	 * @author atila
	 */
	public boolean choisirLeBarOuAller(TheGame quaranteDeux, String debitDeBoisson) {
		outils.ToString.toStringVousEtesIci("On est dans peutSeDeplacerVersCeBar dans class.Agent");
		Stand stand = this.listeDesStandNonVisite.get(debitDeBoisson);
		float coutDistance = coutDuDeplacementVers(stand.getCoordonnees());

		outils.ToString.toStringListe("liste des stand non visite" + this.listeDesStandNonVisite);
		outils.ToString.toStringMath("cout en distance " + coutDistance + " volonte : "
				+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " pour le bar de " + stand.getOwner());

		if ((this.listeDeLaVolontePourStand.get(stand.getOwner())
				- coutDistance) > outils.Global.volonteMinPourAllerVersUnStand) {
			outils.ToString.toStringDiver("Le bar :" + debitDeBoisson + " est choisie car Volonte : "
					+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " cout de la distance : " + coutDistance);
			return true;
		} else {
			outils.ToString.toStringDiver("Le bar :" + debitDeBoisson + " est trop loin car Volonte : "
					+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " cout de la distance : " + coutDistance);
			return false;
		}
	}
	
	
	/**
	 * Calcule s'il restera sufisament de volonte finale pour boire un verre
	 * dans le stand choisie
	 * 
	 * @param stand
	 * @return vrai s'il serait peut etre possible de boire dans le stand
	 *         choisie, faux sinon
	 * @author atila
	 */
	public boolean choisirLeBarOuAller2(TheGame quaranteDeux, Stand stand) {
		outils.ToString.toStringVousEtesIci("On est dans choisirLeBarOuAller2 dans class.Agent");

		float coutDistance;
		float volonteFinaleDuBar;

		outils.ToString.toStringDebug("listeDesStandNonVisite : " + listeDesStandNonVisite + " stand en cour : " + stand
				+ " avec proprio : " + stand.getOwner());
		coutDistance = coutDuDeplacementVers2(stand.getCoordonnees());
		outils.ToString.ecrireUneTrace("Stand : " + stand.toString() + " cout en deplacement : " + coutDistance
				+ " volonteMin a l'arrive autorise : " + outils.Global.volonteMinPourAllerVersUnStand);
		volonteFinaleDuBar = this.listeDeLaVolontePourStand.get(stand.getOwner()).floatValue();
		volonteFinaleDuBar -= -coutDistance;
		this.listeDeLaVolontePourStand.put(stand.getOwner(), volonteFinaleDuBar);
		if (this.listeDeLaVolontePourStand.get(stand.getOwner()).floatValue() > outils.Global.volonteMinPourAllerVersUnStand) {
			outils.ToString.toStringDiver("Le bar :" + stand.getOwner() + " est choisie car Volonte : "
					+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " cout de la distance : " + coutDistance);
			return true;
		} else {
			outils.ToString.toStringDiver("Le bar :" + stand.getOwner() + " est trop loin car Volonte : "
					+ this.listeDeLaVolontePourStand.get(stand.getOwner()) + " cout de la distance : " + coutDistance);
			return false;
		}

	}

	/**
	 * tente de boire une boisson dans le stand d'un joueur
	 * 
	 * @param player
	 * @return
	 */
	public boolean commanderUneBoisson(TheGame leMonde, String debitDeBoisson) {
		outils.ToString.toStringVousEtesIci("On est dans commanderUneBoisson dans class.Agent");
		float coutBoissonVF;
		int i_drinks = 0;
		boolean aBue = false;
		ArrayList<DrinkInfo> boissonPropose = new ArrayList<>(
				leMonde.getListePlayerInfo().get(debitDeBoisson).getDrinksOffered());

		while (this.aBueAujourdhui == false && i_drinks < boissonPropose.size()) {
			coutBoissonVF = boissonPropose.get(i_drinks).getCoutEnVolonteFinalePourBoire();
			
			outils.ToString.ecrireUneTrace("cout boisson : " + coutBoissonVF);
			if (this.listeDeLaVolontePourStand.get(debitDeBoisson) > coutBoissonVF) {
				if (this.veutBoissonFroide == boissonPropose.get(i_drinks).getIsCold()
						&& this.veutBoissonSansAlcool == boissonPropose.get(i_drinks).getIsHasAlcohol()) {

					aBue = boissonPropose.get(i_drinks).demandeDeBoire(debitDeBoisson, 1);
					
					//si on est en simulation, on test juste si le client va boire.
					if (false == outils.Global.requeteServeurVraiOuFauxPourSimulationEnLocal){
						aBue = true;
					}
					
					outils.ToString.toStringMath("Un client essaie de boire cout boisson : "+ coutBoissonVF + 
							" volonte qu'a le client : "+this.listeDeLaVolontePourStand.get(debitDeBoisson) +
							" boisson en stock : " + aBue);
					if (aBue == true) {
						this.aBueAujourdhui = true;
						outils.ToString.toStringDiver("!!!  Client a bue " + boissonPropose.get(i_drinks).toString()
								+ " chez " + debitDeBoisson);
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
	 * tente de boire une boisson dans le stand d'un joueur
	 * 
	 * @param player
	 * @return
	 */
	public boolean commanderUneBoisson2(TheGame leMonde, String debitDeBoisson) {
		//On oublie pas de se deplacer sur le bar
		outils.ToString.toStringDebug("Attention coordonne du bar : "+ leMonde.getListeDesStand().get(debitDeBoisson).getCoordonnees().toString()+" pour verification qu'il bouge pas avec le client");
		this.setCoordonnees(leMonde.getListeDesStand().get(debitDeBoisson).getCoordonnees());
		
		outils.ToString.toStringVousEtesIci("On est dans commanderUneBoisson2 dans class.Agent");
		float coutBoissonVF;
		int i_drinks = 0;
		boolean aBue = false;
		//on recupere la liste des boissons proposer par le stand
		ArrayList<DrinkInfo> boissonPropose = new ArrayList<>(
				leMonde.getListePlayerInfo().get(debitDeBoisson).getDrinksOffered());

		//si le client a pas bu et qu'il reste une boisson
		while (this.aBueAujourdhui == false && i_drinks < boissonPropose.size()) {
			coutBoissonVF = boissonPropose.get(i_drinks).getCoutEnVolonteFinalePourBoire();
			outils.ToString.ecrireUneTrace("cout boisson : " + coutBoissonVF);
			
			//s'il a envie de boire
			if (this.listeDeLaVolontePourStand.get(debitDeBoisson) > coutBoissonVF
					&& this.veutBoissonFroide == boissonPropose.get(i_drinks).getIsCold()
					&& this.veutBoissonSansAlcool == boissonPropose.get(i_drinks).getIsHasAlcohol()) {

				aBue = boissonPropose.get(i_drinks).demandeDeBoire(debitDeBoisson, 1);

				// si on est en simulation, on test juste si le client va boire.
				if (false == outils.Global.requeteServeurVraiOuFauxPourSimulationEnLocal) {
					aBue = true;
				}

				outils.ToString.toStringMath(
						"Un client essaie de boire cout boisson : " + coutBoissonVF + " volonte qu'a le client : "
								+ this.listeDeLaVolontePourStand.get(debitDeBoisson) + " boisson en stock : " + aBue);
				if (aBue == true) {
					this.aBueAujourdhui = true;
					outils.ToString.toStringDiver(
							"!!!  Client a bue " + boissonPropose.get(i_drinks).toString() + " chez " + debitDeBoisson);
					return true;
				}
			}
			
			i_drinks++;
		}

		// il n'y avait rien en stock qui nous convienne on boude
		outils.ToString.ecrireUneTrace("Un client n'a pas trouver ce qu'il voulait chez : " + debitDeBoisson);
		this.listeDesStandNonVisite.remove(debitDeBoisson);
		return false;

	}

	/**
	 * On genere la volonte finale
	 * On recherche les stand non visite, puis pour chacun d'entre eux
	 * on genere la volonteLie
	 * @param listeItemByPlayer
	 */
	public void generationDeLaVolonteFinale(HashMap<String, ArrayList<MapItem>> listeItemByPlayer) {
		outils.ToString.toStringVousEtesIci("On est dans generationDeLaVolonteFinale dans class.Agent");

		this.listeDeLaVolontePourStand = new HashMap<String, Float>();
		float volonte = 0f;
		float influancePub = 0f;
		outils.ToString.toStringListe("Voila la liste des standNonVisite : " +this.listeDesStandNonVisite);
		for (String playerName : this.listeDesStandNonVisite.keySet()) {
			this.listeDeLaVolontePourStand.put(playerName, 0f);
			volonte = 0f;
			outils.ToString.toStringListe("Voila la liste des mapItem de : " + playerName +" : " +listeItemByPlayer);
			for (MapItem mapItem : listeItemByPlayer.get(playerName)) {
				influancePub = this.calculerInfluancePub(mapItem);

				volonte += influancePub;

				outils.ToString.toStringMath("influance pub : " + influancePub + " volonte resultante : " + volonte);
				this.listeDeLaVolontePourStand.put(playerName, volonte);
			}
		}

		// on recupere la liste des stand trie

		outils.ToString.toStringListe("Voila la volonteFinale :" + this.listeDeLaVolontePourStand);
		// je suis un idiot j'ai perdue 2 h pour ca, mais quel cretin
		// this.listeDesStandTrie =
		// trierCleHasmapStringFloatDescendant(this.listeDeLaVolontePourStand);

		HashMap<String, Float> pourTrie = new HashMap<String, Float>(this.listeDeLaVolontePourStand);
		this.listeDesStandTrie = trierCleHasmapStringFloatDescendant(pourTrie);

	}
	
	
	
	/**
	 * On genere la volonte finale
	 * On recherche les stand non visite, puis pour chacun d'entre eux
	 * on genere la volonteLie
	 * @param listeItemByPlayer
	 */
	public void generationDeLaVolonteFinale2(HashMap<String, ArrayList<MapItem>> listeItemByPlayer) {
		outils.ToString.toStringVousEtesIci("On est dans generationDeLaVolonteFinale2 dans class.Agent");

		this.listeDeLaVolontePourStand = new HashMap<String, Float>();
		float volonte = 0f;
		float influancePub = 0f;
		outils.ToString.toStringListe("Voila la liste des standNonVisite : " +this.listeDesStandNonVisite);
		//Pour chaque Stand qui n'a pas encore ete visité
		for (String playerName : this.listeDesStandNonVisite.keySet()) {
			this.listeDeLaVolontePourStand.put(playerName, 0f);
			volonte = 0f;
			outils.ToString.toStringListe("Voila la liste des mapItem de : " + playerName +" : " +listeItemByPlayer);
			for (MapItem mapItem : listeItemByPlayer.get(playerName)) {
				influancePub = this.calculerInfluancePub2(mapItem);

				volonte += influancePub;
			}
			outils.ToString.toStringMath("influance pub : " + influancePub + " volonte resultante : " + volonte);
			this.listeDeLaVolontePourStand.put(playerName, (volonte+this.motivation));
		}

		// on recupere la liste des stand trie

		outils.ToString.toStringListe("Voila la volonteFinale :" + this.listeDeLaVolontePourStand);
		outils.ToString.ecrireUneTrace("Voila la volonteFinale :" +this.listeDeLaVolontePourStand.toString());
		HashMap<String, Float> pourTrie = new HashMap<String, Float>(this.listeDeLaVolontePourStand);
		this.listeDesStandTrie = trierCleHasmapStringFloatDescendant(pourTrie);
		outils.ToString.ecrireUneTrace("Stand avec meilleur volonte trie : " + this.listeDesStandTrie.toString());
	}

	/**
	 * Renvoie une arraylist avec les cle de la HasMap, de la meilleure valeur,
	 * à la plus mauvaise.
	 * 
	 * @param aTrier
	 * @return
	 */
	public ArrayList<String> trierCleHasmapStringFloatDescendant(HashMap<String, Float> aTrier) {
		outils.ToString.toStringVousEtesIci("On est dans trierCleHasmapStringFloatDescendant dans class.Agent");
		ArrayList<String> listeTrie = new ArrayList<String>();
		String cleMeilleureValeur = "";
		float meilleureValeur;
		while (!aTrier.isEmpty()) {
			meilleureValeur = 0f;
			for (String cle : aTrier.keySet()) {
				if (aTrier.get(cle) > meilleureValeur) {
					meilleureValeur = aTrier.get(cle);
					cleMeilleureValeur = cle;
				}
			}
			listeTrie.add(cleMeilleureValeur);
			aTrier.remove(cleMeilleureValeur);
		}

		outils.ToString.toStringListe("et voila la version trie : " + listeTrie);
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
