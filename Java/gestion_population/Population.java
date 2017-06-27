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
			Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {

		this.population = new ArrayList<Agent>();
		nombreDeClient = calculDuNombreDeClient(nbJoueur, meteo, periodeJournee);

		for (int i = 0; i < nombreDeClient; i++) {
			this.population.add(this.creerUnAgent(meteo, periodeJournee, listeDesStand));
		}
	}

	private Agent creerUnAgent(Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {
		Agent client = new Agent(meteo, periodeJournee,listeDesStand);

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
			int nbJoueur, Meteo meteo, Meteo periodeJournee, HashMap<String, Stand> listeDesStand) {
		nombreDeClient = calculDuNombreDeClient(nbJoueur, meteo, periodeJournee);

		//on fixe la taille de la map
		this.latitudeMax = latitudeMax;
		this.LatitudeMin = LatitudeMin;
		this.longitudeMax = longitudeMax;
		this.LongitudeMin = LongitudeMin;
		
		outils.ToString.toString("On genere les pigeons");
		
		for (int i = 0; i < nombreDeClient; i++) {
			this.population.add(this.creerUnAgent(meteo, periodeJournee, listeDesStand));
		}
	}

	/**
	 * Essaie de faire boire la population, si un client a été attiré par un bar
	 * le matin et qu'il n'a pas trouver de boisson a son gout (par rapport au
	 * stock) il n'essaira pas dis retourner le soir (il boude)
	 * 
	 * La pub et sa motivation creer sa volontéFinale de vouloir boire dans un bar
	 * il va dans le bar qui lui procure la plus grande volonte finale et qui n'est pas trop loin pour qu'il lui reste
	 * de la volontéFinale une fois arrivé
	 * 
	 * se deplacer diminue ca volonté finale, donc avant de se deplacer, on voie si 
	 * sa volonté finale ne sera pas trop basse
	 * 
	 * une fois arrivé il voie s'il y a une boisson qu'il aimerai boire par rapport a ces envie et
	 * aussi s'il lui reste suffisament de volonté de commandé. Il tente de boire la boisson la plus chere selon ca volonté
	 * Une boisson à un tot de consomation de volontéFinale calculé par rapport a son prix
	 * 
	 * 
	 * s'il peut boire il fait la demande, s'il y a du stock (requete au serveur) il boit et on le retire de la population
	 * 
	 * s'il n'a pas bue, il divise sa motivation par 2 et recherche un nouveau bar (et tout recommence)
	 * 
	 * 
	 * 
	 * Explication du fonctionnement de cette grosse fonction :
	 * On agit client par client
	 * tant que le client n'a pas bu, a la volonté de continué, a encore un stand qu'il peut visiter
	 * 	on calcule la volontéFInale de chaque stand pour le client
	 * 	on recupere la liste trie des stand par ordre de volonté finale
	 *  
	 *  on voie si on peut allé au premier sinon on va au suivant ...
	 *  
	 *  on va au bar -> changement de coordonnees on est sur le bar
	 *  
	 *  on essaie de boire
	 *  	post boire
	 *  	oui 
	 *  		-> stand -> recipe +1 vente
	 *			-> on retire l'agent
	 *
	 *  	non -> on essaie boisson suivante
	 *  		il n'y a pas de boisson	
	 *  			-> on retire le stand 
	 *  			-> on relance sur les stand qu'il reste		
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  Population : faireBoireLaPopulation
	 *  	Agent : generationDeLaVolonteFinale
	 *  		Agent : calculerInfluancePub
	 *  		Agent : trierCleHasmapStringFloatDescendant
	 *  	

	 * @param meteo
	 * @param periodeJournee
	 * @param ranking
	 *            cle de la hasmap listeItemByPlayer
	 * @param listeItemByPlayer
	 */
	public void faireBoireLaPopulation(Meteo meteo, TheGame laPartie) {
		int numeroClient = 0;
		Agent client;
		//for (Agent client : this.population) {
		for(int i_client = 0; i_client<this.population.size(); i_client++){
			client = this.population.get(i_client);
			numeroClient++;
			outils.ToString.toString("On s'occupe du client : " + numeroClient + " sur : " + population.size());
			outils.ToString.toString("aBue = " + client.getIsaBueAujourdhui() + " motivation : "+ client.getMotivation() + 
					" motivation min autorise : " + outils.Global.minMotivationAvantDeNePlusVouloirBoire +
				" nombre de stand qu'il reste a visiter : " + client.getListeDesStandNonVisite().size()	);
			do{
			
			
				//on genere la volontéFinale
				client.generationDeLaVolonteFinale(laPartie.getListeMapItemJoueur());
					
				//on cherche sur qu'elle bar on va.
				int i_stand = -1;
				boolean peutSeDeplacer = false;
				do{
					i_stand++;
					outils.ToString.toString("i : " + i_stand + " size : " + client.getListeDesStandTrie().size() +"probleme d'index Population faireBoire...");
					peutSeDeplacer = client.peutSeDeplacerVersCeBar(laPartie, client.getListeDesStandTrie().get(i_stand));
					
				}while (i_stand < client.getListeDesStandTrie().size()-1 && peutSeDeplacer == false );
				
				//le client passe sa commande
				outils.ToString.toString("Le client :" + numeroClient + " passe commande chez : " + client.getListeDesStandTrie().get(i_stand));
				client.commanderUneBoisson(laPartie, client.getListeDesStandTrie().get(i_stand));
				
			}while(client.getIsaBueAujourdhui()==false && client.getMotivation()>outils.Global.minMotivationAvantDeNePlusVouloirBoire && client.getListeDesStandNonVisite().size()>0);
			//Le client  a bue, il n'a plus soif, on peut le retirer du jeu
			if (client.getIsaBueAujourdhui() == true){
				outils.ToString.toString("On a retirer un client. Il reste : "+ population.size()+" clients");
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
