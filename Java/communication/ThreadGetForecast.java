package communication;

import gestion_population.TheGame;



/**
 * Thread pour recuperer la meteo et l'heure dans la partie
 * 
 * @author atila
 *
 */
public class ThreadGetForecast extends Thread {

	TheGame referenceALaPartie;

	public ThreadGetForecast(TheGame laPartie) {

		this.referenceALaPartie = laPartie;
	}

	public void run() {
		outils.ToString.toStringDebug("Dans le threadForecast");
		String forecastJson;
		while (true) {

			forecastJson = Communication.getRecevoir(outils.Global.URL_GET_FORECAST);
			outils.ToString.toStringMeteo("Json forecast en String du serveur : " + forecastJson);
			ManipulationJson.jsonFromStringTemps(forecastJson, referenceALaPartie);
			referenceALaPartie.setJourDuServeur(referenceALaPartie.getHeureDepuisDebutJeu() / 24);

			try {
				Thread.sleep(outils.Global.dureerDuSleep);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}



}
 