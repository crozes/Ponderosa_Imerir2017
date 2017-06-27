package gestion_population;

import com.google.gson.JsonObject;

import outils.ValeurPositive;

public class Region {

	private Coordonnees center;
	private CoordonneesSpan span;
	private float latitudeMax;
	private float latitudeMin;
	private float longitudeMax;
	private float longitudeMin;

	public Region() {

	}

	public Region(float latitude_center, float longitude_center, float latitude_span, float longitude_span)
			throws ValeurPositive {

		super();
		this.center = new Coordonnees(latitude_center, longitude_center);
		this.span = new CoordonneesSpan(latitude_span, longitude_span);
		this.calculerBordDeLaMapCarre();
	}

	private void calculerBordDeLaMapCarre() {
		float y_x_max = outils.OutilsCalculs.calculerDistance(center.getLatitude(), center.getLongitude(),
				span.getLatitude_span(), span.getLongitude_span());
		this.latitudeMax = y_x_max;
		this.latitudeMin = (-y_x_max);
		this.longitudeMax = y_x_max;
		this.longitudeMin = (-y_x_max);
	}

	public float getLatitudeMax() {
		return latitudeMax;
	}

	public void setLatitudeMax(float latitudeMax) {
		this.latitudeMax = latitudeMax;
	}

	public float getLatitudeMin() {
		return latitudeMin;
	}

	public void setLatitudeMin(float latitudeMin) {
		this.latitudeMin = latitudeMin;
	}

	public float getLongitudeMax() {
		return longitudeMax;
	}

	public void setLongitudeMax(float longitudeMax) {
		this.longitudeMax = longitudeMax;
	}

	public float getLongitudeMin() {
		return longitudeMin;
	}

	public void setLongitudeMin(float longitudeMin) {
		this.longitudeMin = longitudeMin;
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
	
	public String toString(){
		if(this.center != null && this.span!= null){
			String toReturn = (this.center.toString() + " " + this.span.toString());
			return toReturn;
		}
		else{
			return "Objet null";
		}
	}

}
