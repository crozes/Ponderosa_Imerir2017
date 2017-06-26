package gestion_population;

import java.util.ArrayList;
import java.util.Random;

import outils.Global;
import outils.Meteo;

public class Population {

	private ArrayList<Agent> population;
	
	private int latitudeMax;
	private int LatitudeMin;
	private int longitudeMax;
	private int LongitudeMin;
	private int nombreDeClient;
	
	
	public float test_motivationMax= 0;
	public float test_motivationMin = 1000000;
	
	
	public Population(int latitudeMax, int LatitudeMin, int longitudeMax, int LongitudeMin){
		this.latitudeMax = latitudeMax;
		this.LatitudeMin = LatitudeMin;
		this.longitudeMax = longitudeMax;
		this.LongitudeMin = LongitudeMin;
		
		this.population = new ArrayList<Agent>();
	}
	
	public Population(int latitudeMax, int LatitudeMin, int longitudeMax, int LongitudeMin, int nbJoueur, Meteo meteo, Meteo periodeJournee){
		
		this.population= new ArrayList<Agent>();
		nombreDeClient = calculDuNombreDeClient( nbJoueur,  meteo,  periodeJournee);
		
		for(int i = 0 ; i<nombreDeClient; i++){
			this.population.add( this.creerUnAgent(meteo, periodeJournee) );
		}
	}
	
	
	private Agent creerUnAgent(Meteo meteo, Meteo periodeJournee) {
		Agent client = new Agent(meteo, periodeJournee);

		client.setCoordonnees(calculerPositionClient(meteo, periodeJournee));
		
		if(client.getMotivation()>this.test_motivationMax){
			test_motivationMax=client.getMotivation();
		}
		if(client.getMotivation()<this.test_motivationMin){
			test_motivationMin=client.getMotivation();
		}
		return client;
	}
	
	
	private Coordonnees calculerPositionClient(Meteo meteo, Meteo periodeJournee){
		Random random = new Random();
		float latitude = this.LatitudeMin + random.nextFloat() * (this.latitudeMax - this.LatitudeMin);
		float longitude = this.LongitudeMin + random.nextFloat() * (this.longitudeMax - this.LongitudeMin);
		
		return (new Coordonnees(latitude, longitude));
	}
	

	
	public void genererPopulation(int latitudeMax, int LatitudeMin, int longitudeMax, int LongitudeMin, int nbJoueur,Meteo meteo, Meteo periodeJournee ){
		nombreDeClient = calculDuNombreDeClient( nbJoueur,  meteo,  periodeJournee);
		
		for(int i = 0 ; i<nombreDeClient; i++){
			this.population.add( this.creerUnAgent(meteo, periodeJournee) );
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
	
	public String toString(){
		String toReturn = "";
		int boisson_froide = 0;
		int boisson_sans_alcool =0;


		for(Agent a : population){
			
			toReturn += a.coordonnees.toString();
			toReturn += ("Motivation:" + a.motivation + "   Boisson froide: " + a.veutBoissonFroide + "   sansAlcool: " + a.veutBoissonSansAlcool);
			if(a.veutBoissonFroide==true){
				boisson_froide++;
			}
			if(a.veutBoissonSansAlcool==true){
				boisson_sans_alcool++;
			}
			toReturn += "  Distance avec centre: "+outils.OutilsCalculs.calculerDistance(a.getCoordonnees(), new Coordonnees(0f, 0f));
			toReturn += "\n";
		}
		
		toReturn += ("Nombre d'agent sur la map:" + population.size() + "  veut boisson froide :" + boisson_froide
				+ "   sans alcool :" + boisson_sans_alcool);
		return toReturn;
	}

	
}
