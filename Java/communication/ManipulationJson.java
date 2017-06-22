package communication;

import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gestion_population.*;
import outils.Meteo;

public class ManipulationJson {

	/**
	 * Envoie : PlayerInfo "cash":float "sales":int "profit":float
	 * 
	 * @return
	 */
	public static String jsonToStringPlayerInfo() {
		String jsonPlayerInfo = " ";

		return jsonPlayerInfo;
	}

	/**
	 * Recoie : Temps "timestamp":int Forecast "dfn" :int /days from now - 0
	 * means today, 1 means "tomorrow" "weather": String -> Enum
	 * 
	 * @param jsonTemps
	 */
	public static void jsonFromStringTemps(String jsonTemps, Partie laPartie) {
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
	 *            Le Strig Json recuperer du serveur
	 * @param laPartie
	 *            L'objet utilisé pour enregistrer les informations du Json
	 */
	public static void jsonFromStringMap(String jsonMap, Partie laPartie) {
		JsonElement jsonEl = new JsonParser().parse(jsonMap);
		JsonObject jsonObMap = jsonEl.getAsJsonObject();

		JsonArray jsonArRanking = jsonObMap.get("ranking").getAsJsonArray();

		
		JsonObject jsonObPlayerInfo = jsonObMap.get("playerInfo").getAsJsonObject();
		JsonObject jsonObDrinkByPlayer = jsonObMap.get("drinksByPlayer").getAsJsonObject();

		/*------------------------------------------------------------------------------------------------------
		 * Declaration des variables
		 */
		int size_jsonArray = 0;
		String a;
		

		/*------------------------------------------------------------------------------------------------------
		 * Le ranking
		 */
		size_jsonArray = jsonArRanking.size();
		for (int i = 0; i < size_jsonArray; i++) {
			laPartie.getRanking().clear();
			laPartie.getRanking().add(jsonArRanking.get(i).getAsString());
		}

		/*------------------------------------------------------------------------------------------------------
		 * Les players
		 */ 

		laPartie.getListePlayerInfo().clear(); 
		
		
		JsonObject jsonObInfoPlayer; //il est à l'interieur de PlayerInfo
		
		JsonArray jsonArDrinkOffered;
		JsonObject jsonObDrinkInfo;
		
		PlayerInfo playerInfo;
		int cash;
		int sales;
		float profit;
		
		//DrinkInfo drinkInfo;
		String name;
		float price;
		boolean hasAlcohol;
		boolean isCold;
		
		
		laPartie.getListeDesDrinkInfo().clear();
				
		JsonObject jsonObDrinksByPlayer = jsonObMap.get("drinksByPlayer").getAsJsonObject();
		JsonArray jsonArDrinkInfo;
		
		
		/*
		
	     *   "itemsByPlayers":{ //string id/name player
		     *     string:[mapItem],
		     *     string:[mapItem]
		     *   },
		*/
		
		laPartie.getListeItemByPlayer().clear();
		
		JsonObject jsonObItemsByPlayers = jsonObMap.get("itemsByPlayers").getAsJsonObject();
		JsonArray jsonArMapItem;
		
		
		//un for assez consequent mais qui permet de traiter tous les joueurs les uns apres les autres..
		for (String playerName : laPartie.getRanking()) {
					
			jsonObInfoPlayer = jsonObPlayerInfo.get(playerName).getAsJsonObject();
			cash = jsonObInfoPlayer.get("cash").getAsInt();
			sales = jsonObInfoPlayer.get("sales").getAsInt();
			profit = jsonObInfoPlayer.get("profit").getAsFloat();
			
			jsonArDrinkOffered = jsonObInfoPlayer.get("drinksOffered").getAsJsonArray();

			size_jsonArray = jsonArDrinkOffered.size();
			playerInfo = new PlayerInfo(sales, cash, profit, new HashMap<String, DrinkInfo>());

			for (int i = 0; i < size_jsonArray; i++) {
				jsonObDrinkInfo = jsonArDrinkOffered.get(i).getAsJsonObject();
				name = jsonObDrinkInfo.get("name").getAsString();
				price = jsonObDrinkInfo.get("price").getAsFloat();
				hasAlcohol = jsonObDrinkInfo.get("hasAlcohol").getAsBoolean();
				isCold = jsonObDrinkInfo.get("isCold").getAsBoolean();

				playerInfo.getDrinksOffered().put(name, new DrinkInfo(name, price, hasAlcohol, isCold));

			}

			laPartie.getListePlayerInfo().put(playerName, playerInfo);

			// drinksByPlayer

			jsonArDrinkInfo = jsonObDrinksByPlayer.get(playerName).getAsJsonArray();

			size_jsonArray = jsonArDrinkInfo.size();
			for (int i = 0; i < size_jsonArray; i++) {
				jsonObDrinkInfo = jsonArDrinkInfo.get(i).getAsJsonObject();
				name = jsonObDrinkInfo.get("name").getAsString();
				price = jsonObDrinkInfo.get("price").getAsFloat();
				hasAlcohol = jsonObDrinkInfo.get("hasAlcohol").getAsBoolean();
				isCold = jsonObDrinkInfo.get("isCold").getAsBoolean();

				laPartie.getListeDesDrinkInfo().put(playerName, new DrinkInfo(name, price, hasAlcohol, isCold));
			}
			
			// itemsByPlayers

			jsonArMapItem = jsonObItemsByPlayers.get(playerName).getAsJsonArray();
			
			size_jsonArray = jsonArMapItem.size();
			JsonObject jsonObLocation;
			
			for(int i = 0 ; i<size_jsonArray; i++){
				
				String kind;
				String owner;
				Coordonnees location;
				float influence;
				
				float x;
				float y;
			}

		}
		

			
		

	}

	public static String jsonToStringMap(Partie laPartie) {
		String result = " ";

		return result;
	}

	/**
	 * test envoie
	 * 
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
