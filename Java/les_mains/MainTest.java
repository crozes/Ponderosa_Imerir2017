package les_mains;

import java.io.BufferedReader;
import java.io.IOException;

import communication.*;
import outils.Global;
import netscape.javascript.JSObject;
import com.google.gson.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator;
import communication.ManipulationJson;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Debut du main");

		// connection.postEnvoyer("Plop_du_java");

		String plop = testGet();

		
		
		
		testPost(" ");
		
		
		System.out.println("fin du main");
	}
	
	
	private static void testPost(String plop){

		
		Communication.postEnvoyer(ManipulationJson.creerUnString("prout","The_Game"), Global.URL_TEST_JSON_POST);

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
