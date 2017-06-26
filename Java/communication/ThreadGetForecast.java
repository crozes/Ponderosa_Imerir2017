package communication;

import gestion_population.TheGame;

public class ThreadGetForecast {

	public static void main(TheGame laPartie) {
		// TODO Auto-generated method stub
		
		Thread thread = new Thread(){
			public void run(){
				String forecastJson;
				while(true){
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					forecastJson = Communication.getRecevoir(outils.Global.URL_GET_FORECAST);
					ManipulationJson.jsonFromStringTemps(forecastJson, laPartie);
					laPartie.setJourDuServeur(laPartie.getHeureDepuisDebutJeu()/24);
				}
			}
		};
	}
}
 