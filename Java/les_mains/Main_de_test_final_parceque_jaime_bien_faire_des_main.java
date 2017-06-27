package les_mains;

import java.util.ArrayList;
import java.util.HashMap;

import communication.Communication;
import communication.ManipulationJson;
import gestion_population.Coordonnees;
import gestion_population.MapItem;
import gestion_population.Stand;
import gestion_population.TheGame;
import outils.Meteo;

public class Main_de_test_final_parceque_jaime_bien_faire_des_main {

	public static void main(String[] args) throws Exception {
		
		
		//recuperation du string json
		String stringDeLaMapEnJson;
		
		//creation de la partie 
		TheGame laPartie;
		laPartie= new TheGame();
		//recuperation du jsonMap pour innitialiser la partie
		stringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
		System.out.println(stringDeLaMapEnJson);
		ManipulationJson.jsonFromStringMap(stringDeLaMapEnJson, laPartie);
		laPartie.setMeteoDuJour(Meteo.sunny);
		
		//lancement du thread de requete du Forecast
//		ThreadGetForecast threadForecast = new ThreadGetForecast(laPartie);
//		threadForecast.start();
//		
		//lancement du threadImage
		/*
		 * ThreadGraphism threadGraphism = new ThreadGraphism(laPartie);
		threadGraphism.start(laPartie);
		 */

		
		//correction du bug json
		Stand stand;
		ArrayList<MapItem> map;
		HashMap<String, Stand> correction = new HashMap<>();
		
		
		
		map = new ArrayList<>();
		stand = new Stand("stand", "Tata", 20, new Coordonnees(200, 100));
		map.add(stand);
		correction.put("Tata", stand);
		laPartie.getListeMapItemJoueur().put("Tata", map);
		
		stand = new Stand("stand", "Toto", 20, new Coordonnees(100, 200));
		map = new ArrayList<>();
		map.add(stand);
		correction.put("Toto", stand);
		laPartie.getListeMapItemJoueur().put("Toto", map);
		
		stand = new Stand("stand", "Titi", 20, new Coordonnees(500, 400));
		map = new ArrayList<>();
		map.add(stand);
		correction.put("Titi", stand);
		laPartie.getListeMapItemJoueur().put("Titi", map);
		
//		correction.put("Tata", new Stand("stand", "Tata", 20, new Coordonnees(200, 100)));
//		correction.put("Tata", new Stand("stand", "Tata", 20, new Coordonnees(200, 100)));
//		correction.put("Toto", new Stand("stand", "Tata", 20, new Coordonnees(200, 100)));
//		correction.put("Tata", new Stand("stand", "Tata", 20, new Coordonnees(200, 100)));
//		correction.put("Tata", new Stand("stand", "Tata", 20, new Coordonnees(200, 100)));
		
		
		
		
		laPartie.setListeDesStand(correction);
		while (true){
			
			
			
			//si la partie n'est pas vide
			if(laPartie.getRanking().size()>0){
				//generation de la population
				laPartie.getMapDeLaPopulation().genererPopulation(laPartie.getRegion().getLatitudeMax(),
						laPartie.getRegion().getLatitudeMin(), laPartie.getRegion().getLongitudeMax(),
						laPartie.getRegion().getLongitudeMin(), laPartie.getRanking().size(), laPartie.getMeteoDuJour(),
						Meteo.matin, laPartie.getListeDesStand());
				
				
				outils.ToString.toString("Voici la population :");
				outils.ToString.toString(laPartie.getMapDeLaPopulation());
				
				//on fait boire la population le matin
				laPartie.getMapDeLaPopulation().faireBoireLaPopulation(Meteo.thunderstorm, laPartie);
			}
			

			
			//on attend l'apres midi
			while(outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu() ) != Meteo.soir){
				try {
					Thread.sleep(outils.Global.dureerDuSleep);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
			
			
			//si la partie n'est pas vide
			if(laPartie.getRanking().size()>0){
				//on fait deplacer la population
				laPartie.getMapDeLaPopulation().mouvementDuMidi(laPartie.getMeteoDuJour(), Meteo.soir);
				
				//on fait boire la population le soir
				laPartie.getMapDeLaPopulation().faireBoireLaPopulation(Meteo.soir, laPartie);
				
				//on envoie le resultat au serveur
				Communication.postEnvoyer(laPartie.getJsonSales().toString(), outils.Global.URL_POST_SALES);
			}

			
			//on attend le lendemain et on recommence
			
			while(outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu() ) != Meteo.matin){
				try {
					Thread.sleep(outils.Global.dureerDuSleep);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			//recuperation du nouveau jsonPournouveau jour
			stringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
			ManipulationJson.jsonFromStringMap(stringDeLaMapEnJson, laPartie);
		}
	}
}
