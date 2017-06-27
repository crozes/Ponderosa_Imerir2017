package testPoubelle;

import java.util.ArrayList;

import gestion_population.TheGame;
import outils.Meteo;

public class main_avec_des_trucs_dedans {
	
	
	private static float test;

	public static void main(String[] args) {
		
		
		
		setTest(0);
		
		setTest(outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(Meteo.cloudy));
		
		setTest(outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(Meteo.sunny));
		setTest(outils.OutilsCalculs.poidMeteoMotivationBoissonFroide(Meteo.heatwave));

		
		
		
		//PlayerInfo test = new PlayerInfo();
		
		
		TheGame laPartie = new TheGame();
		
		ArrayList<String> ranking = new ArrayList<>();
		ranking.add("Viktor");
		ranking.add("Stefan");
		ranking.add("Tupolef");
		ranking.add("Tchekov");
		
		Meteo meteo = Meteo.heatwave;
		Meteo periodeJournee = Meteo.matin;
		
//		laPartie.getMapDeLaPopulation().genererPopulation(50, -50, 50, -50, ranking.size(), meteo, periodeJournee);
		laPartie.setRanking(ranking);

		System.out.println(laPartie.getMapDeLaPopulation().toString());
		
		System.out.println("Motivation min: "+ laPartie.getMapDeLaPopulation().test_motivationMin + "   Max: " + laPartie.getMapDeLaPopulation().test_motivationMax);
		
		System.out.println(laPartie.getRanking());
		
		
		
		
	}

	public static float getTest() {
		return test;
	}

	public static void setTest(float test) {
		main_avec_des_trucs_dedans.test = test;
	}
	

}
