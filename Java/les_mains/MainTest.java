package les_mains;

import java.io.BufferedReader;
import java.io.IOException;

import communication.*;
import outils.Global;
import netscape.javascript.JSObject;


public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Debut du main");
		
		

		
		
		//connection.postEnvoyer("Plop_du_java");
		
		
		BufferedReader testGet = null; 
		String testGetString = " ";
		testGet = Communication.getRecevoir(Global.URL_TEST_JSON);

		try {
			testGetString = org.apache.commons.io.IOUtils.toString(testGet);
			JSONObject  json = new JSONObject (testGetString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		System.out.println("fin du main");
	}

}
