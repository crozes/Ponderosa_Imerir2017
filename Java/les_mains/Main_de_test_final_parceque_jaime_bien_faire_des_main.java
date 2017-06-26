package les_mains;

import communication.Communication;
import communication.ThreadGetForecast;
import gestion_population.TheGame;
import outils.Meteo;

public class Main_de_test_final_parceque_jaime_bien_faire_des_main {

	public static void main(String[] args) {
		
		
		//recuperation du string json
		String StringDeLaMapEnJson;
		
		//creation de la partie avec recuperation du jsonMap pour innitialiser la partie
		TheGame laPartie; 
		StringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
		laPartie= new TheGame(StringDeLaMapEnJson);
		
		
		//lancement du thread de requete du Forecast
		ThreadGetForecast threadForecast = new ThreadGetForecast(laPartie);
		threadForecast.start();
		
		

		while (true){

			//generation de la population
			laPartie.getMapDeLaPopulation().genererPopulation(laPartie.getRegion().getLatitudeMax(),
					laPartie.getRegion().getLatitudeMin(), laPartie.getRegion().getLongitudeMax(),
					laPartie.getRegion().getLongitudeMin(), laPartie.getRanking().size(), laPartie.getMeteoDuJour(),
					Meteo.matin);
			
			//on fait boire la population le matin
			laPartie.getMapDeLaPopulation().faireBoireLaPopulation(laPartie.getMeteoDuJour(),
					Meteo.matin, laPartie.getRanking(),
					laPartie.getListeMapItemJoueur());
			
			//on attend l'apres midi
			while(outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu() ) != Meteo.soir){
				try {
					Thread.sleep(outils.Global.dureerDuSleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//on fait deplacer la population
			laPartie.getMapDeLaPopulation().mouvementDuMidi(laPartie.getMeteoDuJour(), Meteo.soir);
			
			//on fait boire la population le soir
			laPartie.getMapDeLaPopulation().faireBoireLaPopulation(laPartie.getMeteoDuJour(),
					Meteo.soir, laPartie.getRanking(),
					laPartie.getListeMapItemJoueur());
			
			//on envoie le resultat au serveur
			
			Communication.postEnvoyer(laPartie.getJsonSales().toString(), outils.Global.URL_POST_SALES);
			
			//on attend le lendemain et on recommence
			
			while(outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu() ) != Meteo.matin){
				try {
					Thread.sleep(outils.Global.dureerDuSleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//on recupere le temps est quand on est à une nouvelle journee, on recommence.
			StringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
			laPartie= new TheGame(StringDeLaMapEnJson);
			
		}


	}
}
