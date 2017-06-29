package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Class player info
 * 
 * @author atila
 *
 */
public class PlayerInfo {

	private int sales;
	private float cash;
	private float profit;
	private ArrayList<DrinkInfo> drinksOffered;
	private HashMap<String, Integer> deTest = new HashMap<>();

	public PlayerInfo() {

	}

	public PlayerInfo(int sales, float cash, float profit, ArrayList<DrinkInfo> drinksOffered) {
		super();
		this.sales = sales;
		this.cash = cash;
		this.profit = profit;
		this.drinksOffered = drinksOffered;

		this.trierDrinksOffered();

	}

	/**
	 * Permet de trier les boisson par ordre de prix
	 */
	private void trierDrinksOffered() {
		ArrayList<DrinkInfo> drinksOfferedTrie = new ArrayList<DrinkInfo>();
		float price = 0f;
		DrinkInfo boissonLaPlusChere = null;
		while (this.drinksOffered.size() > 0) {
			price = 0f;
			for (DrinkInfo drinkInfo : this.drinksOffered) {
				if (drinkInfo.getPrice() > price) {
					price = drinkInfo.getPrice();
					boissonLaPlusChere = drinkInfo;
				}
			}
			drinksOfferedTrie.add(boissonLaPlusChere);
			this.drinksOffered.remove(boissonLaPlusChere);
		}
		this.drinksOffered = drinksOfferedTrie;
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

		for (int i = 0; i < this.drinksOffered.size(); i++) {
			jsonArDrinksOffered.add(drinksOffered.get(i).getJsonObject());
		}

		jsonOb.add("drinksOffered", jsonArDrinksOffered);

		return jsonOb;
	}

	public String toString() {
		return ("Nb ventes : " + this.sales);
	}

}
