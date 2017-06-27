package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import outils.Global;
import outils.Meteo;

public class Population {

	private ArrayList<Agent> population;

	private float latitudeMax;
	private float LatitudeMin;
	private float longitudeMax;
	private float LongitudeMin;
	private float nombreDeClient;

	public float test_motivationMax = 0;
	public float test_motivationMin = 1000000;
	
	public Population(){
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
			Meteo meteo, Meteo periodeJournee) {

		this.population = new ArrayList<Agent>();
		nombreDeClient = calculDuNombreDeClient(nbJoueur, meteo, periodeJournee);

		for (int i = 0; i < nombreDeClient; i++) {
			this.population.add(this.creerUnAgent(meteo, periodeJournee));
		}
	}

	private Agent creerUnAgent(Meteo meteo, Meteo periodeJournee) {
		Agent client = new Agent(meteo, periodeJournee);

		client.setCoordonnees(calculerPositionClient(meteo, periodeJournee));

		if (client.getMotivation() > this.test_motivationMax) {
			test_motivationMax = client.getMotivation();
		}
		if (client.getMotivation() < this.test_motivationMin) {
			test_motivationMin = client.getMotivation();
		}
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
		float latitude = this.LatitudeMin + random.nextFloat() * (this.latitudeMax - this.LatitudeMin);
		float longitude = this.LongitudeMin + random.nextFloat() * (this.longitudeMax - this.LongitudeMin);

		return (new Coordonnees(latitude, longitude));
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
			int nbJoueur, Meteo meteo, Meteo periodeJournee) {
		nombreDeClient = calculDuNombreDeClient(nbJoueur, meteo, periodeJournee);

		for (int i = 0; i < nombreDeClient; i++) {
			this.population.add(this.creerUnAgent(meteo, periodeJournee));
		}
	}

	/**
	 * Essaie de faire boire la population, si un client a été attiré par un bar
	 * le matin et qu'il n'a pas trouver de boisson a son gout (par rapport au
	 * stock) il n'essaira pas dis retourner le soir (il boude)
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @param ranking
	 *            cle de la hasmap listeItemByPlayer
	 * @param listeItemByPlayer
	 */
	public void faireBoireLaPopulation(Meteo meteo, Meteo periodeJournee, ArrayList<String> ranking,
			HashMap<String, ArrayList<MapItem>> listeItemByPlayer) {
		for (Agent client : this.population) {
			while(client.getIsaBueAujourdhui()==false && client.getMotivation()>outils.Global.minMotivationAvantDeNePlusVouloirBoire && client.getListeDesStand().size()>0){
				for (String playerName : ranking) {
					for (MapItem mapItem : listeItemByPlayer.get(playerName))
						client.calculerGainVolonteFinaleParUnePub(playerName, mapItem);
				}
				//on recupere le meilleur stand
			}
			//Le client  a bue, il n'a plus soif, on peut le retirer du jeu
			//
			if (client.getIsaBueAujourdhui() == true){
				this.population.remove(client);
			}

		}

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
	
	
	public void mouvementDuMidi(Meteo meteo, Meteo periodeJournee){
		for(Agent client : this.population){
			client.setCoordonnees(this.calculerPositionClient(meteo, periodeJournee));
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

}
