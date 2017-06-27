package gestion_population;

import com.google.gson.JsonObject;

import communication.Communication;

public class DrinkInfo {

	/*
	 * DrinkInfo { "name":string, "price":float, "hasAlcohol":bool,
	 * "isCold":bool }
	 */

	private String name;
	private float price;
	private boolean hasAlcohol;
	private boolean isCold;

	private int vendue;
	private boolean isPlusEnStock;

	private float coutEnVolonteFinalePourBoire;

	public DrinkInfo(String name, float price, boolean hasAlcohol, boolean isCold) {
		super();
		this.name = name;
		this.price = price;
		this.hasAlcohol = hasAlcohol;
		this.isCold = isCold;
		this.vendue = 0;
		this.isPlusEnStock = false;
		this.calculCoutVolonteFinalePourBoire();
	}

	private void calculCoutVolonteFinalePourBoire() {
		this.coutEnVolonteFinalePourBoire = price * outils.Global.drinkInfoPoidVolonteFinalePourBoire;
	}

	public boolean demandeDeBoire(String playerName) {
		if (this.isPlusEnStock == true) {
			return false;
		} else {
			String result = Communication.postEnvoyer(this.getJsonObjectSale(playerName).toString(),
					outils.Global.URL_POST_REQUEST_FOR_SELLING);
			if (Integer.parseInt(result) == 0) {
				this.isPlusEnStock = true;
				return false;
			} else {
				return true;
			}
		}
	}

	public float getCoutEnVolonteFinalePourBoire() {
		return coutEnVolonteFinalePourBoire;
	}

	public void setCoutEnVolonteFinalePourBoire(float coutEnVolonteFinalePourBoire) {
		this.coutEnVolonteFinalePourBoire = coutEnVolonteFinalePourBoire;
	}

	public int getVendue() {
		return vendue;
	}

	public void setVendue(int vendue) {
		this.vendue = vendue;
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

	public JsonObject getJsonObjectSale(String playerName) {
		JsonObject jsonObSale = new JsonObject();
		jsonObSale.addProperty("player", playerName);
		jsonObSale.addProperty("item", this.name);
		jsonObSale.addProperty("quantity", this.vendue);
		return jsonObSale;
	}

	public void uneVente(){
		this.vendue++;
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
