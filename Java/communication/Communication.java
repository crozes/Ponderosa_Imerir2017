package communication;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

			// DataOutputStream out = new DataOutputStream();
			// DataOutputStream(urlConn.getOutputStream());
			// String content = "MYNAME=RYANBOHNERT";
			// out.writeBytes (content);
			// out.flush();
			// out.close();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String resultat = org.apache.commons.io.IOUtils.toString(in);
			System.out.println("get recuperer = " + resultat +" >");
		
			return resultat;

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
	public static void postEnvoyer(String toPost, String URL_Serveur) {
		URL url;
		HttpURLConnection connection =null;


		try {
			url= new URL(URL_Serveur);
			connection = (HttpURLConnection) url.openConnection();
		

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			
			connection.setRequestProperty("Content-Length", "" + Integer.toString(toPost.length()));
			connection.setRequestProperty( "charset", "utf-8");
			
			connection.setInstanceFollowRedirects( false );
			connection.setUseCaches( false );
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			
			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			
			wr.write(toPost.getBytes());
			wr.flush();
			wr.close();
			

			
			
			System.out.println("Envoie: " + toPost);
			int reponse = connection.getResponseCode() ;
			System.out.println("Code reponse " + reponse + " Reponse " + connection.getResponseMessage());

			InputStream in = null;
			
			if(reponse==200){
				in = new BufferedInputStream(connection.getInputStream());
			}
			else {
				in = new BufferedInputStream(connection.getErrorStream());
				System.out.println("\nMauvaise data : ");
			}
			String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
			System.out.println("result " + result);


		} catch (Exception e) {

			e.printStackTrace();
		} 
	}


	
}
