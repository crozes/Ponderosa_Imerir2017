package communication;

import gestion_population.TheGame;

public class ThreadGetForecast extends Thread {

	TheGame referenceALaPartie;

	public ThreadGetForecast(TheGame laPartie) {
		// TODO Auto-generated method stub
		
		this.referenceALaPartie = laPartie;
	}
	public void run(){
		String forecastJson;
		while(true){
			try {
				Thread.sleep(outils.Global.dureerDuSleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forecastJson = Communication.getRecevoir(outils.Global.URL_GET_FORECAST);
			ManipulationJson.jsonFromStringTemps(forecastJson, referenceALaPartie);
			referenceALaPartie.setJourDuServeur(referenceALaPartie.getHeureDepuisDebutJeu()/24);
		}
	}


}
 