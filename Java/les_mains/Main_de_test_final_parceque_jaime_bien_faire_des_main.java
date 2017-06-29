
package les_mains;

import communication.Communication;
import communication.ManipulationJson;
import communication.ThreadGetForecast;
import gestion_population.TheGame;
import outils.Meteo;

public class Main_de_test_final_parceque_jaime_bien_faire_des_main {

	public static void main(String[] args) throws Exception {

		// recuperation du string json
		String stringDeLaMapEnJson;

		// creation de la partie
		TheGame laPartie;
		laPartie = TheGame.getInstance();

		boolean localOuServeur = outils.Global.requeteServeurVraiOuFauxPourSimulationEnLocal;
		if (localOuServeur == true) {
			// recuperation du jsonMap pour innitialiser la partie
			stringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
			outils.ToString.toStringJSON(stringDeLaMapEnJson);
			ManipulationJson.jsonFromStringMap(stringDeLaMapEnJson, laPartie);

			outils.ToString.ecrireUneTrace(laPartie.toString());
			// lancement du thread de requete du Forecast
			ThreadGetForecast threadForecast = new ThreadGetForecast(laPartie);
			threadForecast.start();

			// petit slep pour etre sur de bien avoir recuperer la meteo et
			// l'heure
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} else { // on simule

			stringDeLaMapEnJson = outils.OutilsCalculs.fichierTxtVersString("Files/json.txt");
			ManipulationJson.jsonFromStringMap(stringDeLaMapEnJson, laPartie);
			laPartie.setHeureDepuisDebutJeu(10);
			laPartie.setMeteoDuJour(Meteo.valueOf("sunny"));
		}

		// lancement du threadImage
		/*
		 * ThreadGraphism threadGraphism = new ThreadGraphism(laPartie);
		 * threadGraphism.start(laPartie);
		 */

		// //correction du bug json
		// Stand stand;
		// ArrayList<MapItem> map;
		// HashMap<String, Stand> correction = new HashMap<>();
		//
		//
		//
		// map = new ArrayList<>();
		// stand = new Stand("stand", "Tata", 20, new Coordonnees(200, 100));
		// map.add(stand);
		// correction.put("Tata", stand);
		// laPartie.getListeMapItemJoueur().put("Tata", map);
		//
		// stand = new Stand("stand", "Toto", 20, new Coordonnees(100, 200));
		// map = new ArrayList<>();
		// map.add(stand);
		// correction.put("Toto", stand);
		// laPartie.getListeMapItemJoueur().put("Toto", map);
		//
		// stand = new Stand("stand", "Titi", 20, new Coordonnees(500, 400));
		// map = new ArrayList<>();
		// map.add(stand);
		// correction.put("Titi", stand);
		// laPartie.getListeMapItemJoueur().put("Titi", map);
		//
		//// correction.put("Tata", new Stand("stand", "Tata", 20, new
		// Coordonnees(200, 100)));
		//// correction.put("Tata", new Stand("stand", "Tata", 20, new
		// Coordonnees(200, 100)));
		//// correction.put("Toto", new Stand("stand", "Tata", 20, new
		// Coordonnees(200, 100)));
		//// correction.put("Tata", new Stand("stand", "Tata", 20, new
		// Coordonnees(200, 100)));
		//// correction.put("Tata", new Stand("stand", "Tata", 20, new
		// Coordonnees(200, 100)));
		//
		//
		//
		//
		// laPartie.setListeDesStand(correction);
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
			ManipulationJson.jsonFromStringMap(stringDeLaMapEnJson, laPartie);

		}
	}
}
