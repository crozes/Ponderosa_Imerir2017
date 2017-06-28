package gestion_population;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

	/**
	 * Permet de savoir si une boisson est encore en stock.
	 * Si le serveur nous dit qu'il n'y a plus de stock nous enregistrons l'information pour ne pas saturer le
	 * serveur ce requete.
	 * @param playerName
	 * @param quantity
	 * @return 
	 * 		vrai si une vente a ete faite 
	 * 		faux s'il n'y a plus de boisson en stock
	 */
	public boolean demandeDeBoire(String playerName, int quantityCommande) {
		if (this.isPlusEnStock == true) {
			return false;
		} else {
			String resultJson = Communication.postEnvoyer(this.postUneCommandeDeBoissonEnArSales(playerName, quantityCommande).toString(),
					outils.Global.URL_POST_REQUEST_FOR_SELLING);
			int quantityEnStock = this.lireReponsePostCommande(resultJson);

			//on en commande plus qu'il y en a
			if (quantityEnStock < quantityCommande) {
				this.isPlusEnStock = true;
				return false;
			} else {
				this.uneVente();
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
	
	/**
	 * Renvoie la quantite en stock avant la vente
	 * @param jsonSales
	 * @return Quantite en stock de la boisson avant la vente
	 */
	private int lireReponsePostCommande(String jsonSales){
		JsonElement jsonEl = new JsonParser().parse(jsonSales);
		JsonObject jsonOb = jsonEl.getAsJsonObject();
		JsonArray jsonArDrinkInfo = jsonOb.get("sales").getAsJsonArray();
		
		return jsonArDrinkInfo.get(0).getAsJsonObject().get("quantity").getAsInt();
	}
	
	private JsonObject postUneCommandeDeBoissonEnArSales(String playerName, int quantity){
		JsonArray jsonAr = new JsonArray();
		jsonAr.add(this.getJsonObjectSale(playerName, quantity));
		JsonObject jsonOb = new JsonObject();
		jsonOb.add("sales", jsonAr);
		return jsonOb;
	}
	
	public JsonObject getJsonObjectSale(String playerName, int quantity) {
		JsonObject jsonObSale = new JsonObject();
		jsonObSale.addProperty("player", playerName);
		jsonObSale.addProperty("item", this.name);
		jsonObSale.addProperty("quantity", quantity);
		return jsonObSale;
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

	public String toString(){
		return ("Drink nom : " + this.name + " prix : " + this.price + " cout en volonte : " + this.coutEnVolonteFinalePourBoire);
	}
	
}
