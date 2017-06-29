package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import outils.Meteo;

public class TheGame {

	private static TheGame singleton;

	private Meteo meteoDuJour;
	private Meteo meteoDeDemain;
	private int heureDepuisDebutJeu;
	private int jourActuel;
	private int jourDuServeur;

	private Region region;
	private ArrayList<String> ranking;

	private HashMap<String, ArrayList<MapItem>> listeMapItemJoueur;

	private HashMap<String, PlayerInfo> listePlayerInfo;
	private HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo; // joueur
																		// drinkinfo
	private HashMap<String, Stand> listeDesStand;

	private ArrayList<MapItem> laMapDesObjets;
	private Population mapDeLaPopulation;

	public TheGame() {
		this.region = new Region();
		this.ranking = new ArrayList<String>();

		this.listeMapItemJoueur = new HashMap<String, ArrayList<MapItem>>();
		this.listePlayerInfo = new HashMap<String, PlayerInfo>();
		this.listeDesDrinkInfo = new HashMap<String, ArrayList<DrinkInfo>>();
		this.listeDesStand = new HashMap<String, Stand>();

		this.laMapDesObjets = new ArrayList<MapItem>();

		this.mapDeLaPopulation = new Population();

	}

	public TheGame(ArrayList<MapItem> laMapDesObjets, Population mapDeLaPopulation,
			HashMap<String, ArrayList<MapItem>> listeItemByPlayer, HashMap<String, PlayerInfo> listePlayerInfo,
			Meteo meteoDuJour, Meteo meteoDeDemain, int heureDepuisDebutJeu,
			HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo, ArrayList<String> ranking, Region region) {
		super();
		this.laMapDesObjets = laMapDesObjets;
		this.mapDeLaPopulation = mapDeLaPopulation;
		this.listeMapItemJoueur = listeItemByPlayer;
		this.listePlayerInfo = listePlayerInfo;
		this.meteoDuJour = meteoDuJour;
		this.meteoDeDemain = meteoDeDemain;
		this.heureDepuisDebutJeu = heureDepuisDebutJeu;
		this.listeDesDrinkInfo = listeDesDrinkInfo;
		this.ranking = ranking;
		this.region = region;
	}

	public static TheGame getInstance() {
		if (null == singleton) { // Premier appel
			singleton = new TheGame();
		}
		return singleton;
	}

	public HashMap<String, Stand> getListeDesStand() {
		return listeDesStand;
	}

	public void setListeDesStand(HashMap<String, Stand> listeDesStand) {
		this.listeDesStand = listeDesStand;
	}

	public int getJourActuel() {
		return jourActuel;
	}

	public void setJourActuel(int jourActuel) {
		this.jourActuel = jourActuel;
	}

	public int getJourDuServeur() {
		return jourDuServeur;
	}

	public void setJourDuServeur(int jourDuServeur) {
		this.jourDuServeur = jourDuServeur;
	}

	public ArrayList<MapItem> getLaMapDesObjets() {
		return laMapDesObjets;
	}

	public void setLaMapDesObjets(ArrayList<MapItem> laMapDesObjets) {
		this.laMapDesObjets = laMapDesObjets;
	}

	public Population getMapDeLaPopulation() {
		return mapDeLaPopulation;
	}

	public void setMapDeLaPopulation(Population mapDeLaPopulation) {
		this.mapDeLaPopulation = mapDeLaPopulation;
	}

	public HashMap<String, ArrayList<MapItem>> getListeMapItemJoueur() {
		return listeMapItemJoueur;
	}

	public void setListeMapItemJoueur(HashMap<String, ArrayList<MapItem>> listeMapItemJoueur) {
		this.listeMapItemJoueur = listeMapItemJoueur;
	}

	public HashMap<String, ArrayList<DrinkInfo>> getListeDesDrinkInfo() {
		return listeDesDrinkInfo;
	}

