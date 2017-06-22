package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;


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
	int sales;
	float cash;
	float profit;
	HashMap<String, DrinkInfo> drinksOffered;
	HashMap<String, Integer> deTest = new HashMap<>();
	
	public PlayerInfo(){

	}
	
	public PlayerInfo(int sales, float cash, float profit, HashMap<String, DrinkInfo> drinksOffered) {
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
	public HashMap<String, DrinkInfo> getDrinksOffered() {
		return drinksOffered;
	}
	public void setDrinksOffered(HashMap<String, DrinkInfo> drinksOffered) {
		this.drinksOffered = drinksOffered;
	}
	

	
	


}
