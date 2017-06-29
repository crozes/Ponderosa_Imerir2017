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
import gestion_population.Region;
import gestion_population.Stand;
import outils.Meteo;


/**
 * Permet de lire du json et de mettre à jours les objets lies
 * @author atila
 *
 */
public class ManipulationJson {




	/**
	 * Voici la fonction qui lit le forecast produit par l'Arduino. Ce json
	 * contient la meteo d'aujourd'hui et de demain, ainsi que la duree en heure
	 * depuis le debut de la partie.
	 * 
	 * Recoie : Forecast "dfn" :int, "wheather" : wheather /days from now - 0
	 * Temps "timestamp":int, "wheather":[forecast] means today, 1 means
	 * "tomorrow" "weather": String -> Enum
	 * 
	 * @param jsonTemps
	 * @author atila
	 */
	public static void jsonFromStringTemps(String jsonTemps, TheGame laPartie) {
		String laMeteo = " ";
		JsonElement jsonEl = new JsonParser().parse(jsonTemps);
		JsonObject jsonObTemps = jsonEl.getAsJsonObject();
		JsonArray jsonArWheather;
		JsonObject jsonObForecast;
		int timeStamp = 0;
		int dfn;
		Meteo meteo;

		timeStamp = jsonObTemps.get("timestamp").getAsInt();
		laPartie.setHeureDepuisDebutJeu(timeStamp);

		jsonArWheather = jsonObTemps.get("weather").getAsJsonArray();

		for (int i = 0; i < jsonArWheather.size(); i++) {
			jsonObForecast = jsonArWheather.get(i).getAsJsonObject();
			laMeteo = jsonObForecast.get("weather").getAsString();
			outils.ToString.toStringMeteo(laMeteo + " = format meteo envoye par le serveur");
			meteo = Meteo.valueOf(laMeteo);
			dfn = jsonObForecast.get("dfn").getAsInt();
			if (dfn == 0) {
				outils.ToString.toStringMeteo("La meteo en string : " + laMeteo);
				laPartie.setMeteoDuJour(meteo);
			} else if (dfn == 1) {
				outils.ToString.toStringMeteo("La meteo en string : " + laMeteo);
				laPartie.setMeteoDeDemain(meteo);
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
	 * @throws Exception 
	 */
	public static void jsonFromStringMap(String jsonMap, TheGame laPartie) throws Exception {
		JsonElement jsonEl = new JsonParser().parse(jsonMap);
		JsonObject jsonObMape = jsonEl.getAsJsonObject();
		JsonObject jsonObMap = jsonObMape.get("map").getAsJsonObject();
		JsonObject jsonObRegion = jsonObMap.get("region").getAsJsonObject();
		JsonArray jsonArRanking = jsonObMap.get("ranking").getAsJsonArray();

		JsonObject jsonObPlayerInfo = jsonObMap.get("playerInfo").getAsJsonObject();

		/*------------------------------------------------------------------------------------------------------
		 * Declaration des variables
		 */
		int size_jsonArray = 0;

		/*------------------------------------------------------------------------------------------------------
		 * La region 
		 */
		float center_latitude = jsonObRegion.get("center").getAsJsonObject().get("latitude").getAsFloat();
		float center_longitude = jsonObRegion.get("center").getAsJsonObject().get("longitude").getAsFloat();
		float span_latitude = jsonObRegion.get("span").getAsJsonObject().get("latitudeSpan").getAsFloat();
		float span_longitude = jsonObRegion.get("span").getAsJsonObject().get("longitudeSpan").getAsFloat();
		laPartie.setRegion(new Region(center_latitude, center_longitude, span_latitude, span_longitude));
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

		PlayerInfo playerInfo = null;
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
		laPartie.getListeMapItemJoueur().clear();
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

			try {
				jsonArDrinkOffered = jsonObInfoPlayer.get("drinksOffered").getAsJsonArray();

				size_jsonArray = jsonArDrinkOffered.size();
				playerInfo = new PlayerInfo(sales, cash, profit, new ArrayList<DrinkInfo>());


				for (int i = 0; i < size_jsonArray; i++) {
					jsonObDrinkInfo = jsonArDrinkOffered.get(i).getAsJsonObject();
					name = jsonObDrinkInfo.get("name").getAsString();
					price = jsonObDrinkInfo.get("price").getAsFloat();
					hasAlcohol = jsonObDrinkInfo.get("hasAlcohol").getAsBoolean();
					isCold = jsonObDrinkInfo.get("isCold").getAsBoolean();

					playerInfo.getDrinksOffered().add(new DrinkInfo(name, price, hasAlcohol, isCold));

				}
			} catch (Exception e) {
				System.out.println("Probleme de lecture dans drinkOffered");

			}

			laPartie.getListePlayerInfo().put(playerName, playerInfo);

			// drinksByPlayer

			jsonArDrinkInfo = jsonObDrinksByPlayer.get(playerName).getAsJsonArray();

			size_jsonArray = jsonArDrinkInfo.size();
			ArrayList<DrinkInfo> drinkInfo = new ArrayList<>();

			for (int i = 0; i < size_jsonArray; i++) {
				jsonObDrinkInfo = jsonArDrinkInfo.get(i).getAsJsonObject();
				name = jsonObDrinkInfo.get("name").getAsString();
				price = jsonObDrinkInfo.get("price").getAsFloat();
				hasAlcohol = jsonObDrinkInfo.get("hasAlcohol").getAsBoolean();
				isCold = jsonObDrinkInfo.get("isCold").getAsBoolean();
				drinkInfo.add(new DrinkInfo(name, price, hasAlcohol, isCold));

			}
			laPartie.getListeDesDrinkInfo().put(playerName, drinkInfo);

			// itemsByPlayers
			
			
			ArrayList<MapItem> mapItemPourUnJoueur = new ArrayList<>();
			try {
				jsonArMapItem = jsonObItemsByPlayers.get(playerName).getAsJsonArray();
				

				size_jsonArray = jsonArMapItem.size();


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

					//mapItem contient tous les mapItem, pub et stand. Mais listeStand que des stand
					if(kind.equals("ad")){
						outils.ToString.toStringJSON("Verification du kind pour ad : " + kind );
						mapItemPourUnJoueur.add(new Publicite(kind, owner, influence, new Coordonnees(latitude, longitude)));
					}else{ //c'est un stand
						outils.ToString.toStringJSON("Verification du kind pour stand : " + kind );
						mapItemPourUnJoueur.add(new Stand(kind, owner, influence, new Coordonnees(latitude, longitude)));
						laPartie.getListeDesStand().put(playerName, ( new Stand(kind, owner, influence, new Coordonnees(latitude, longitude)) ) );	
					}

				}
			} catch (Exception e) {
				System.out.println("Mauvaise lecture de ItemByPlayer");
			}
				

			outils.ToString.toStringJSON("insertion de la mapItem dans la partie : "
					+ mapItemPourUnJoueur + " pour : " + playerName + " on est dans la lecture du json sur le serveur" );
			
			
			laPartie.getListeMapItemJoueur().put(playerName, mapItemPourUnJoueur);
			outils.ToString.toStringJSON("Voila la map item finale : " + laPartie.getListeMapItemJoueur());
		}
	}
	
	


	/**
	 * Creer un object json cle + valeur en string Permet de tester une requete
	 * post avec URL URL_TEST_JSON_POST
	 * ="https://ponderosaproject.herokuapp.com/posttest";
	 * 
	 * @param key
	 *            la clé du json
	 * @param value
	 *            la valeur du json
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
