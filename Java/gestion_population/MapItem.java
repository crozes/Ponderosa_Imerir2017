package gestion_population;

import com.google.gson.JsonObject;

/**
 * Classe abstrete MapItem qui peut etre un stand ou une plublicite
 * 
 * @author atila
 *
 */
public  class MapItem {

	String kind;
	String owner;
	float influence;

	Coordonnees coordonnees;

	
	
	
	
	


	public MapItem(String kind, String owner, float influence, Coordonnees coordonnees) {
		super();
		this.kind = kind;
		this.owner = owner;
		this.influence = influence;
		this.coordonnees = coordonnees;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public float getInfluence() {
		return influence;
	}

	public void setInfluence(float influence) {
		this.influence = influence;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public void setCoordonnees(float latitude, float longitude) {
		this.coordonnees = new Coordonnees(latitude, longitude);
	}

	public float getLatitude() {
		return this.coordonnees.getLatitude();
	}

	public void setLatitude(float latitude) {
		this.coordonnees.setLatitude(latitude);
	}

	public float getLongitude() {
		return this.coordonnees.getLongitude();
	}

	public void setLongitude(float longitude) {
		this.coordonnees.setLongitude(longitude);
	}

	public JsonObject getJsonObject() {
		JsonObject jsonOb = new JsonObject();

		jsonOb.addProperty("kind", this.kind);
		jsonOb.addProperty("owner", this.owner);
		jsonOb.addProperty("influence", this.influence);
		jsonOb.add("location", this.coordonnees.getJsonObject());

		return jsonOb;
	}

}
