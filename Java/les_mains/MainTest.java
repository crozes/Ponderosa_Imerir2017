package les_mains;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import communication.Communication;
import communication.ManipulationJson;
import outils.Global;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Debut du main");

		// connection.postEnvoyer("Plop_du_java");

		
		System.out.println("\n\n On lanche une demande get \n ");
		String plop = testGet();

		
		
		System.out.println("\n\n On lance un post qui pour m'a part me fait perdre à chaque fois");
		testPost(" ");
		
		
		System.out.println("fin du main");
	}
	
	
	private static void testPost(String plop){

		
		Communication.postEnvoyer(ManipulationJson.creerUnString("The_Game","Perdue"), Global.URL_TEST_JSON_POST);

	}
	
	private static String testGet(){
		String testGetString = " ";
		testGetString = Communication.getRecevoir(Global.URL_TEST_JSON_GET);

		JsonElement jelement = new JsonParser().parse(testGetString);
		JsonObject json = jelement.getAsJsonObject();



		System.out.println(json.toString());

		String weather = json.get("weather").getAsString();
		int day = json.get("day").getAsInt();
		float budget = json.get("budget").getAsFloat();
		System.out.println("Afficher string : " + weather + " int : " + day + " float :" + budget);
		return testGetString;
	}

}
