package outils;

public final class Global {

	/**
	 * Voici les url pour contacter le serveur
	 */

	// url de test afin de recuperer une trame json.
	public static String URL_TEST_JSON_GET = "https://ponderosaproject.herokuapp.com/dayinfo";
	// url de tes pour poster et recuperer ce qu'on a envoyé.
	public static String URL_TEST_JSON_POST = "https://ponderosaproject.herokuapp.com/posttest";

	public static String URL_POST_SALES = " ";
	public static String URL_POST_REQUEST_FOR_SELLING = " ";
	public static String URL_GET_MAP = " ";
	public static String URL_GET_FORECAST = " ";
	/**
	 * min max motivation et mouvement
	 */

	public static final int minMotivation = 100;
	public static final int maxMotivation = 1000;

	public static final int minMouv = 100;
	public static final int maxMouv = 1000;

	public static final int clientMinParJoueur = 50;
	public static final int clientMaxParJoueur = 100;

	public static final float clientMinMotivation = 20;
	public static final float clientMaxMotivation = 75;

	public static final int clientAlcoloMatin = 25;
	public static final int clientAlcoloSoir = 75;

	public static final int drinkInfoPoidVolonteFinalePourBoire = 100;

	public static final int poidInfluencePub = 100;
	public static final int poidDistancePerteVolonteFinale = 100;
	
	public static final int volonteMinPourAllerVersUnStand = 1500;
	
	public static final int dureerDuSleep = 10000;
	
	public static final int minMotivationAvantDeNePlusVouloirBoire = 10;
	

}
