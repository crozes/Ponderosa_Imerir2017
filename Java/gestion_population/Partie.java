package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import outils.*;


public class Partie {

	private MapItem laMapDesObjets[][];
	private Population mapDeLaPopulation;
	
	private HashMap<String, ArrayList<MapItem> > listeItemByPlayer;
	private HashMap<String, PlayerInfo> listePlayerInfo;
	private Meteo meteoDuJour;
	private Meteo meteoDeDemain;
	private int heureDepuisDebutJeu;
	private HashMap<String, ArrayList<DrinkInfo>> listeDesDrinkInfo; //joueur drinkinfo
	private ArrayList<String> ranking;
	private Region region;


	
	
	
	
	


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
	
	
	public JsonObject getJsonObject() {
		JsonObject jsonOb = new JsonObject();
		//JsonArray jsonArRanking = new JsonArray();
		JsonArray jsonArRanking = new JsonArray();
		JsonObject jsonObPlayerInfo = new JsonObject();
		JsonObject jsonObItempByPlayers = new JsonObject();

		for (String playerName : this.ranking) {
			JsonPrimitive element = new JsonPrimitive(playerName);
			jsonArRanking.add(element);
			jsonObPlayerInfo.add(playerName, this.listePlayerInfo.get(playerName).getJsonObject());
			jsonObItempByPlayers.add(playerName, getAllMapItems(playerName));
		}

		
		jsonOb.add("region", this.region.getJsonObject());
		jsonOb.add("ranking", jsonArRanking);
		
		
		jsonOb.add("playerInfo", jsonObPlayerInfo);

		return jsonOb;
	}
	
	private JsonArray getAllMapItems(String playerName){
		JsonArray jsonAr = new JsonArray();
		
		for(MapItem mapItem : this.listeItemByPlayer.get(playerName)){
			jsonAr.add(mapItem.getJsonObject());
		}
		
		
		return jsonAr;
	}


}
