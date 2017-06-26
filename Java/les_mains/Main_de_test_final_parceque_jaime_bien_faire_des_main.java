package les_mains;

import communication.Communication;
import gestion_population.TheGame;

public class Main_de_test_final_parceque_jaime_bien_faire_des_main {

	public static void main(String[] args) {
		
		
		//recuperation du string json
		String StringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
		
		//creation de la partie
		TheGame laPartie = new TheGame(StringDeLaMapEnJson);
		
		
		//generation de la population
		laPartie.getMapDeLaPopulation().genererPopulation(laPartie.getRegion().getLatitudeMax(),
				laPartie.getRegion().getLatitudeMin(), laPartie.getRegion().getLongitudeMax(),
				laPartie.getRegion().getLongitudeMin(), laPartie.getRanking().size(), laPartie.getMeteoDuJour(),
				outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu()));
		
		//on fait boire la population
		laPartie.getMapDeLaPopulation().faireBoireLaPopulation(laPartie.getMeteoDuJour(),
				outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(laPartie.getHeureDepuisDebutJeu()), laPartie.getRanking(),
				laPartie.getListeMapItemJoueur());
		
		//on fait deplacer la population
		
		
		//on fait boire la population le soir
		
		//on envoie le resultat au serveur
		
		//on recupere le temps est quand on est à une nouvelle journee, on recommence.

	}
}
