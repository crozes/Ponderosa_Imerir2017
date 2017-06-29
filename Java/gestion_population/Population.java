package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import outils.Global;
import outils.Meteo;

/**
 * Class population qui contient les client et lance la simulation de leurs
 * comportements
 * 
 * @author atila
 *
 */
public class Population {

	private ArrayList<Agent> population;

	private float latitudeMax;
	private float LatitudeMin;
	private float longitudeMax;
	private float LongitudeMin;
	private float nombreDeClient;

	public Population() {
		this.population = new ArrayList<Agent>();
	}

	public Population(float latitudeMax, float LatitudeMin, float longitudeMax, float LongitudeMin) {
		this.latitudeMax = latitudeMax;
		this.LatitudeMin = LatitudeMin;
		this.longitudeMax = longitudeMax;
		this.LongitudeMin = LongitudeMin;

		this.population = new ArrayList<Agent>();
	}

	public Population(float latitudeMax, float LatitudeMin, float longitudeMax, float LongitudeMin, int nbJoueur,
			Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {

		this.population = new ArrayList<Agent>();
		nombreDeClient = calculDuNombreDeClient(nbJoueur, meteo, periodeJournee);

		for (int i = 0; i < nombreDeClient; i++) {
			this.population.add(this.creerUnAgent(meteo, periodeJournee, listeDesStand));
		}
	}

	/**
	 * Creer un nouveau client avec les bon paramettre, position et envie
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @param listeDesStand
	 * @return
	 */
	private Agent creerUnAgent(Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {
		Agent client = new Agent(meteo, periodeJournee, listeDesStand);

		client.setCoordonnees(calculerPositionClient(meteo, periodeJournee));

		return client;
	}

	/**
	 * Genere de maniere aleatoire une position
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @return Coordonnes aleatoire
	 */
	private Coordonnees calculerPositionClient(Meteo meteo, Meteo periodeJournee) {
		Random random = new Random();
		float manipGaussien = (float) ThreadLocalRandom.current().nextGaussian();
//		float latitude = this.LatitudeMin + random.nextFloat() * (this.latitudeMax - this.LatitudeMin);
//		float longitude = this.LongitudeMin + random.nextFloat() * (this.longitudeMax - this.LongitudeMin);
		float latitude = (-400) + random.nextFloat() * ((400) - (-400));
		float longitude = (-250) + random.nextFloat() * ((250) - (-250));
		
		


		return (new Coordonnees((latitude * manipGaussien)+400, (longitude * manipGaussien)+250));
	}

	/**
	 * Genere la population
	 * 
	 * @param latitudeMax
	 * @param LatitudeMin
	 * @param longitudeMax
	 * @param LongitudeMin
	 * @param nbJoueur
	 *            permet d'augmenter la taille de la population en fonction du
	 *            nombre de joueur
	 * @param meteo
	 *            affecte la consonmation de boisson froide, la motivation et
	 *            impact l'effort pour se deplacer
	 * @param periodeJournee
	 *            augmente l'envie de boisson alcooliser le soir
	 */
	public void genererPopulation(float latitudeMax, float LatitudeMin, float longitudeMax, float LongitudeMin,
			int nbJoueur, Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {
		nombreDeClient = calculDuNombreDeClient(nbJoueur, meteo, periodeJournee);

		// on fixe la taille de la map
		this.latitudeMax = latitudeMax;
		this.LatitudeMin = LatitudeMin;
		this.longitudeMax = longitudeMax;
		this.LongitudeMin = LongitudeMin;

		outils.ToString.toStringVousEtesIci("On genere les pigeons ... les clients pardons");

		for (int i = 0; i < nombreDeClient; i++) {
			this.population.add(this.creerUnAgent(meteo, periodeJournee, listeDesStand));
		}
	}

	/**
	 * Essaie de faire boire la population, si un client a été attiré par un bar
	 * le matin et qu'il n'a pas trouver de boisson a son gout (par rapport au
	 * stock) il n'essaira pas dis retourner le soir (il boude)
	 * 
	 * La pub et sa motivation creer sa volontéFinale de vouloir boire dans un
	 * bar il va dans le bar qui lui procure la plus grande volonte finale et
	 * qui n'est pas trop loin pour qu'il lui reste de la volontéFinale une fois
	 * arrivé
	 * 
	 * se deplacer diminue ca volonté finale, donc avant de se deplacer, on voie
	 * si sa volonté finale ne sera pas trop basse
	 * 
	 * une fois arrivé il voie s'il y a une boisson qu'il aimerai boire par
	 * rapport a ces envie et aussi s'il lui reste suffisament de volonté de
	 * commandé. Il tente de boire la boisson la plus chere selon ca volonté Une
	 * boisson à un tot de consomation de volontéFinale calculé par rapport a
	 * son prix
	 * 
	 * 
	 * s'il peut boire il fait la demande, s'il y a du stock (requete au
	 * serveur) il boit et on le retire de la population
	 * 
	 * s'il n'a pas bue, il divise sa motivation par 2 et recherche un nouveau
	 * bar (et tout recommence)
	 * 
	 * 
	 * 
	 * Explication du fonctionnement de cette grosse fonction : On agit client
	 * par client tant que le client n'a pas bu, a la volonté de continué, a
	 * encore un stand qu'il peut visiter on calcule la volontéFInale de chaque
	 * stand pour le client on recupere la liste trie des stand par ordre de
	 * volonté finale
	 * 
	 * on voie si on peut allé au premier sinon on va au suivant ...
	 * 
	 * on va au bar -> changement de coordonnees on est sur le bar
	 * 
	 * on essaie de boire post boire oui -> stand -> recipe +1 vente -> on
	 * retire l'agent
	 *
	 * non -> on essaie boisson suivante il n'y a pas de boisson -> on retire le
	 * stand -> on relance sur les stand qu'il reste
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Population : faireBoireLaPopulation Agent : generationDeLaVolonteFinale
	 * Agent : calculerInfluancePub Agent : trierCleHasmapStringFloatDescendant
	 * 
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @param ranking
	 *            cle de la hasmap listeItemByPlayer
	 * @param listeItemByPlayer
	 */
	public void faireBoireLaPopulation(TheGame laPartie) {
		int numeroClient = 0;
		Agent client;
		// for (Agent client : this.population) {
		for (int i_client = 0; i_client < this.population.size(); i_client++) {
			client = this.population.get(i_client);
			numeroClient++;
			outils.ToString.toStringDiver("On s'occupe du client : " + numeroClient + " sur : " + population.size());
			outils.ToString
					.toStringDiver("aBue = " + client.getIsaBueAujourdhui() + " motivation : " + client.getMotivation()
							+ " motivation min autorise : " + outils.Global.minMotivationAvantDeNePlusVouloirBoire
							+ " nombre de stand qu'il reste a visiter : " + client.getListeDesStandNonVisite().size());
			do {

				// on genere la volontéFinale
				outils.ToString.toStringDebug(laPartie.getListeMapItemJoueur());
				client.generationDeLaVolonteFinale(laPartie.getListeMapItemJoueur());

				// on cherche sur qu'elle bar on va.
				int i_stand = -1;
				boolean peutSeDeplacer = false;
				while (i_stand < client.getListeDesStandTrie().size() - 1 && peutSeDeplacer == false) {
					i_stand++;
					outils.ToString.toStringDebug("i : " + i_stand + " size : " + client.getListeDesStandTrie().size()
							+ "probleme d'index Population faireBoire...");
					peutSeDeplacer = client.choisirLeBarOuAller(laPartie, client.getListeDesStandTrie().get(i_stand));

				}

				if (i_stand != -1) {
					// le client passe sa commande
					outils.ToString.toStringDiver("Le client :" + numeroClient + " passe commande chez : "
							+ client.getListeDesStandTrie().get(i_stand));
					client.commanderUneBoisson(laPartie, client.getListeDesStandTrie().get(i_stand));
				}

			} while (client.getIsaBueAujourdhui() == false
					&& client.getMotivation() > outils.Global.minMotivationAvantDeNePlusVouloirBoire
					&& client.getListeDesStandNonVisite().size() > 0);
			// Le client a bue, il n'a plus soif, on peut le retirer du jeu
			if (client.getIsaBueAujourdhui() == true) {
				outils.ToString.toStringDiver(
						"----------------------> On a retirer un client. Il reste : " + population.size() + " clients");
				this.population.remove(client);
			}

		}

	}

	/**
	 * Meme fonctionnement que faireBoireLaPopulation, mais en corrigeant
	 * quelques erreur. La version 1 reste car elle est malgré tout
	 * fonctionnelle
	 * 
	 * @param laPartie
	 */
	public void faireBoireLaPopulation2(TheGame laPartie) {
		// pour chaque client faire

		int vendeManip = 0;

		boolean aPuBoire = false;
		Agent client;

		for (int i_agent = 0; i_agent < this.population.size(); i_agent++) {
			client = population.get(i_agent);
			outils.ToString.ecrireUneTrace("\n\n Client : " + i_agent + "\n\n");
			// generer la volonterFinale de tous les stands.

			// genere volonteFinale et la liste des stand trie a partir des
			// stand non visite
			client.generationDeLaVolonteFinale2(laPartie.getListeMapItemJoueur());

			do {

				// on essaie de voir si on peut aller dans un des bar
				int i_stand = -1;
				boolean peutSeDeplacer = false;

				outils.ToString.toStringDebug("stand trie sont : " + client.getListeDesStandTrie());
				while (i_stand < client.getListeDesStandTrie().size() && peutSeDeplacer == false) {

					i_stand++;
					outils.ToString.toStringDebug("i : " + i_stand + " size : " + client.getListeDesStandTrie().size()
							+ "probleme d'index Population faireBoire...");
					
					if(client.getListeDesStandTrie().size()>0){
						peutSeDeplacer = client.choisirLeBarOuAller2(laPartie,
								laPartie.getListeDesStand().get(client.getListeDesStandTrie().get(i_stand)));
					}


				}

				outils.ToString.toStringDebug("-------------- i_stand: " + i_stand);
				// si on peut se deplacer alors on va tenter d'y boire une
				// bierre

				if (i_stand != -1) {
					aPuBoire = client.commanderUneBoisson2(laPartie, client.getListeDesStandTrie().get(i_stand));

					if (aPuBoire == true) {
						client.setaBueAujourdhui(true);

						outils.ToString.toStringDiver("----------------------> On a retirer un client. Il reste : "
								+ population.size() + " clients");

						outils.ToString.ecrireUneTrace("----------------------> On a retirer un client. Il reste : "
								+ population.size() + " clients");

						vendeManip = laPartie.getListePlayerInfo().get(client.getListeDesStandTrie().get(i_stand))
								.getSales();
						vendeManip++;
						laPartie.getListePlayerInfo().get(client.getListeDesStandTrie().get(i_stand))
								.setSales(vendeManip);
						// this.population.remove(client);
					} else {
						// le client boude
						outils.ToString.toStringDebug("Le bar : " + client.getListeDesStandTrie().get(i_stand)
								+ " est retirer de la liste des bar a visite car il n'avait rien qui convenait");
						client.getListeDesStandNonVisite().remove(client.getListeDesStandTrie().get(i_stand));
						client.setMotivation((client.getMotivation() / 2));
					}
				}

			} while (aPuBoire == false && client.getMotivation() > outils.Global.minMotivationAvantDeNePlusVouloirBoire
					&& client.getListeDesStandNonVisite().size() > 0);

		}

		System.out.println(laPartie.getListePlayerInfo().toString());
		// outils.ToString.toStringDiver("Nombre de client qui ont bu : " +
		// ventes.toString());
		// outils.ToString.ecrireUneTrace("Nombre de client qui ont bu : " +
		// ventes.toString());
	}

	/**
	 * Renvoie le nombre de client calculé par rapport aux nombres des joueurs,
	 * la meteo du jour et la periode de la journee
	 * 
	 * Pour le moment, la meteo n'est pas prise en compte
	 * 
	 * @param nbJoueur
	 * @param meteo
	 * @param periodeJournee
	 * @return
	 */
	private int calculDuNombreDeClient(int nbJoueur, Meteo meteo, Meteo periodeJournee) {
		int nbClient = 0;
		for (int i = 0; i < nbJoueur; i++) {
			nbClient += outils.OutilsCalculs.randomInt(Global.clientMinParJoueur, Global.clientMaxParJoueur);
		}

		return nbClient;
	}

	/**
	 * On deplace la population qui n'a pas bue
	 * 
	 * @param meteo
	 * @param periodeJournee
	 */
	public void mouvementDuMidi(Meteo meteo, Meteo periodeJournee) {
		for (Agent client : this.population) {
			client.setCoordonnees(this.calculerPositionClient(meteo, periodeJournee));

			client.calculerEnvieBoissonSansAlcool(meteo, periodeJournee);
			client.calculerBoissonFroideClient(meteo, periodeJournee);

		}
	}

	public String toString() {
		String toReturn = "";
		int boisson_froide = 0;
		int boisson_sans_alcool = 0;

		for (Agent a : population) {

			toReturn += a.getCoordonnees().toString();
			toReturn += ("Motivation:" + a.getMotivation() + "   Boisson froide: " + a.getIsVeutBoissonFroide()
					+ "   sansAlcool: " + a.getIsVeutBoissonSansAlcool());
			if (a.getIsVeutBoissonFroide() == true) {
				boisson_froide++;
			}
			if (a.getIsVeutBoissonSansAlcool() == true) {
				boisson_sans_alcool++;
			}
			toReturn += "  Distance avec centre: "
					+ outils.OutilsCalculs.calculerDistance(a.getCoordonnees(), new Coordonnees(0f, 0f));
			toReturn += "\n";
		}

		toReturn += ("Nombre d'agent sur la map:" + population.size() + "  veut boisson froide :" + boisson_froide
				+ "   sans alcool :" + boisson_sans_alcool);
		return toReturn;
	}

	public ArrayList<Agent> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Agent> population) {
		this.population = population;
	}

	public float getLatitudeMax() {
		return latitudeMax;
	}

	public void setLatitudeMax(float latitudeMax) {
		this.latitudeMax = latitudeMax;
	}

	public float getLatitudeMin() {
		return LatitudeMin;
	}

	public void setLatitudeMin(float latitudeMin) {
		LatitudeMin = latitudeMin;
	}

	public float getLongitudeMax() {
		return longitudeMax;
	}

	public void setLongitudeMax(float longitudeMax) {
		this.longitudeMax = longitudeMax;
	}

	public float getLongitudeMin() {
		return LongitudeMin;
	}

	public void setLongitudeMin(float longitudeMin) {
		LongitudeMin = longitudeMin;
	}

	public int getNombreDeClient() {
		return this.population.size();
	}

	public void setNombreDeClient(float nombreDeClient) {
		this.nombreDeClient = nombreDeClient;
	}

}
