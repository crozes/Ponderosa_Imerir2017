package gestion_population;

import com.google.gson.JsonObject;

/**
 * Simple classe qui à comme paramettres x et y.
 * Elle permet de localiser toute chose physique sur la carte
 * @author atila
 *
 */
public class Coordonnees {
	private float latitude;
	private float longitude;
	
	public Coordonnees(float latitude, float longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public JsonObject getJsonObject(){
		JsonObject jsonOb = new JsonObject();
		
		
		jsonOb.addProperty("latitude", this.latitude);
		jsonOb.addProperty("longitude", this.longitude);
		return jsonOb;
	}

	
	public String toString(){
		return ("Latitude:" + this.latitude + " Longitude:" + this.longitude + " .");
	}

}
