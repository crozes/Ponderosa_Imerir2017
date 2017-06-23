package gestion_population;

import com.google.gson.JsonObject;

import outils.ValeurPositive;

public class Region {
	
	private Coordonnees center;
	private CoordonneesSpan span;
	
	
	public Region(float latitude_center, float longitude_center, float latitude_span, float longitude_span) throws ValeurPositive {

		super();
		this.center = new Coordonnees(latitude_center, longitude_center);
		this.span = new CoordonneesSpan(latitude_span, longitude_span);
	}
	
	
	
	public Coordonnees getCenter() {
		return center;
	}
	public void setCenter(Coordonnees center) {
		this.center = center;
	}
	public CoordonneesSpan getSpan() {
		return span;
	}
	public void setSpan(CoordonneesSpan span) {
		this.span = span;
	}
	
	
	public JsonObject getJsonObject() {
		JsonObject jsonOb = new JsonObject();

		jsonOb.add("center", this.center.getJsonObject());
		jsonOb.add("center", this.span.getJsonObject());

		return jsonOb;
	}


}
