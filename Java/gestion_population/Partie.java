package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import outils.Meteo;


public class Partie {

	private Meteo meteoDuJour;
	private Meteo meteoDeDemain;
	private int heureDepuisDebutJeu;
	
	private Region region;
	private ArrayList<String> ranking;
	
	private HashMap<String, ArrayList<MapItem> > listeItemByPlayer;
	private HashMap<String, PlayerInfo> listePlayerInfo;
	private HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo; //joueur drinkinfo
	
	

	private ArrayList<MapItem> laMapDesObjets;
	private Population mapDeLaPopulation;
	
	
	public Partie(int latitudeMax, int LatitudeMin, int longitudeMax, int LongitudeMin){
		this.region = new Region();
		this.ranking = new ArrayList<String>();
		
		this.listeItemByPlayer = new HashMap<String, ArrayList<MapItem> >();
		this.listePlayerInfo = new HashMap<String, PlayerInfo>();
		this.listeDesDrinkInfo = new HashMap<String, ArrayList<DrinkInfo>>();
		
		this.laMapDesObjets = new ArrayList<MapItem>();
		
		this.mapDeLaPopulation = new Population(latitudeMax, LatitudeMin, longitudeMax, LongitudeMin);
		

	}
	
	
	


	public Partie(ArrayList<MapItem> laMapDesObjets, Population mapDeLaPopulation,
			HashMap<String, ArrayList<MapItem>> listeItemByPlayer, HashMap<String, PlayerInfo> listePlayerInfo,
			Meteo meteoDuJour, Meteo meteoDeDemain, int heureDepuisDebutJeu,
			HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo, ArrayList<String> ranking, Region region) {
		super();
		this.laMapDesObjets = laMapDesObjets;
		this.mapDeLaPopulation = mapDeLaPopulation;
		this.listeItemByPlayer = listeItemByPlayer;
		this.listePlayerInfo = listePlayerInfo;
		this.meteoDuJour = meteoDuJour;
		this.meteoDeDemain = meteoDeDemain;
		this.heureDepuisDebutJeu = heureDepuisDebutJeu;
		this.listeDesDrinkInfo = listeDesDrinkInfo;
		this.ranking = ranking;
		this.region = region;
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

	public HashMap<String, ArrayList<MapItem>> getListeItemByPlayer() {
		return listeItemByPlayer;
	}

	public void setListeItemByPlayer(HashMap<String, ArrayList<MapItem>> listeItemByPlayer) {
		this.listeItemByPlayer = listeItemByPlayer;
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
	 * @param playerName
	 * @return une JsonArray de tous les MapItem du joueur
	 */
	private JsonArray getAllMapItems(String playerName) {
		JsonArray jsonAr = new JsonArray();

		for (MapItem mapItem : this.listeItemByPlayer.get(playerName)) {
			jsonAr.add(mapItem.getJsonObject());
		}

		return jsonAr;
	}

	/**
	 * Utilise par getJsonMap()
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


}
