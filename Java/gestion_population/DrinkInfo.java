package gestion_population;

import com.google.gson.JsonObject;

public class DrinkInfo {
	
	/* DrinkInfo
     * {
     *   "name":string,
     *   "price":float,
     *   "hasAlcohol":bool,
     *   "isCold":bool
     * } 
     */

	String name;
	float price;
	boolean hasAlcohol;
	boolean isCold;
	
	int vendue;
	int demande_apres_stock_vide;
	
	
	
	
	
	public DrinkInfo(String name, float price, boolean hasAlcohol, boolean isCold) {
		super();
		this.name = name;
		this.price = price;
		this.hasAlcohol = hasAlcohol;
		this.isCold = isCold;
		this.vendue= 0;
		this.demande_apres_stock_vide = 0;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean getIsHasAlcohol() {
		return hasAlcohol;
	}
	public void setHasAlcohol(boolean hasAlcohol) {
		this.hasAlcohol = hasAlcohol;
	}
	public boolean getIsCold() {
		return isCold;
	}
	public void setCold(boolean isCold) {
		this.isCold = isCold;
	}
	
	
	
	public JsonObject getJsonObject() {
		JsonObject jsonOb = new JsonObject();

		jsonOb.addProperty("name", this.name);
		jsonOb.addProperty("price", this.price);
		jsonOb.addProperty("hasAlcohol", this.hasAlcohol);
		jsonOb.addProperty("isCold", this.isCold);
		
		

		return jsonOb;
	}
	
	
	
}
