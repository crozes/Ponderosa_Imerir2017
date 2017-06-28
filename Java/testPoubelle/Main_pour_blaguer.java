package testPoubelle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import communication.Communication;
import outils.Meteo;

public class Main_pour_blaguer {

	public static void main(String[] args) {
		// 
//		//lancement du thread de requete du Forecast
//		ThreadGetForecast threadForecast = new ThreadGetForecast(new TheGame());
//		threadForecast.start();
//		
////		
		String jsonTemps = Communication.getRecevoir(outils.Global.URL_GET_FORECAST);
		System.out.println(jsonTemps);
		
		String laMeteo = " ";
		JsonElement jsonEl = new JsonParser().parse(jsonTemps);
		JsonObject jsonObTemps = jsonEl.getAsJsonObject();
		JsonArray jsonArWheather;
		JsonObject jsonObForecast;
		

		System.out.println((jsonObTemps.get("timestamp").getAsInt()));

		jsonArWheather = jsonObTemps.get("weather").getAsJsonArray();

		for (int i = 0; i < 2; i++) {
			jsonObForecast = jsonArWheather.get(i).getAsJsonObject();
			laMeteo = jsonObForecast.get("weather").getAsString();
			outils.ToString.toStringMeteo(laMeteo +" = format meteo envoye par le serveur");
			if (jsonObForecast.get("dfn").getAsInt() == i) {
				//outils.ToString.toStringMeteo("La meteo en string : " +laMeteo);
				System.out.println((Meteo.valueOf(laMeteo)));
			} else {
				//outils.ToString.toStringMeteo("La meteo en string : " +laMeteo);
				System.out.println((Meteo.valueOf(laMeteo)));
			}
		}
		
		

		
	}

}
