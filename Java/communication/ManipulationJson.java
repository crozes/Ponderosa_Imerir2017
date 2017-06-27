package communication;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gestion_population.Coordonnees;
import gestion_population.DrinkInfo;
import gestion_population.MapItem;
import gestion_population.TheGame;
import gestion_population.PlayerInfo;
import gestion_population.Publicite;
import gestion_population.Stand;
import outils.Meteo;

public class ManipulationJson {






	/**
	 * Voici la fonction qui lit le forecast produit par l'Arduino.
	 * Ce json contient la meteo d'aujourd'hui et de demain, ainsi que la duree en heure depuis le debut de la partie.
	 * 
	 * Recoie : Temps "timestamp":int Forecast "dfn" :int /days from now - 0
	 * means today, 1 means "tomorrow" "weather": String -> Enum
	 * 
	 * @param jsonTemps
	 * @author atila
	 */
	public static void jsonFromStringTemps(String jsonTemps, TheGame laPartie) {
		String laMeteo = " ";
		JsonElement jsonEl = new JsonParser().parse(jsonTemps);
		JsonObject jsonOb = jsonEl.getAsJsonObject();

		laPartie.setHeureDepuisDebutJeu(jsonOb.get("timestamp").getAsInt());

		JsonArray jsonAr = jsonOb.get("weather").getAsJsonArray();

		jsonOb = jsonAr.get(0).getAsJsonObject();

		for (int i = 0; i < 2; i++) {
			laMeteo = jsonOb.get("weather").getAsString();
			if (jsonOb.get("dfn").getAsInt() == i) {
				laPartie.setMeteoDuJour(Meteo.valueOf(laMeteo));
			} else {
				laPartie.setMeteoDeDemain(Meteo.valueOf(laMeteo));
			}
		}

	}
	

	
	

