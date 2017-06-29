package gestion_population;

import communication.Communication;
import communication.ManipulationJson;
import outils.Meteo;

public class ThreadBoucleGestionPopulation extends Thread {

	TheGame laPartie;

	public ThreadBoucleGestionPopulation(TheGame referenceALaPartie) {

		this.laPartie = referenceALaPartie;
	}

	public void run() {
		outils.ToString.toStringDebug("Dans le threadForecast");
		String stringDeLaMapEnJson;

		Meteo meteoDuJour;

		while (true) {

			meteoDuJour = laPartie.getMeteoDuJour();
			// si la partie n'est pas vide
			if (laPartie.getRanking().size() > 0) {
				// generation de la population
				laPartie.getMapDeLaPopulation().genererPopulation(laPartie.getRegion().getLatitudeMax(),
						laPartie.getRegion().getLatitudeMin(), laPartie.getRegion().getLongitudeMax(),
						laPartie.getRegion().getLongitudeMin(), laPartie.getRanking().size(), meteoDuJour, Meteo.matin,
						laPartie.getListeDesStand());

				outils.Global.uneFoisAfficherLaCarte = true;

				outils.ToString.toStringListe("Voici la population :");
				outils.ToString.toStringListe(laPartie.getMapDeLaPopulation());

				// on fait boire la population le matin
				laPartie.getMapDeLaPopulation().faireBoireLaPopulation2(laPartie);

			}

			outils.ToString.toStringDiver("\n\nattende midi\n\n");
			// on attend l'apres midi
			while (outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu()) != Meteo.soir) {
				try {
					Thread.sleep(outils.Global.dureerDuSleep);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

			// si la partie n'est pas vide
			if (laPartie.getRanking().size() > 0) {
				// on fait deplacer la population
				laPartie.getMapDeLaPopulation().mouvementDuMidi(laPartie.getMeteoDuJour(), Meteo.soir);

				outils.Global.uneFoisAfficherLaCarte = true;

				// on fait boire la population le soir
				laPartie.getMapDeLaPopulation().faireBoireLaPopulation2(laPartie);

				/*
				 * On envoie PAS les ventes de la journnee au seveurle resultat
				 * au serveur, car les ventes sont faites durant la journee.
				 */

			}

			outils.ToString.toStringDiver("\n\nattende matin\n\n");
			// on attend le lendemain et on recommence

			while (outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu()) != Meteo.matin) {
				try {
					Thread.sleep(outils.Global.dureerDuSleep);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

			// recuperation du nouveau jsonPournouveau jour
			// on attend quand meme 2 sec pour bien etre sur que la map soit
			// mise a jours
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			stringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
			try {
				ManipulationJson.jsonFromStringMap(stringDeLaMapEnJson, laPartie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