	public void setListeDesDrinkInfo(HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo) {
		this.listeDesDrinkInfo = listeDesDrinkInfo;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public HashMap<String, PlayerInfo> getListePlayerInfo() {
		return listePlayerInfo;
	}

	public void setListePlayerInfo(HashMap<String, PlayerInfo> listePlayerInfo) {
		this.listePlayerInfo = listePlayerInfo;
	}

	public ArrayList<String> getRanking() {
		return ranking;
	}

	public void setRanking(ArrayList<String> ranking) {
		this.ranking = ranking;
	}

	public Meteo getMeteoDuJour() {
		return meteoDuJour;
	}

	public void setMeteoDuJour(Meteo meteoDuJour) {
		this.meteoDuJour = meteoDuJour;
	}

	public Meteo getMeteoDeDemain() {
		return meteoDeDemain;
	}

	public void setMeteoDeDemain(Meteo meteoDeDemain) {
		this.meteoDeDemain = meteoDeDemain;
	}

	public int getHeureDepuisDebutJeu() {
		return heureDepuisDebutJeu;
	}

	public void setHeureDepuisDebutJeu(int heureDepuisDebutJeu) {
		this.heureDepuisDebutJeu = heureDepuisDebutJeu;
	}

	/**
	 * Permet de creer l'objet JsonMap afin de l'envoyer au serveur
	 * 
	 * @return l'objet jsonMap
	 * @author atila
	 */
	public JsonObject getJsonMap() {
		JsonObject jsonOb = new JsonObject();
		// JsonArray jsonArRanking = new JsonArray();
		JsonArray jsonArRanking = new JsonArray();
		JsonObject jsonObPlayerInfo = new JsonObject();
		JsonObject jsonObItempByPlayers = new JsonObject();
		JsonObject jsonObDrinksByPlayers = new JsonObject();

		for (String playerName : this.ranking) {
			JsonPrimitive element = new JsonPrimitive(playerName);
			jsonArRanking.add(element);
			jsonObPlayerInfo.add(playerName, this.listePlayerInfo.get(playerName).getJsonObject());
			jsonObItempByPlayers.add(playerName, getAllMapItems(playerName));
			jsonObDrinksByPlayers.add("drinksByPlayer", getAllDrinksInfo(playerName));
		}

		jsonOb.add("region", this.region.getJsonObject());
		jsonOb.add("ranking", jsonArRanking);

		jsonOb.add("playerInfo", jsonObPlayerInfo);

		return jsonOb;
	}

	/**
	 * Utilise par getJsonMap()
	 * 
	 * @param playerName
	 * @return une JsonArray de tous les MapItem du joueur
	 */
	private JsonArray getAllMapItems(String playerName) {
		JsonArray jsonAr = new JsonArray();

		for (MapItem mapItem : this.listeMapItemJoueur.get(playerName)) {
			jsonAr.add(mapItem.getJsonObject());
		}

		return jsonAr;
	}

	/**
	 * Utilise par getJsonMap()
	 * 
	 * @param playerName
	 * @return une JsonArray de tous les DrinkInfo du joueur
	 */
	private JsonArray getAllDrinksInfo(String playerName) {
		JsonArray jsonAr = new JsonArray();

		for (DrinkInfo drinkInfo : this.listeDesDrinkInfo.get(playerName)) {
			jsonAr.add(drinkInfo.getJsonObject());
		}

		return jsonAr;
	}

	/**
	 * Renvoie une liste json avec les info des boisson du jour Sale
	 * player:string, item:string, quantity:int
	 * 
	 * sales:[sale]
	 * 
	 * @return
	 */
	public JsonArray getJsonSales() {
		JsonArray jsonArSales = new JsonArray();
		for (String playerName : this.ranking) {
			for (DrinkInfo drinkInfo : this.listeDesDrinkInfo.get(playerName)) {
				jsonArSales.add(drinkInfo.getJsonObjectSale(playerName));
			}
		}
		return jsonArSales;

	}

	public String toString() {
		String resultToReturn = "";

		// private Region region;
		resultToReturn += "Region :" + this.region.toString() + "\n";
		// private ArrayList<String> ranking;
		resultToReturn += "Ranking :" + this.ranking.toString() + "\n";
		// private HashMap<String, ArrayList<MapItem>> listeMapItemJoueur;
		resultToReturn += "listeMapItemJoueur :" + this.listeMapItemJoueur.toString() + "\n";
		// for(MapItem mapItem : this.listeMapItemJoueur){
		// mapItem.toString();
		// }
		// private HashMap<String, PlayerInfo> listePlayerInfo;
		resultToReturn += "listePlayerInfo :" + this.listePlayerInfo.toString() + "\n";
		// private HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo; //
		// joueur
		resultToReturn += "listeDesDrinkInfo :" + this.listeDesDrinkInfo.toString() + "\n";// drinkinfo
		// private HashMap<String, Stand> listeDesStand;
		resultToReturn += "listeDesStand :" + this.listeDesStand.toString() + "\n";
		// private ArrayList<MapItem> laMapDesObjets;
		resultToReturn += "laMapDesObjets :" + this.laMapDesObjets.toString() + "\n";
		// private Population mapDeLaPopulation;
		resultToReturn += "mapDeLaPopulation :" + this.mapDeLaPopulation.toString() + "\n";

		return resultToReturn;

	}

	public void apercuDesVentes() {
		for (String playerInfo : this.listePlayerInfo.keySet()) {
			System.out.println(playerInfo + " a vendue : " + this.listePlayerInfo.get(playerInfo));
		}

	}

}
