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
	public static String URL_POST_REQUEST_FOR_SELLING = "https://ponderosaproject.herokuapp.com/sales";
	public static String URL_GET_MAP = "https://ponderosaproject.herokuapp.com/map";
	public static String URL_GET_FORECAST = "https://ponderosaproject.herokuapp.com/metrology";
	/**
	 * min max motivation et mouvement
	 */

	public static final float minMotivation = 200;
	public static final float maxMotivation = 2000;

	public static final float minMouv = 100;
	public static final float maxMouv = 1000;

	public static final int clientMinParJoueur = 200;
	public static final int clientMaxParJoueur = 300;

	public static final float clientMinMotivation = 200;
	public static final float clientMaxMotivation = 2000;

	public static final float clientAlcoloMatin = 25;
	public static final float clientAlcoloSoir = 75;

	public static final float drinkInfoPoidVolonteFinalePourBoire = 20;

	public static final float poidInfluencePub = 100;
	public static final float poidDistancePerteVolonteFinale = 0.5f;
	public static final float poidCalculInfluence = 100000;
	public static final float poidCoutDeplacementPuissanceFormule2 = 1.45f;
	
	
	
	public static final float volonteMinPourAllerVersUnStand = 200;
	
	public static final int dureerDuSleep = 5000;
	
	public static final float minMotivationAvantDeNePlusVouloirBoire = 10;
	
	
	
	
	public static final boolean requeteServeurVraiOuFauxPourSimulationEnLocal = true;
	
	public static boolean uneFoisAfficherLaCarte=false;
	
	

	
	
	
	
	
	

}
