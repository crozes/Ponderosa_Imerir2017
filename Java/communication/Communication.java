package communication;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import outils.Global;

/**
 * 
 * @author atila
 *
 */
public class Communication {
	

	/**
	 * 
	 * @return
	 */
	public static String getRecevoir(String URL_Serveur) {

		URL url;
		HttpURLConnection connection =null;
		BufferedReader in = null;
		try {
			url= new URL(URL_Serveur);
			connection = (HttpURLConnection) url.openConnection();

			System.out.println("< Recuperation du get");

			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// DataOutputStream out = new
			// DataOutputStream(urlConn.getOutputStream());
			// String content = "MYNAME=RYANBOHNERT";
			// out.writeBytes (content);
			// out.flush();
			// out.close();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String resultat = org.apache.commons.io.IOUtils.toString(in);
			System.out.println("get recuperer = " + resultat +" >");
		
			JsonElement jelement = new JsonParser().parse(resultat);
			JsonObject json = jelement.getAsJsonObject();

			String weather = json.get("weather").getAsString();
			int day = json.get("day").getAsInt();
			float budget = json.get("budget").getAsFloat();
			return "meteo : " + weather + "\n jour : " + day + "\n budjet : " + budget;

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return " ";
		
	}
	
	
	/**
	 * 
	 * @param toPost
	 */
	public String postEnvoyer(String toPost, String URL_Serveur) {
		URL url;
		HttpURLConnection connection =null;
		BufferedReader in = null;

		try {
			url= new URL(URL_Serveur);
			connection = (HttpURLConnection) url.openConnection();
		

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(toPost.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(toPost);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} 
	}


	
}
