package outils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

import gestion_population.Agent;
import gestion_population.Coordonnees;
import gestion_population.MapItem;

public class OutilsCalculs {

	/**
	 * 
	 * @param heureDepuisDepusPartie
	 * @return
	 */
	public static Meteo quelEstLaPeriodeDeLaJournee(int heureDepuisDepusPartie) {
		float resultModulo;

		resultModulo = heureDepuisDepusPartie % 24;

		if (resultModulo > 12) {
			return Meteo.soir;
		} else {
			return Meteo.matin;
		}
	}

	/**
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
	public static float calculerDistance(float latitude, float longitude, float latitude_p, float longitude_p) {

		return (float) Math.sqrt(Math.pow((latitude - latitude_p), 2) + Math.pow((longitude - longitude_p), 2));
	}

	/**
	 * Permet d'utiliser la fonction distance avec un cliet et une map item
	 * 
	 * @param personne
	 * @param mapItem
	 * @return
	 */
	public static float calculerDistance(Agent personne, MapItem mapItem) {

		return OutilsCalculs.calculerDistance(personne.getLatitude(), personne.getLongitude(), mapItem.getLatitude(),
				mapItem.getLongitude());
	}

	/**
	 * Permet d'utiliser la fonction calculerDistance avec deux coordonnees
	 * 
	 * @param personne
	 * @param mapItem
	 * @return
	 */
	public static float calculerDistance(Coordonnees personne, Coordonnees mapItem) {

		return OutilsCalculs.calculerDistance(personne.getLatitude(), personne.getLongitude(), mapItem.getLatitude(),
				mapItem.getLongitude());
	}

	/**
	 * Renvoie un int aleatoire entre min et max
	 * 
	 * @param min
	 * @param max
	 * @return int entre min(inclu) et max(inclu)
	 */
	public static int randomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	/**
	 * Renvoie un float aleatoire entre min et max
	 * 
	 * @param min
	 * @param max
	 * @return float entre min(inclu) et max(inclu)
	 */
	public static float randomFloat(float min, float max) {
		float manipGaussien = (float) ThreadLocalRandom.current().nextGaussian();
		manipGaussien = manipGaussien/10;
		System.out.println("Gauss : " + manipGaussien);
		float leRandom = ThreadLocalRandom.current().nextFloat() * (max - min) + min;
		
		return (leRandom * manipGaussien);
	}

	/**
	 * Retourne le poid de la meteo sur le déplacement (definie par les
	 * commites) Si la meteo est "tempete" le retour est automatiquement 0
	 * Déplacement 10%rainny 40%cloudy 50%sunny 20%heatwave 0%thunderstorm
	 * 
	 * @param meteo
	 * @return Valeur du poid de la meteo sur la motivation
	 */
	public static float bonusDeMotivationSelonLaMeteo(Meteo meteo) {
		float poid = 1;

		switch (meteo) {
		case rainny:
			return poid *= 1.1;
		case cloudy:
			return poid *= 1.4;
		case sunny:
			return poid *= 1.5;
		case heatwave:
			return poid *= 1.2;
		case thunderstorm:
			return poid *= 0;
		default:
			return poid;
		}
	}

	/**
	 * Renvoie le poids de l'envie de boisson froide ou chaude selon la meteo
	 * Durant la canicule les clients veulent tous des boissons froide
	 * 
	 * @param meteo
	 * @return Pourcentage d'envie d'une boisson froide
	 */
	public static float pourcentagePopulationVoulantUneBoissonSelonLaMeteo(Meteo meteo) {
		float poid = 100;

		// Consommation Clients rainny:15% cloudy:30% sunny:75% heatwave:100%
		// thunderstorm:0%

		switch (meteo) {
		case rainny:
			return poid *= 0.15;
		case cloudy:
			return poid *= 0.3;
		case sunny:
			return poid *= 0.75;
		case heatwave:
			return poid *= 1;
		case thunderstorm:
			return poid *= 0;
		default:
			return poid;
		}
	}

	public static String fichierTxtVersString(String urlDuFichier){
		String result ="";
		String ligne = "";
		try{
			InputStream flux=new FileInputStream(urlDuFichier); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);

			while ((ligne=buff.readLine())!=null){
				result+=ligne;
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		
		return result;
	}
	
	
	
}
