package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class PlayerInfo {
	
    /* PlayerInfo
    * {
    *   "cash":float,
    *   "sales":int,
    *   "profit":float,
    *   "drinksOffered":[drinkInfo]
    * }
      * ------------------------------
     * DrinkInfo
     * {
     *   "name":string,
     *   "price":float,
     *   "hasAlcohol":bool,
     *   "isCold":bool
     * } 
    */
	private int sales;
	private float cash;
	private float profit;
	private ArrayList<DrinkInfo> drinksOffered; 
	private HashMap<String, Integer> deTest = new HashMap<>();
	
	public PlayerInfo(){

	}
	
	public PlayerInfo(int sales, float cash, float profit, ArrayList<DrinkInfo> drinksOffered) {
		super();
		this.sales = sales;
		this.cash = cash;
		this.profit = profit;
		this.drinksOffered = drinksOffered;
	}
	
	
	
	public HashMap<String, Integer> getDeTest(){
		return this.deTest;
	}
	
	
	
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}
	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}

	public ArrayList<DrinkInfo> getDrinksOffered() {
		return drinksOffered;
	}

	public void setDrinksOffered(ArrayList<DrinkInfo> drinksOffered) {
		this.drinksOffered = drinksOffered;
	}
	
	
	
	

	public JsonObject getJsonObject() {
		JsonObject jsonOb = new JsonObject();
		JsonArray jsonArDrinksOffered = new JsonArray();

		jsonOb.addProperty("cash", this.cash);
		jsonOb.addProperty("sales", this.sales);
		jsonOb.addProperty("profit", this.profit);

		for (int i = 0; i<this.drinksOffered.size();i++) {
			jsonArDrinksOffered.add(drinksOffered.get(i).getJsonObject());
		}

		jsonOb.add("drinksOffered", jsonArDrinksOffered);

		return jsonOb;
	}


}
