package outils;


import gestion_population.Individu;
import gestion_population.MapItem;
import java.util.concurrent.ThreadLocalRandom;






public class OutilsCalculs {
	
	
	public static float calculerDistance(int x, int y, int x_p, int y_p){
		float resultat = 0;
		
		
		
		
		return resultat;
	}
	
	
	public static float calculerDistance(Individu personne, MapItem truc){
		float resultat = 0;
		
		resultat = OutilsCalculs.calculerDistance(personne.getX(), personne.getY(), truc.getX(), truc.getY());
		
		
		return resultat;
	}
	
	
	public static int randomInt(int min, int max){

		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static float randomFloat(int min, int max){

		return ThreadLocalRandom.current().nextFloat()* (max - min) + min;
	}
	
	
	
	public static float poidMeteoParcourBoissonFroide(Meteo meteo){
		float poid = 1;
	 
//		Déplacement	10%rainny 40%cloudy 50%sunny 20%heatwave 0%thunderstorm
		 
		switch (meteo){
		case rainny:
			return poid*= 1.1;
		case cloudy:
			return poid*= 1.4;
		case sunny:
			return poid*= 1.5;
		case heatwave:
			return poid*= 1.2;
		case thunderstorm:
			return 0;
		default:
			return poid;
		}
	}

	public static float poidMeteoMotivationBoissonFroide(Meteo meteo){
		float poid = 1;
		
//		Consommation Clients 15% 30% 75%	100% 0%
		
		switch (meteo){
		case rainny:
			return poid*=1.15;
		case cloudy:
			return poid*=1.3;
		case sunny:
			return poid*=1.75;
		case heatwave:
			return poid*=2;
		case thunderstorm:
			return poid*=0;
		default:
			return poid; 
		}	
	}

}