	/**
	 * Voici la fonction pour traduire le json par rapport à nos objets.
	 * Vous trouverez dans ce commentaire l'architecture du json Map est ses sous-element.
	 * 
     * ------------------------------
     * Coordinates
     * {
     *   "latitude":float,
     *   "longitude":float
     *}
     * ------------------------------
     * CoordinatesSpan
     * {
     *   "latitudeSpan":float, //positive
     *   "longitudeSpan":float //positive
     * }
     * ------------------------------
     * DrinkInfo
     * {
     *   "name":string,
     *   "price":float,
     *   "hasAlcohol":bool,
     *   "isCold":bool
     * } 
     * ------------------------------
     * MapItem
     * {
     *   "kind":string,   //"stand" or "ad"
     *   "owner":string,  //id/name player
     *   "location":coordinates,  //location on the map
     *   "influence":float  //distance
     * }
     * ------------------------------ 
     * PlayerInfo
     * {
     *   "cash":float,
     *   "sales":int,
     *   "profit":float,
     *   "drinksOffered":[drinkInfo]
     * }
     * ------------------------------
     * Region
     * {
     *   "center":coordinates,
     *   "span":coordinatesSpan
     * }
     * ------------------------------
     * Map
     * {
     *   "region":region,
     *   "ranking":[string],  //string id/name first richest
     *   "playerInfo":{  //string id/name
     *     string:playerInfo,
     *     string:playerInfo,
     *   },
     *   "itemsByPlayers":{ //string id/name player
     *     string:[mapItem],
     *     string:[mapItem]
     *   },
     *   "drinksByPlayer":{ //string id/name
     *     string:[drinkInfo],
     *     string:[drinkInfo]
     *  }
     * }
     *
	 * @param jsonMap
	 *            Le Strig Json Map 
	 * @param laPartie
	 *            LaPartie est un objet de class Partie qui est l'element central du programme, c'est lui qui fait
	 *            le lien avec toutes les informations.
	 *            
	 * @author atila
	 */
	public static void jsonFromStringMap(String jsonMap, TheGame laPartie) {
		JsonElement jsonEl = new JsonParser().parse(jsonMap);
		JsonObject jsonObMape = jsonEl.getAsJsonObject();
		JsonObject jsonObMap = jsonObMape.get("map").getAsJsonObject();
		JsonArray jsonArRanking = jsonObMap.get("ranking").getAsJsonArray();

		JsonObject jsonObPlayerInfo = jsonObMap.get("playerInfo").getAsJsonObject();


		/*------------------------------------------------------------------------------------------------------
		 * Declaration des variables
		 */
		int size_jsonArray = 0;

		/*------------------------------------------------------------------------------------------------------
		 * Le ranking
		 */
		laPartie.getRanking().clear();
		size_jsonArray = jsonArRanking.size();
		for (int i = 0; i < size_jsonArray; i++) {
			laPartie.getRanking().add(jsonArRanking.get(i).getAsString());
		}

		/*------------------------------------------------------------------------------------------------------
		 * Les players
		 */

		laPartie.getListePlayerInfo().clear();

		JsonObject jsonObInfoPlayer; // il est à l'interieur de PlayerInfo

		JsonArray jsonArDrinkOffered;
		JsonObject jsonObDrinkInfo;

		PlayerInfo playerInfo;
		float cash;
		int sales;
		float profit;

		// DrinkInfo drinkInfo;
		String name;
		float price;
		boolean hasAlcohol;
		boolean isCold;

		laPartie.getListeDesDrinkInfo().clear();

		JsonObject jsonObDrinksByPlayer = jsonObMap.get("drinksByPlayer").getAsJsonObject();
		JsonArray jsonArDrinkInfo;

		/*
		 * 
		 * "itemsByPlayers":{ //string id/name player string:[mapItem],
		 * string:[mapItem] },
		 */

		

		JsonObject jsonObItemsByPlayers = jsonObMap.get("itemsByPlayers").getAsJsonObject();
		JsonArray jsonArMapItem;

		// MapItem
		
		JsonObject jsonObLocation;
		JsonObject jsonObMapItem;
		String kind;
		String owner;
		float influence;

		// Coordonnees location;
		float latitude = 0f;
		float longitude = 0f;

		// un for assez consequent mais qui permet de traiter tous les joueurs
		// les uns apres les autres..
		for (String playerName : laPartie.getRanking()) {

			jsonObInfoPlayer = jsonObPlayerInfo.get(playerName).getAsJsonObject();
			cash = jsonObInfoPlayer.get("cash").getAsFloat();
			sales = jsonObInfoPlayer.get("sales").getAsInt();
			profit = jsonObInfoPlayer.get("profit").getAsFloat();

			jsonArDrinkOffered = jsonObInfoPlayer.get("drinksOffered").getAsJsonArray();

			size_jsonArray = jsonArDrinkOffered.size();
			playerInfo = new PlayerInfo(sales, cash, profit, new ArrayList<DrinkInfo>());

			//int i =0;
			//while(jsonArDrinkOffered.get(i).getAsJsonObject().isJsonNull()==false){
			for (int i = 0; i < size_jsonArray; i++) {
				jsonObDrinkInfo = jsonArDrinkOffered.get(i).getAsJsonObject();
				name = jsonObDrinkInfo.get("name").getAsString();
				price = jsonObDrinkInfo.get("price").getAsFloat();
				hasAlcohol = jsonObDrinkInfo.get("hasAlcohol").getAsBoolean();
				isCold = jsonObDrinkInfo.get("isCold").getAsBoolean();

				playerInfo.getDrinksOffered().add(new DrinkInfo(name, price, hasAlcohol, isCold));
//				i++;
			}

			laPartie.getListePlayerInfo().put(playerName, playerInfo);

			// drinksByPlayer

			jsonArDrinkInfo = jsonObDrinksByPlayer.get(playerName).getAsJsonArray();

			size_jsonArray = jsonArDrinkInfo.size();
			ArrayList<DrinkInfo> drinkInfo = new ArrayList<>();
//			i =0;
//			while(!jsonArDrinkInfo.get(i).getAsJsonObject().isJsonNull()){
			for (int i = 0; i < size_jsonArray; i++) {
				jsonObDrinkInfo = jsonArDrinkInfo.get(i).getAsJsonObject();
				name = jsonObDrinkInfo.get("name").getAsString();
				price = jsonObDrinkInfo.get("price").getAsFloat();
				hasAlcohol = jsonObDrinkInfo.get("hasAlcohol").getAsBoolean();
				isCold = jsonObDrinkInfo.get("isCold").getAsBoolean();
				drinkInfo.add(new DrinkInfo(name, price, hasAlcohol, isCold));
//				i++;
			}
			laPartie.getListeDesDrinkInfo().put(playerName, drinkInfo);

			// itemsByPlayers
			laPartie.getListeMapItemJoueur().clear();
			jsonArMapItem = jsonObItemsByPlayers.get(playerName).getAsJsonArray();

			size_jsonArray = jsonArMapItem.size();
			ArrayList<MapItem> mapItem = new ArrayList<>();
//			i =0;
//			while(!jsonArMapItem.get(i).getAsJsonObject().isJsonNull()){
			for (int i = 0; i < size_jsonArray; i++) {
				jsonObMapItem = jsonArMapItem.get(i).getAsJsonObject();

				kind = jsonObMapItem.get("kind").getAsString();
				owner = jsonObMapItem.get("owner").getAsString();
				influence = jsonObMapItem.get("influence").getAsFloat();
				jsonObLocation = jsonObMapItem.get("location").getAsJsonObject();

				latitude = jsonObLocation.get("latitude").getAsFloat();
				longitude = jsonObLocation.get("longitude").getAsFloat();

				// public MapItem(String kind, String owner, float influence,
				// Coordonnees coordonnees) {

				
				if(kind == "ad"){
					mapItem.add(new Publicite(kind, owner, influence, new Coordonnees(latitude, longitude)));
				}else{ //c'est un stand
					mapItem.add(new Stand(kind, owner, influence, new Coordonnees(latitude, longitude)));
					laPartie.getListeDesStand().put(playerName, ( new Stand(kind, owner, influence, new Coordonnees(latitude, longitude)) ) );	
				}
//				i++;
			}
				laPartie.getListeMapItemJoueur().put(playerName, mapItem);
			
		}
	}
	
	
	
//	
//	
//	public static String jsonToStringMap(Partie laPartie){
//		
//		JsonObject jsonObResult = new JsonObject();
//		
//		JsonObject jsonObRegion = new JsonObject();
//		JsonObject jsonObSpan = new JsonObject();
//		JsonObject jsonObCenter = new JsonObject();
//
//		JsonArray jsonArRanking = new JsonArray();
//		JsonObject jsonObRanking = new JsonObject();
//		
//		JsonObject jsonObPlayerInfo = new JsonObject();
//		JsonObject jsonObInfoPlayer = new JsonObject(); // le playerinfo dans le playerinfo In foplayer ....
//		JsonArray jsonArDrinksOffered = new JsonArray();
//		JsonObject jsonObDrinkInfo = new JsonObject();
//
//		
//		JsonObject jsonObItemsByPlayers = new JsonObject();
//		JsonArray jsonArMapItem = new JsonArray();
//		JsonObject jsonObMapItem = new JsonObject();
//		JsonObject jsonObLocation = new JsonObject();
//		
//		
//		JsonObject jsonObDrinksByPlayers= new JsonObject();
//		JsonArray jsonArDrinkInfo = new JsonArray();
////		JsonObject jsonObDrinkInfo = new JsonObject();
////		JsonObject jsonObName = new JsonObject();
////		JsonObject jsonObPrice = new JsonObject();
////		JsonObject jsonObHasAlcohol = new JsonObject();
////		JsonObject jsonObIsCold = new JsonObject();
//		
//		
//		
//		jsonObCenter.addProperty("latitude", laPartie.getRegion().getCenter().getLatitude());
//		jsonObCenter.addProperty("longitude", laPartie.getRegion().getCenter().getLatitude());
//		jsonObRegion.add("center", jsonObCenter);
//		
//		jsonObCenter.addProperty("latitude", laPartie.getRegion().getSpan().getLatitude_span());
//		jsonObCenter.addProperty("latitude", laPartie.getRegion().getSpan().getLongitude_span());
//		jsonObRegion.add("span", jsonObSpan);
//		
//		jsonObResult.add("region", jsonObRegion);
//		
//
//		
//		for (String playerName : laPartie.getRanking()) {
//
//			// ranking
//			jsonObRanking.addProperty(playerName, playerName);
//			jsonArRanking.add(jsonObRanking);
//
//
//			
//			jsonObItemsByPlayers.add(playerName, jsonArMapItem);
//			jsonObResult.add("itemsByPlayers", jsonObItemsByPlayers);
//
//			// drinksByPlayer
//			jsonObResult.add("drinksByPlayer", jsonObDrinksByPlayers);
//		}
//
//		return jsonObResult.toString();
//	}


	/**
	 * Creer un object json cle + valeur en string
	 * Permet de tester une requete post avec URL
	 * URL_TEST_JSON_POST ="https://ponderosaproject.herokuapp.com/posttest";
	 * 
	 * @param key la clé du json
	 * @param value la valeur du json
	 * 
	 * @author atila
	 */

	public static String creerUnString(String key, String value) {
		String result = " ";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(key, value);
		result = jsonObject.toString();

		System.out.println(result);

		return result;
	}

}
