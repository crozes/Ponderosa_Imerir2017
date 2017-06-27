package gestion_population;

import com.google.gson.JsonObject;

import outils.ValeurPositive;


public class CoordonneesSpan {

	private float latitude_span;
	private float longitude_span;

	/**
	 * Constructeur
	 * 
	 * 
	 * 
	 * 
	 * @param latitude_span
	 * @param longitude_span
	 * 
	 * @exception Si
	 *                latitude_span ou longitude_span sont n�gatives
	 * 
	 * @author atila
	 * @throws PositionException 
	 */
	public CoordonneesSpan(float latitude_span, float longitude_span) throws ValeurPositive {
		super();

		if (latitude_span < 0 || longitude_span < 0) {
			
			throw new ValeurPositive();
			
		}
		else{
			this.latitude_span = latitude_span;
			this.longitude_span = longitude_span;
		}


	}

	public float getLatitude_span() {
		return latitude_span;
	}

	public void setLatitude_span(float latitude_span) {
		this.latitude_span = latitude_span;
	}

	public float getLongitude_span() {
		return longitude_span;
	}

	public void setLongitude_span(float longitude_span) {
		this.longitude_span = longitude_span;
	}
	
	public JsonObject getJsonObject(){
		JsonObject jsonOb = new JsonObject();
		
		
		jsonOb.addProperty("latitude", this.latitude_span);
		jsonOb.addProperty("longitude", this.longitude_span);
		return jsonOb;
	}

}