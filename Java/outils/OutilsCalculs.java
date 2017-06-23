package outils;


import gestion_population.Individu;
import gestion_population.MapItem;
import java.util.concurrent.ThreadLocalRandom;






public class OutilsCalculs {
	
	
	
	/**
	 * influencePub = pub1/distance1² + pu2/dis2² + pubN/distanceN²
	 * 
	 * distance = racineCarre( (xa-xb)+(ya-yb) )
	 * 
	 * diviser par X motiv entre chaque bar
	 * 
	 * x = Val/Max
	 * 
	 * VolonteFinale - Distance > PrixAchat
	 * 
	 * @param latitude
	 * @param longitude
	 * @param latitude_p
	 * @param longitude_p
	 * @return
	 */
	
	public static float calculerDistance(float latitude, float longitude, float latitude_p, float longitude_p){
		float resultat = 0;
		
		
		
		
		return resultat;
	}
	
	
	public static float calculerDistance(Individu personne, MapItem mapItem){
		float resultat = 0;
		
		resultat = OutilsCalculs.calculerDistance(personne.getLatitude(), personne.getLongitude(), mapItem.getLatitude(), mapItem.getLongitude());
		
		
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
