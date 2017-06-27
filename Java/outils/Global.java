package outils;

public final class Global {

	/**
	 * Voici les url pour contacter le serveur
	 */

	// url de test afin de recuperer une trame json.
	public static String URL_TEST_JSON_GET = "https://ponderosaproject.herokuapp.com/dayinfo";
	// url de tes pour poster et recuperer ce qu'on a envoyé.
	public static String URL_TEST_JSON_POST = "https://ponderosaproject.herokuapp.com/posttest";

	public static String URL_POST_SALES = "https://ponderosaproject.herokuapp.com/sales";
	public static String URL_POST_REQUEST_FOR_SELLING = "https://ponderosaproject.herokuapp.com/sale";
	public static String URL_GET_MAP = "https://ponderosaproject.herokuapp.com/map";
	public static String URL_GET_FORECAST = "https://ponderosaproject.herokuapp.com/metrology";
	/**
	 * min max motivation et mouvement
	 */

	public static final float minMotivation = 100;
	public static final float maxMotivation = 1000;

	public static final float minMouv = 100;
	public static final float maxMouv = 1000;

	public static final int clientMinParJoueur = 50;
	public static final int clientMaxParJoueur = 100;

	public static final float clientMinMotivation = 20;
	public static final float clientMaxMotivation = 75;

	public static final float clientAlcoloMatin = 25;
	public static final float clientAlcoloSoir = 75;

	public static final float drinkInfoPoidVolonteFinalePourBoire = 100;

	public static final float poidInfluencePub = 100;
	public static final float poidDistancePerteVolonteFinale = 0.5f;
	public static final float poidCalculInfluence = 100000;
	
	public static final float volonteMinPourAllerVersUnStand = 1500;
	
	public static final int dureerDuSleep = 10000;
	
	public static final float minMotivationAvantDeNePlusVouloirBoire = 10;
	
	public static final boolean activerLesSystemOut = true;
	

}
