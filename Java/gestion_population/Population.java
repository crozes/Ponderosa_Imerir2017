package gestion_population;

import java.util.ArrayList;
import java.util.Random;

import com.sun.javafx.scene.control.GlobalMenuAdapter;

import outils.Global;
import outils.Meteo;

public class Population {

	private ArrayList<Agent> population;
	
	private int latitudeMax;
	private int LatitudeMin;
	private int longitudeMax;
	private int LongitudeMin;
	private int nombreDeClient;
	
	
	public Population(int latitudeMax, int LatitudeMin, int longitudeMax, int LongitudeMin, int nbJoueur, Meteo meteo, Meteo periodeJournee){
		
		this.population= new ArrayList<Agent>();
		nombreDeClient = calculDuNombreDeClient( nbJoueur,  meteo,  periodeJournee);
		
		for(int i = 0 ; i<nombreDeClient; i++){
			this.population.add(new Agent(calculerPositionClient(meteo, periodeJournee),
					calculerMotivationClient(meteo, periodeJournee), calculerBoissonFroideClient(meteo, periodeJournee),
					calculerBoissonAlcoolCLient(meteo, periodeJournee)));
		}
	}
	
	
	private Agent creerUnAgent(Meteo meteo, Meteo periodeJournee) {
		Agent client = new Agent(calculerPositionClient(meteo, periodeJournee), calculerMotivationClient(meteo, periodeJournee),
				calculerBoissonFroideClient(meteo, periodeJournee), calculerBoissonAlcoolCLient(meteo, periodeJournee));

		return client;
	}
	
	
	private Coordonnees calculerPositionClient(Meteo meteo, Meteo periodeJournee){
		Random random = new Random();
		float latitude = this.LatitudeMin + random.nextFloat() * (this.latitudeMax - this.LatitudeMin);
		float longitude = this.LongitudeMin + random.nextFloat() * (this.longitudeMax - this.LongitudeMin);
		
		return (new Coordonnees(latitude, longitude));
	}
	
	private float calculerMotivationClient(Meteo meteo, Meteo periodeJournee){
		
		if(meteo == Meteo.heatwave){
			return 0f;
		}
		else{
			float motivation = outils.OutilsCalculs.randomFloat(Global.clientMinMotivation, Global.clientMinMotivation);
			
			motivation *= outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(meteo);
			
			return motivation;
		}
	}
	
	/**
	 * Lance un rand de 0 a 1, s'il est inferieur a la valeur lie a la meteo (Déplacement	15%rainny 30%cloudy 75%sunny 100%heatwave 0%thunderstorm
	 * @param meteo
	 * @param periodeJournee
	 * @return
	 */
	private boolean calculerBoissonFroideClient(Meteo meteo, Meteo periodeJournee){
		
		float des = outils.OutilsCalculs.randomFloat(0, 1);
		
		if(outils.OutilsCalculs.poidMeteoParcourBoissonFroide(meteo)>des){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * Prend en comtpe si on est le matin ou le soir (plus d'allcool le soir), prend en compte la meteo pour les valeur.
	 * 
	 * @param meteo
	 * @param periodeJournee
	 * @return
	 */
	private boolean calculerBoissonAlcoolCLient(Meteo meteo, Meteo periodeJournee){
		int des = outils.OutilsCalculs.randomInt(0, 1);
		
		if (des > 0 ){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Renvoie le nombre de client calculé par rapport aux nombres des joueurs, la meteo du jour et la periode de la journee
	 * 
	 * Pour le moment, la meteo n'est pas prise en compte
	 * 
	 * @param nbJoueur
	 * @param meteo
	 * @param periodeJournee
	 * @return
	 */
	private int calculDuNombreDeClient(int nbJoueur, Meteo meteo, Meteo periodeJournee){
		int nbClient = 0;
		for(int i =0; i<nbJoueur; i++){
			nbClient += outils.OutilsCalculs.randomInt(Global.clientMinParJoueur, Global.clientMaxParJoueur);
		}

		return nbClient;
	}
	
	/**
	 * Generation de la population
	 */
	
	
	/**
	 * La population ce déplace
	 */

	
}
